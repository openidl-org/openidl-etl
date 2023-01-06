const config = require('./config/config.json')
// Load the AWS SDK for Node.js
var AWS = require('aws-sdk');
// Set the region 
AWS.config.update({
    region: 'us-east-1', httpOptions: {
        timeout: config.reqTimeOut
    }
});
const log4js = require("log4js");
const logger = log4js.getLogger('sqs_listqeues');
logger.level = config.logLevel;
// Create an SQS service object
var sqs = new AWS.SQS({ apiVersion: '2012-11-05' });
var params = {};
sqs.listQueues(params, function (err, data) {
    if (err) {
        logger.error("Error", err);
    } else {
        logger.info("Success", data.QueueUrls);
    }
});