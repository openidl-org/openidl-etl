const csv = require('csvtojson')
const DateTime = require('luxon').DateTime
const config = require('./config/config.json')
const crypto = require('crypto')
async function convertToJson(recordsText) {
    // let textRecords = recordsText.split('\n')
    let errors = []
    await csv()
        .on('error', (err) => {
            errors.push({ 'type': 'csv parsing', 'message': err })
        })
        .fromString(recordsText)
        .then((jsonObj) => {
            outputRecords = jsonObj
        })

    if (errors.length > 0) {
        return { 'valid': false, 'errors': errors }
    }
    if (outputRecords.length === 0) {
        return { 'valid': false, 'errors': [{ 'type': 'data', 'message': 'empty data' }] }
    }
    let resultRecords = []
    for (outputRecord of outputRecords) {
        console.log(outputRecord)
        let resultRecord = {}
        let recordError = false
        resultRecord.organizationID = outputRecord['Organization ID']
        resultRecord.state = outputRecord['State']
        if (!resultRecord.state) {
            resultRecord.state = config.state
        }
        resultRecord.vin = outputRecord['VIN']
        resultRecord.transactionDate = outputRecord['Transaction Date']
        resultRecord.transactionMonth = outputRecord['Transaction Month']
        resultRecord.vinHash = crypto.createHash('sha256').update(resultRecord.vin).digest('hex')

        if (!resultRecord.organizationID) {
            errors.push({ 'type': 'data', 'message': 'missing organizationID', 'record': outputRecord })
            recordError = true
        }
        if (!resultRecord.vin) {
            errors.push({ 'type': 'data', 'message': 'missing vin', 'record': outputRecord })
            recordError = true
        }
        if (!resultRecord.transactionDate) {
            errors.push({ 'type': 'data', 'message': 'missing transaction date', 'record': outputRecord })
            recordError = true
        }
        if (!resultRecord.transactionMonth) {
            errors.push({ 'type': 'data', 'message': 'missing transaction month', 'record': outputRecord })
            recordError = true
        }
        console.log('result: ')
        console.log(resultRecord)
        console.log('errors')
        console.log(errors)
        console.log('record error: '+recordError)
        if (!recordError) resultRecords.push(resultRecord)
    }
    if (errors.length > 0) {
        return { 'valid': false, 'errors': errors }
    }
    return { 'valid': true, 'records': resultRecords }
}

module.exports.convertToJson = convertToJson