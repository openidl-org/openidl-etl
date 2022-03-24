const fs = require('fs')
const { handler } = require('../openidl-etl-statplan-processor/index')
const loadInsuranceData = require('../openidl-etl-success-processor/processor').process

const testPremiumInputFileName = './test/testPAPremiumRecords.txt'
const testLossInputFileName = './test/testPALossRecords.txt'

// const testFileNames = ['./test/testPAPremiumRecords_CAR1.txt', './test/testPAPremiumRecords_CAR2.txt', './test/testPAPremiumRecords_TRVI.txt']
const testFileNames = ['./test/PA_Loss_Output_v1.1.txt', './test/PA_Premium_Output_v1.1.txt']

let args = process.argv.slice(2)
console.log('args: ' + args)

// let testPremiumRecordsText = fs.readFileSync(testPremiumInputFileName, 'utf-8')
// let testLossRecordsText = fs.readFileSync(testLossInputFileName, 'utf-8')

async function processRecords(records) {
    let event = { Records: records }
    let results = await handler(event, {})
    console.log(`Record count: ${results.length}`)
    console.log(results[0])
    if (args && args[0] !== 'dryrun') {
        loadInsuranceData(results)
    }
}

for (fileName of testFileNames) {
    let records = []
    let testPremiumRecordsText = fs.readFileSync(fileName, 'utf-8')
    records.push({ body: testPremiumRecordsText })
    // records.push({ body: testLossRecordsText })
    processRecords(records)
}
