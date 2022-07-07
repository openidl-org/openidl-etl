// const config = require("./config/config.json");
const source = require("./test/sample-data-9001.json")
const processRecords = require("./processor").process;
console.log(source)

async function process(){
    processRecords(source)
}

process()