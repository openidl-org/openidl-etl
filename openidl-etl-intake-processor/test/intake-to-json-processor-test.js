
const fs = require('fs')
const convertToJson = require('../intake-to-json-processor').convertToJson
const test = async () => {
	let inputRecords = fs.readFileSync('DateTest1.csv', 'utf-8')
	result = await convertToJson(inputRecords)
	console.log("First test: Should have dates in 'yyyy-MM-dd' format")
	console.log(result); // Should have dates in "yyyy-MM-dd" format
	inputRecords = fs.readFileSync('DateTest2.csv', 'utf-8')
	result = await convertToJson(inputRecords)
	console.log("Second test: Should have dates in''yyyy-MM-dd' format")
	console.log(result); // Should have dates in "yyyy-MM-dd" format
	inputRecords = fs.readFileSync('DateTest3Invalid.csv', 'utf-8')
	result = await convertToJson(inputRecords)
	console.log("Third test: Should have invalid date format error")
	console.log(result); // should have errors
	inputRecords = fs.readFileSync('InvalidInput.csv', 'utf-8')
	result = await convertToJson(inputRecords)
	console.log("Third test: Should have several errors")
	console.log(result); // should have errors
	inputRecords = fs.readFileSync('vinTrim.csv', 'utf-8')
	result = await convertToJson(inputRecords)
	console.log("vin trim test: Should have no error")
	console.log(result); // should have errors
	inputRecords = fs.readFileSync('csvTest1.csv', 'utf-8')
	result = await convertToJson(inputRecords)
	console.log("fourth test: Should have csv errors")
	console.log(result); // should have errors
	inputRecords = fs.readFileSync('csvTest2.csv', 'utf-8')
	result = await convertToJson(inputRecords)
	console.log("fifth test: Should have several errors")
	console.log(result); // should have errors
	inputRecords = fs.readFileSync('csvTest3.csv', 'utf-8')
	result = await convertToJson(inputRecords)
	console.log("sixth test: Should have several errors")
	console.log(result); // should have errors
}


test()
