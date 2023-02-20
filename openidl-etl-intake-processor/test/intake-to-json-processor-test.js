
const fs = require('fs')
const convertToJson = require('../intake-to-json-processor').convertToJson
const test = async () => {
	const inputRecords = fs.readFileSync('DateTest3Invalid.csv', 'utf-8')
		result = await convertToJson(inputRecords)
		console.log(result);
}


test()
