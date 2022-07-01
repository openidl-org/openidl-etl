const convertToJson = require('./intake-to-json-processor').convertToJson
const handler = require('./index').handler
const fs = require('fs')

let args = process.argv.slice(2)
let testname = args[0]

function testConversion() {
    let results = convertToJson("1\n2\n3\n4")
    for (record of results) {
        console.log("Record " + record)
    }
}

async function testProcessS3File() {
    let event = JSON.parse(fs.readFileSync('./test/testS3Event.json'))
    await handler(event, null)
    // for (record of results) {
    //     console.log("Record " + record)
    // }
}

async function testCSVLoad() {
    let csvText = fs.readFileSync('./test/sample01.csv').toString()
    let results = await convertToJson(csvText)
    console.log(results)
}

if (testname === 'testConversion') testConversion()
if (testname === 'testProcessS3File') testProcessS3File()
if (testname === 'testCSVLoad') testCSVLoad()
