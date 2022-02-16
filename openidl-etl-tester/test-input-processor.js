const handleInput = require('../openidl-etl-input-processor/index').handler

const messages = require('./test/samplemessages.json')

async function processMessages() {
    let wrappedMessages = []
    for (message of messages) {
        let wrappedMessage = { "body": message }
        wrappedMessages.push(wrappedMessage)
    }
    await handleInput({ Records: wrappedMessages }, {})
}

processMessages()