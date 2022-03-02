const handleInput = require('../openidl-etl-success-processor/index').handler

const messages = require('./test/samplevalidmessages.json')

async function processMessages() {
    let wrappedMessages = []
    for (message of messages) {
        let wrappedMessage = { "body": JSON.stringify(message) }
        wrappedMessages.push(wrappedMessage)
    }
    await handleInput({ Records: wrappedMessages }, {})
}

processMessages()