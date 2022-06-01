const schema = require("./schemas/personalAutoStatPlan.json");
const converter = require("./converters/autoConverter").converter;


function sleep(ms) {
  return new Promise((resolve) => {
    setTimeout(resolve, ms);
  });
}

module.exports.convertTextRecordsToJson = function (recordsText) {
  return convertTextRecordsToJsonUsingSchema(recordsText, schema);
};

function convertTextRecordsToJsonUsingSchema(recordsText, schema) {
  let results = [];
  let records = recordsText.split("\n");
  for (record of records) {
    if (record)
      results.push(convertTextRecordToJsonUsingSchema(record, schema));
  }
  return results;
}
function convertTextRecordToJsonUsingSchema(record, schema) {
  let result = {};
  for (let fieldName in schema.properties) {
    let field = schema.properties[fieldName];
    var start = field.start;
    var end = start + field.length;
    var type = field.type;
    var acted = false;
    //console.log('type: '+type)
    if (record.length > start) {
      var value = record.substring(start, end).trim();
      //console.log(' type: '+ type +' fieldname: '+fieldName+" value: "+value)
      if (type == "number") {
        console.log('number: '+fieldName+ ' value: '+value)
        result[field.name] = Number(value);
        acted = true;
        // sleep(100000)
      }
      if ((type == "string")) {
        result[field.name] = value;
        acted = true;
      }
      if (!acted) {
        result[field.name] = "Unhandled Data Type in processor.js";
      }
    }
  }
  console.table(result)
  sleep(100000)
  return result;
}

exports.process = async function (records) {
  let resultRecords = [];
  // each record is a whole file with many actual individual text records in it.
  records.forEach((inputRecord) => {
    let record = inputRecord.body;
    // console.log("We have a new submission");
    // console.log(record)
    let jsonRecords = convertTextRecordsToJsonUsingSchema(record, schema);
    for (jsonRecord of jsonRecords) {
      console.log(jsonRecord);
      resultRecords.push(converter(jsonRecord));
    }
  });
  return resultRecords;
};
