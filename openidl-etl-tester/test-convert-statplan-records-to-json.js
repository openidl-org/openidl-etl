const fs = require('fs')

const config = {

}

const convertToJson = require('../openidl-etl-statplan-processor/processor').convertTextRecordsToJson

let testPremiumRecordsText = fs.readFileSync('./test/PA_Premium_Output_v1.1.txt', 'utf-8')
let jsonPremiumRecords = convertToJson(testPremiumRecordsText)

fs.writeFileSync('./test/idmJsonPremiumRecords.json', JSON.stringify(jsonPremiumRecords))

let testLossRecordsText = fs.readFileSync('./test/PA_Loss_Output_v1.1.txt', 'utf-8')
let jsonLossRecords = convertToJson(testLossRecordsText)

fs.writeFileSync('./test/idmJsonLossRecords.json', JSON.stringify(jsonLossRecords))

