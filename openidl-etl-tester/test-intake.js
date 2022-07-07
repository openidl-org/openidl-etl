const convertToJson = require('../openidl-etl-intake-processor/intake-to-json-processor').convertToJson
const handler = require('../openidl-etl-intake-processor/index').handler
const fs = require('fs')

let args = process.argv.slice(2)
let testname = args[0]

async function testConversion() {
    let results = await convertToJson(fs.readFileSync('../openidl-etl-intake-processor/test/sample01.csv').toString())
    for (record of results.records) {
        console.log("Record " + JSON.stringify(record))
    }
}

async function testProcessS3File() {
    let event = JSON.parse(fs.readFileSync('../openidl-etl-intake-processor/test/testS3Event.json'))
    await handler(event, null)
    // for (record of results) {
    //     console.log("Record " + record)
    // }
}

async function testCSVLoad() {
    let csvText = fs.readFileSync('../openidl-etl-intake-processor/test/sample01.csv').toString()
    let results = await convertToJson(csvText)
    console.log(results)
}

switch (testname) {
    case 'testConversion':
        testConversion()
        break
    case 'testProcessS3File':
        testProcessS3File()
        break
    case 'testCSVLoad':
        testCSVLoad()
        break
    default:
        console.log("===========================================================")
        console.log("usage: node test-intake.js <testname>")
        console.log("testname = testConversion | testProcessS3File | testCSVLoad")
        console.log("===========================================================")
}
