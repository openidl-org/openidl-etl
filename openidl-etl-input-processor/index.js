const Validator = require("jsonschema").Validator;
var v = new Validator();
const sendMessage = require('./sqs-utility').sendMessage
const config = require('./config/config.json')
var schema = require("./schemas/personalAutoPolicyMessage.json");

exports.handler = async function (event, context) {
    let validRecords = []
    let invalidRecords = []
    event.Records.forEach(eventRecord => {
        let record = JSON.parse(eventRecord.body)
        console.log("We have a new record");
        console.log(record)
        if (record.system && record.system.schemaValidation && record.system.schemaValidation.validated === 'Yes') {
            if (record.system.schemaValidation.valid === 'No') {
                invalidRecords.push(record)
            } else {
                console.log("Valid")
                validRecords.push(record)
            }
        } else {
            let validationResult = v.validate(record, schema);
            if (!record.system) record.system = {}
            if (!record.system.schemaValidation) record.system.schemaValidation = {}
            if (validationResult.errors.length > 0) { // error
                let errorRecord = {}
                console.log("Invalid")
                record.system.schemaValidation.errors = []
                validationResult.errors.forEach(error => {
                    let newError = {}
                    newError.path = error.path
                    newError.message = error.message
                    newError.schema = error.schema
                    newError.name = error.name
                    newError.argument = error.argument
                    newError.stack = error.stack
                    record.system.schemaValidation.errors.push(newError)
                    console.log(`  Error: ${error.path} :: ${error.message}`)
                })
                record.system.schemaValidation.validated = 'Yes'
                record.system.schemaValidation.valid = 'No'
                invalidRecords.push(record)
            } else {
                record.system.schemaValidation.validated = 'Yes'
                record.system.schemaValidation.valid = 'Yes'
                console.log("Valid")
                validRecords.push(record)
            }
        }
    });
    if (invalidRecords.length > 0) {
        console.log("Sending invalids")
        for (record of invalidRecords) {
            await sendMessage(record, config.failureQueueURL)
        }
    }
    if (validRecords.length > 0) {
        console.log("Sending valids")
        for (record of validRecords) {
            if (record.system && record.system.ruleValidation && record.system.ruleValidation.validated === 'Yes') {
                if (record.system.schemaValidation.validated === 'Yes') {
                    await sendMessage(record, config.successQueueURL)
                } else {
                    await sendMessage(record, config.failureQueueURL)

                }
            } else {
                await sendMessage(record, config.stagingQueueURL)
            }
        }
    }
    return {}
}