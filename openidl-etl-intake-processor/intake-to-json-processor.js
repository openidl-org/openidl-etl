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
        
        
        if (outputRecord['Organization ID']){
            resultRecord.organizationID = outputRecord['Organization ID']
        }

        if (outputRecord['State']){
            resultRecord.state = outputRecord['State']
        }else {
            resultRecord.state = config.state
        }
        
        if (outputRecord['VIN']){
            resultRecord.vin = outputRecord['VIN']
            resultRecord.vinHash = crypto.createHash('sha256').update(resultRecord.vin).digest('hex')
        }

        if (outputRecord['Transaction Date']){
            resultRecord.transactionDate = outputRecord['Transaction Date']
        }
        
        if (!resultRecord.organizationID) {
            errors.push({ 'type': 'data', 'message': 'missing "Organization ID"', 'record': outputRecord })
            recordError = true
        }
        if (!resultRecord.vin) {
            errors.push({ 'type': 'data', 'message': 'missing "VIN"', 'record': outputRecord })
            recordError = true
        }
        if (!resultRecord.transactionDate) {
            errors.push({ 'type': 'data', 'message': 'missing "Transaction Date"', 'record': outputRecord })
            recordError = true
        }

        console.log('result: ')
        console.table(resultRecord)
       // console.log('errors')
       // console.log(errors)
        console.log('record error: '+recordError)
        if (!recordError) resultRecords.push(resultRecord)
    }
    if (errors.length > 0) {
        return { 'valid': false, 'errors': errors }
    }
    return { 'valid': true, 'records': resultRecords }
}

module.exports.convertToJson = convertToJson