const handleInput = require('../openidl-etl-staging-processor/index').handler

const messages = require('./test/samplemessages.json')

async function processMessages() {
    await handleInput({ Records: messages }, {})
}

processMessages()