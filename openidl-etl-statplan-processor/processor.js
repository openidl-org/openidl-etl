const schema = require("./schemas/personalAutoStatPlan-premium.json");
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
  
  let records = recordsText.split("\n")
  records.pop() //remove end object
  for (record of records) {
    //if (record)  
      lcl_result = convertTextRecordToJsonUsingSchema(record, schema)
      results.push(lcl_result)
      
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
    if (type == 'date') {
      console.log('type: '+type)
    }


    if (record.length > start) {
      var value = record.substring(start, end).trim();
      if (type == "number") {
        postive = 1
        if (value.charAt(value.length-1) == '}'){
          value = value.slice(0,-1)
          postive=postive*-1
        }
        if (value.charAt(value.length-1) == '{'){
          value = value.slice(0,-1)
        }

        result[field.name] = Number(value)*postive;
        acted = true;
      }
      if (type == "string") {
        result[field.name] = value;
        acted = true;
      }
      if (type =="date"){
        const dateStr = value
        const date = new Date(dateStr)
        const iso = date.toISOString()
        result[field.name] = iso;
        console.log('iso: '+iso)
        acted = true;
      }
      if (!acted) {
        result[field.name] = "Unhandled Data Type in processor.js";
      }
    }
  }
  console.table(result)
  //sleep(1000000)
  return result;
}

exports.process = async function (records) {
  let resultRecords = [];

  // each record is a whole file with many actual individual text records in it.
  records.forEach((inputRecord) => {
    let record = inputRecord.body;
    //console.log("We have a new submission");
    //console.log(record)
    let jsonRecords = convertTextRecordsToJsonUsingSchema(record, schema);
    for (jsonRecord of jsonRecords) {
      //console.log(jsonRecord);
      resultRecords.push(converter(jsonRecord));
    }
  });
  return resultRecords;
};
