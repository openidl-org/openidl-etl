const config = require("./config/config.json");
var aws = require("aws-sdk");
aws.config.update({
  region: config.region, httpOptions: {
    timeout: config.reqTimeOut
  }
});
const log4js = require("log4js");
const logger = log4js.getLogger('success-processor dynamo');
logger.level = config.logLevel;
//const client = new DynamoDBClient({ region: config.region });
const s3 = new aws.S3({ apiVersion: "2006-03-01" });
const ddb = new aws.DynamoDB({ apiVersion: "2012-08-10" });

async function awaitFunction() {
  const params = {
    TableName: config.Dynamo["etl-control-table"],
    Select: "COUNT",
  };

  const recordCount = await ddb.scan(params).promise();
  //console.table(count)
  logger.info(recordCount.Count + " records found");
  const key = "sample06.csv";
  const params2 = {
    TableName: config.Dynamo["etl-control-table"],
    Key: { SubmissionFileName: { S: key } },
  };
  const item = await ddb.getItem(params2).promise();
  console.table(item);
  //   let params3 = {
  //     TableName: config["etl-control-table"],
  //     Key: { 'SubmissionFileName': { S: key } },
  //     UpdateExpression: "set SubmissionStatus = :st",
  //     ExpressionAttributeValues: {
  //         ":st": { S: 'idmLoader-start' }
  //     },
  //     ReturnValues: "ALL_NEW"
  // }

  //const updateResult = await ddb.updateItem(params3).promise()

  //add new key
  let params4 = {
    TableName: config.Dynamo["etl-control-table"],
    Key: { 'SubmissionFileName': { S: key } },
    UpdateExpression: "set idmLoader = :st",
    ExpressionAttributeValues: {
      ":st": { S: 'idmLoader-start' }
    },
    ReturnValues: "ALL_NEW"
  }
  //start
  let start = {
    TableName: config.Dynamo["etl-control-table"],
    Key: { 'SubmissionFileName': { S: key } },
    UpdateExpression: "set idmLoader = :st",
    ExpressionAttributeValues: {
      ":st": { S: 'idmLoader-start' }
    },
    ReturnValues: "ALL_NEW"
  }

  //succeed
  let succeed = {
    TableName: config.Dynamo["etl-control-table"],
    Key: { 'SubmissionFileName': { S: key } },
    UpdateExpression: "set idmLoader = :st",
    ExpressionAttributeValues: {
      ":st": { S: 'idmLoader-start' }
    },
    ReturnValues: "ALL_NEW"
  }
  //fail
  let fail = {
    TableName: config.Dynamo["etl-control-table"],
    Key: { 'SubmissionFileName': { S: key } },
    UpdateExpression: "set idmLoader = :st",
    ExpressionAttributeValues: {
      ":st": { S: 'idmLoader-start' }
    },
    ReturnValues: "ALL_NEW"

  const updateResult4 = await ddb.updateItem(params4).promise()
  }

  awaitFunction();
