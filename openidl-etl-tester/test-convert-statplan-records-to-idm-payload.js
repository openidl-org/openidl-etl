const fs = require('fs')

const convertToJson = require('../openidl-etl-statplan-processor/processor').convertTextRecordsToJson
const autoConverter = require('../openidl-etl-statplan-processor/converters/autoConverter').converter
const buildPayload = require('../openidl-etl-success-processor/load-insurance-data').buildPayload

let testPremiumRecordsText = fs.readFileSync('./test/PA_Premium_Output_v1.1.txt', 'utf-8')
let jsonPremiumRecords = convertToJson(testPremiumRecordsText)
let hdsPremiumRecords = []
for (jsonPremiumRecord of jsonPremiumRecords) {
    hdsPremiumRecords.push(autoConverter(jsonPremiumRecord))
}

let premiumPayload = buildPayload(hdsPremiumRecords)

fs.writeFileSync('./test/idmPremiumRecords.json', JSON.stringify(premiumPayload))

let testLossRecordsText = fs.readFileSync('./test/PA_Loss_Output_v1.1.txt', 'utf-8')
let jsonLossRecords = convertToJson(testLossRecordsText)
let hdsLossRecords = []
for (jsonLossRecord of jsonLossRecords) {
    hdsLossRecords.push(autoConverter(jsonLossRecord))
}

let lossPayload = buildPayload(hdsLossRecords)

fs.writeFileSync('./test/idmLossRecords.json', JSON.stringify(lossPayload))

