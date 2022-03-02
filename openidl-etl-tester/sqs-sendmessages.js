var AWS = require("aws-sdk");
// Set the region
AWS.config.update({ region: "us-east-1" });
const config = require("./config/config.json");

const messages = require("./test/samplevalidmessages.json");

var sqs = new AWS.SQS({ apiVersion: "2012-11-05" });
let argv = process.argv.slice(2);
let loopCount = argv[0] ? parseInt(argv[0], 10) : 1;
for (let step = 0; step < loopCount; step++) {
  for (message of messages) {
    let params = {
      MessageBody: JSON.stringify(message),
      QueueUrl: config.inputQueueUrl,
      DelaySeconds: 5,
      MessageAttributes: {},
    };

    sqs.sendMessage(params, (err, data) => {
      if (err) console.log(err, err.stack);
      else console.log(data);
    });
  }
}
