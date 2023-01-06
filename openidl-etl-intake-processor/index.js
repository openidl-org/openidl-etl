/**
 * index.js
 * 
 * Process csv files from the s3 bucket that is the trigger
 * Put failures into the failure bucket described in the config.json file
 * Put successes into the idm loader bucket also described in the config.json file
 * @author Ken Sayers
 */
const config = require('./config/config.json')
const aws = require('aws-sdk');
aws.config.update({ region: config.region })
const s3 = new aws.S3({ apiVersion: '2006-03-01' });
const ddb = new aws.DynamoDB({ apiVersion: '2012-08-10' })
const convertToJson = require('./intake-to-json-processor').convertToJson
const log4js = require('log4js');
const logger = log4js.getLogger();
logger.level = config.logLevel;

// Handle the s3.putObject event
exports.handler = async (event, context) => {
    // Get the object from the event and show its content type
    const bucket = event.Records[0].s3.bucket.name;
    const key = decodeURIComponent(event.Records[0].s3.object.key.replace(/\+/g, ' ')).split('.')[0];
    const file_ext = decodeURIComponent(event.Records[0].s3.object.key.replace(/\+/g, ' ')).split('.')[1];

    const params = {
        Bucket: bucket,
        Key: key,
    };

    // Setup SNS parameters for success and failure
    try {
        var sns = new aws.SNS();
        var snsFailureParams = {
            Message: 'unknown',
            Subject: 'ETL Intake Processing Has Failed',
            TopicArn: config.sns.failureTopicARN
        };
        var snsSuccessParams = {
            Message: 'unknown',
            Subject: 'ETL Intake Processing Has Succeeded',
            TopicArn: config.sns.successTopicARN
        };

        // check if already succeeded.  fail if so
        let dbParams = {
            TableName: config.dynamoDB.tableName,
            Key: { 'SubmissionFileName': { S: key } }
        }
        const item = await ddb.getItem(dbParams).promise()

        // if the status is already success, fail gracefully
        let result
        if (item && item.Item && (item.Item.IntakeStatus.S === 'success')) {
            snsFailureParams.Message = `the file ${key} has already been processed successfully`
            const snsResponse = await sns.publish(snsFailureParams).promise();
            logger.debug('sns response: ', snsResponse)
            throw new Error('file has already been processed successfully')
        }

        // setup an initial item for the file in the control db
        dbParams = {
            TableName: config.dynamoDB.tableName,
            Item: { 'SubmissionFileName': { S: key }, 'IntakeStatus': { S: 'submitted' } }
        }
        await ddb.putItem(dbParams).promise()
        const getParams = { 'Bucket': params.Bucket, 'Key': params.Key + '.' + file_ext }
        logger.debug('get params: ', getParams)
        const data = await s3.getObject(getParams).promise();
        logger.debug('Successfullty got data from s3 bucket for key ', params.Key)
        // get the csv data and convert it into a json file
        if (!result) result = await convertToJson(data.Body.toString())
        logger.debug('Result status: ', result.valid);
        if (result.valid) {
            // register success in control db, put result into s3 and send message
            let s3Result = await s3.putObject({ Bucket: config.successBucket.name, Key: key + '.json', Body: JSON.stringify(result.records) }).promise();
            dbParams = {
                TableName: config.dynamoDB.tableName,
                Key: { 'SubmissionFileName': { S: key } },
                UpdateExpression: 'set IntakeStatus = :st',
                ExpressionAttributeValues: {
                    ':st': { S: 'success' }
                },
                ReturnValues: 'ALL_NEW'
            }
            await ddb.updateItem(dbParams).promise()
            snsSuccessParams.Message = `The file ${key} has been processed successfully`
            await sns.publish(snsSuccessParams).promise();
            logger.debug('Successfully published to sns');
            return JSON.stringify(s3Result)
        } else {
            // register failure in control db, put result into failure s3 and send message
            let s3Result = await s3.putObject({ Bucket: config.failureBucket.name, Key: key + '-failure.json', Body: JSON.stringify(result) }).promise();
            dbParams = {
                TableName: config.dynamoDB.tableName,
                Key: { 'SubmissionFileName': { S: key } },
                UpdateExpression: 'set IntakeStatus = :st',
                ExpressionAttributeValues: {
                    ':st': { S: 'failure' }
                },
                ReturnValues: 'ALL_NEW'
            }
            await ddb.updateItem(dbParams).promise()
            snsFailureParams.Message = `The file ${key}.${file_ext} has failed processing`
            await sns.publish(snsFailureParams).promise();
            logger.debug('Successfully published to sns');
            return JSON.stringify(s3Result)
        }
    } catch (err) {
        logger.error(err);
        const message = `Error processing ${key} from bucket ${bucket}. Error: ${err}`;
        logger.error(message);
        throw new Error(message);
    }
};