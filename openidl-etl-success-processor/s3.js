const config = require("./config/config.json");
var aws = require("aws-sdk");
aws.config.update({
  region: config.region, httpOptions: {
    timeout: config.reqTimeOut
  }
});
const log4js = require("log4js");
const logger = log4js.getLogger('s3');
logger.level = config.logLevel;
const s3 = new aws.S3({ apiVersion: "2006-03-01" });
const ddb = new aws.DynamoDB({ apiVersion: "2012-08-10" });

const params = {
  Bucket: 'aais-dev-openidl-etl-idm-loader-bucket',
  Key: 'sample-data-9001.json'
}



async function home() {
  const raw = await s3.getObject(params).promise();
  const data = raw.Body.toString('utf-8')
  logger.debug('data: ' + data)
}

home()