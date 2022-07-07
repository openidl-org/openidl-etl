const processRecords = require("./processor").process;

const config = require("./config/config.json");
var aws = require("aws-sdk");
aws.config.update({ region: "us-east-2" });
const s3 = new aws.S3({ apiVersion: "2006-03-01" });
const ddb = new aws.DynamoDB.DocumentClient();

function getParams(event) {
  // Get the object from the event and show its content type
  // console.log("event: ");
  // console.log(event['Records'])
  const bucket = event.Records[0].s3.bucket.name;
  const key = decodeURIComponent(
    event.Records[0].s3.object.key.replace(/\+/g, " ")
  );
  const params = {
    Bucket: bucket,
    Key: key,
  };
  return params;
}

async function updateStatus(key, status) {
  let start = {
    TableName: config.Dynamo["etl-control-table"],
    Key: { SubmissionFileName: { S: key } },
    UpdateExpression: "set idmLoader = :st",
    ExpressionAttributeValues: {
      ":st": { S: status },
    },
    ReturnValues: "ALL_NEW",
  };

  await ddb.updateItem(start).promise();
}

async function setUp(eventParams) {
  let run = true;
  const params2 = {
    TableName: config["Dynamo"]["etl-control-table"],
    Key: { SubmissionFileName: eventParams.Key },
  };
  // console.log("params2");
  // console.log(params2);
  // console.log("get item");
  let item = await ddb.get(params2).promise();
  console.log("item next");
  console.log(item.Item);
  if (item.Item.SubmissionFileName===eventParams.Key) {
    console.log("file exists in control");

    if (item.Item.IDMLoaderStatus === "success") {
      run = false;
      console.log('file found status: sucesss')
    }

    if (item.Item.IDMLoaderStatus === "submitted") {
      run = false;
      console.log('file found status: submitted')
    }

    //updateStatus(eventParams.Key,'submitted')
  }
  if (!item.Item.SubmissionFileName===eventParams.Key) {
    console.log("file DNE in control");
    //add submitted record
    let insertParams = {
      TableName: config.dynamoDB.tableName,
      Item: {
        SubmissionFileName: eventParams.Key ,
        IDMLoaderStatus: "submitted" },
      }
    await ddb.putItem(insertParams).promise();
  }
  return run;
}

async function getRecords(eventParams) {
  // console.log('get records, params: ')
  // console.log(eventParams)
  const raw = await s3.getObject(eventParams).promise();
  const data = raw.Body.toString("utf-8");
  // console.log('data: ')
  // console.log(data)
  return data;
}

exports.handler = async function (event, context) {
  // let recordsToLoad = []
  // event.Records.forEach(recordToLoad => {
  //     console.log("We have a new record");
  //     const { body } = record;
  //     recordsToLoad.push(JSON.parse(body))
  //     console.log(`ETL ID: ${JSON.parse(body).EtlID}`)
  //     console.log(body)
  // });

  //get file name

  let eventParams = getParams(event);
  //console.log("bucket: " + eventParams.Bucket + " key: " + eventParams.Key);

  let run = await setUp(eventParams);
  if (run) {
    let records = await getRecords(eventParams);
    console.log("records: " + records.length);
    console.log(records)
    let response = await processRecords(recordsToLoad)
    const status = response[todo]['status']

    if (status == '200'){
      //success
      await updateStatus(eventParams.Key,'success')
    }
    else(
      //failure
      await updateStatus(eventParams.Key,'failure')
    )
    

  } else {
    console.log(' This file has sucess or a record mid processing')
  }

  //make payload
  //submit payload
  //add new record

  //await processRecords(recordsToLoad)
  return {};
};
