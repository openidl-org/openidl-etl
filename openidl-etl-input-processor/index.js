const Validator = require("jsonschema").Validator;
var v = new Validator();
const sendMessage = require('./sqs-utility').sendMessage
const config = require('./config/config.json')
var schema = require("./schemas/personalAutoPolicyMessage.json");

exports.handler = async function (event, context) {
    let validRecords = []
    let invalidRecords = []
    event.Records.forEach(eventRecord => {
        let record = eventRecord.body
        console.log("We have a new record");
        console.log(record)
        let validationResult = v.validate(record, schema);
        if (validationResult.errors.length > 0) { // error
            let errorRecord = {}
            console.log("Invalid")
            errorRecord.errors = []
            validationResult.errors.forEach(error => {
                let newError = {}
                newError.path = error.path
                newError.message = error.message
                newError.schema = error.schema
                newError.name = error.name
                newError.argument = error.argument
                newError.stack = error.stack
                errorRecord.errors.push(newError)
            })
            errorRecord.instance = record
            invalidRecords.push(errorRecord)
        } else {
            console.log("Valid")
            record.valid = true
            validRecords.push(record)
        }
    });
    if (invalidRecords.length > 0) {
        console.log("Sending invalids")
        for (record of invalidRecords) {
            await sendMessage(record, config.invalidQueueURL)
        }
    }
    if (validRecords.length > 0) {
        console.log("Sending valids")
        for (record of validRecords) {
            await sendMessage(record, config.validQueueURL)
        }
    }
    return {}
}