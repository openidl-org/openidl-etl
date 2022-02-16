// Import required AWS SDK clients and commands for Node.js
const { SQSClient, SendMessageCommand } = require("@aws-sdk/client-sqs");
const config = require('./config/config.json')

// Set the parameters

module.exports.sendMessage = async function (message, queueURL) {
    const sqsClient = new SQSClient({ reqion: config.region });
    console.log(`queue url=${queueURL}`)
    const params = {
        DelaySeconds: 10,
        MessageAttributes: {},
        MessageBody: JSON.stringify(message),
        QueueUrl: queueURL
    };
    try {
        const data = await sqsClient.send(new SendMessageCommand(params));
        console.log("Success, message sent. MessageID:", data.MessageId);
        return data; // For unit tests.
    } catch (err) {
        console.log("Error", err);
    }
};
