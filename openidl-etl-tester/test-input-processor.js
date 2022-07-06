const handleInput = require('../openidl-etl-input-processor/index').handler

const testMessage = require('./test/samplevalidmessage.json')

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
