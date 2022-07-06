
const handleInput = require('../openidl-etl-success-processor/index').handler

const testMessage = require('../openidl-etl-success-processor/test/sample-data-9001.json')

const messages = [testMessage]

async function processMessages() {
    let wrappedMessages = []
    for (message of messages) {
        let wrappedMessage = { "body": JSON.stringify(message) }
        wrappedMessages.push(wrappedMessage)
    }
    await handleInput({ Records: wrappedMessages }, {})
}

processMessages()





