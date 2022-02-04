var AWS = require('aws-sdk');
// Set the region 
AWS.config.update({ region: 'us-east-1' });
const config = require('./config/config.json')

const messages = require('./test/samplemessages.json')

var sqs = new AWS.SQS({ apiVersion: '2012-11-05' })

for (message of messages) {
    let params = {
        MessageBody: JSON.stringify(message),
        QueueUrl: config.stagingQueueUrl,
        DelaySeconds: 5,
        MessageAttributes: {

        }

    }

    sqs.sendMessage(params, (err, data) => {
        if (err) console.log(err, err.stack)
        else console.log(data)
    })
}