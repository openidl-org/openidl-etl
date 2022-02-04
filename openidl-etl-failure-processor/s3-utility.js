// Use this code snippet in your app.
// If you need more information about configurations or implementing the sample code, visit the AWS docs:
// https://aws.amazon.com/developers/getting-started/nodejs/

// Load the AWS SDK
const AWS = require('aws-sdk')
const { S3Client, PutObjectCommand } = require("@aws-sdk/client-s3");
const config = require('./config/config.json')

module.exports.putIntoS3 = async function (key, content) {
    let region = "us-east-1",
        decodedBinarySecret;

    const client = new S3Client({
        region: region
    })
    const command = new PutObjectCommand({ Bucket: config.s3BucketName, Key: key, Body: JSON.stringify(content) })

    // In this sample we only handle the specific exceptions for the 'GetSecretValue' API.
    // See https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
    // We rethrow the exception by default.

    let result = await client.send(command)


    return result
}