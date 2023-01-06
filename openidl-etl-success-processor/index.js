const processRecords = require("./processor").process;
const config = require("./config/config.json");
var aws = require("aws-sdk");
aws.config.update({
  region: config.region, httpOptions: {
    timeout: config.reqTimeOut
  }
});
const s3 = new aws.S3({ apiVersion: "2006-03-01" });
const ddb = new aws.DynamoDB.DocumentClient();
var sns = new aws.SNS();
const log4js = require("log4js");
const logger = log4js.getLogger('index');
logger.level = config.logLevel;
function getParams(event) {
  // Get the object from the event and show its content type
  const bucket = event.Records[0].s3.bucket.name;
  const key = decodeURIComponent(
    event.Records[0].s3.object.key.replace(/\+/g, " ").split(".")[0]
  );
  const file_name = decodeURIComponent(
    event.Records[0].s3.object.key.replace(/\+/g, " ")
  );

  const params = {
    Bucket: bucket,
    Key: key,
    FileName: file_name,
  };
  return params;
}

async function setUp(eventParams) {
  let run = false;
  const params2 = {
    TableName: config.Dynamo.etlControlTable,
    Key: { SubmissionFileName: eventParams.Key },
  };
  let item = await ddb.get(params2).promise();
  if (item.Item) {
    if (item.Item.SubmissionFileName === eventParams.Key) {
      logger.info("file exists in control");
      if (item.Item.IDMLoaderStatus === "success") {
        run = false;
        logger.debug("file found status: success");
        //sns file has already been loaded
        var snsFailureParams = {
          Message: eventParams.Key + " has already been loaded to IDM",
          Subject: "ETL Loader Processing Has Failed",
          TopicArn: config.sns.failureETLARN,
        };
        await sns.publish(snsFailureParams).promise();
      }

      if (item.Item.IDMLoaderStatus === "submitted") {
        run = false;
        logger.debug("file found status: submitted");
        //sns file is already processing

        var snsFailureParams = {
          Message: eventParams.Key + " is already processing/hung",
          Subject: "ETL Loader Processing Has Failed",
          TopicArn: config.sns.failureETLARN,
        };
        await sns.publish(snsFailureParams).promise();
      }

      if (item.Item.IDMLoaderStatus === "error") {
        run = true;
        logger.error("file found status: error");
      }

      if (item.Item.IDMLoaderStatus === "") {
        run = true;
        logger.error("file found status: blank");
      }

      if (!item.Item.IDMLoaderStatus) {
        run = true;
        logger.error("No Loader Record found");
      }
    }

    if (!item.Item.IntakeStatus === "success") {
      run = false;
      logger.error("Loader error: File Has already been loaded");
    }

    //add submitted record
    if (run) {
      let insertParams = {
        TableName: config.Dynamo.etlControlTable,
        Item: {
          SubmissionFileName: eventParams.Key,
          IntakeStatus: "success",
          IDMLoaderStatus: "submitted",
        },
      };
      await ddb.put(insertParams).promise();
    }
    return run;
  }

  let insertParams = {
    TableName: config.Dynamo.etlControlTable,
    Item: {
      SubmissionFileName: eventParams.Key,
      IntakeStatus: "Not Found",
      IDMLoaderStatus: "error",
    },
  };
  await ddb.put(insertParams).promise();

  return false;
}

async function getRecords(eventParams) {
  const params = { "Bucket": eventParams.Bucket, "Key": eventParams.FileName };
  logger.info('get records params :')
  logger.debug(params)
  const raw = await s3.getObject(params).promise();
  const data = JSON.parse(raw.Body.toString("utf-8"));
  return data;
}

exports.handler = async function (event, context) {
  //get file name
  let eventParams = getParams(event);
  logger.info(
    "bucket: " + eventParams.Bucket + " key: " + eventParams.FileName
  );

  let run = await setUp(eventParams);
  logger.debug("Send Payload: " + run);
  if (run) {
    let recordsToLoad = await getRecords(eventParams);
    logger.debug("records length: " + recordsToLoad.length);
    let response = await processRecords(recordsToLoad);
    const status = response["status"];
    logger.debug("response status: " + status);

    if (status == "200") {
      //success
      let insertParams = {
        TableName: config.Dynamo.etlControlTable,
        Item: {
          SubmissionFileName: eventParams.Key,
          IntakeStatus: "success",
          IDMLoaderStatus: "success",
        },
      };
      await ddb.put(insertParams).promise();
      logger.info('after inserting into DynamoDB when stauscode is 200');
      var snsSuccessParams = {
        Message: eventParams.Key + " is loaded to IDM",
        Subject: "ETL Loader Processing Has Succeeded",
        TopicArn: config.sns.successETLARN,
      };
      await sns.publish(snsSuccessParams).promise();
      logger.info('Published to sns when status is 200');
    } else {
      let insertParams = {
        TableName: config.Dynamo.etlControlTable,
        Item: {
          SubmissionFileName: eventParams.Key,
          IntakeStatus: "error on IDM load",
          IDMLoaderStatus: "error",
        },
      };
      await ddb.put(insertParams).promise();
      var snsFailureParams = {
        Message: eventParams.Key + " failed to load IDM",
        Subject: "ETL Loader Processing Has Failed",
        TopicArn: config.sns.failureETLARN,
      };
      logger.info('after inserting into DynamoDB when stauscode is not 200');
      await sns.publish(snsFailureParams).promise();
      logger.info('Published to sns when status is not 200');
    }
  } else {
    logger.error(" This file failed to load\n it has prior success/mid processing/no intake status");
  }
  return {};
};
