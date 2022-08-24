const premiumSchema = require("./schemas/personalAutoStatPlan-premium.json");
const lossSchema = require("./schemas/personalStatPlan-loss.json");
const converter = require("./converters/autoConverter").converter;



module.exports.convertTextRecordsToJson = function (recordsText) {
  return convertTextRecordsToJsonUsingSchema(recordsText,premiumSchema,lossSchema);
};

function convertTextRecordsToJsonUsingSchema(recordsText, premiumSchema,lossSchema) {
  let results = [];
  let uniqueExposures = new Set()
  let records = recordsText.split("\n");
  records.pop(); //remove end object
  for (let record of records) {
    //if (record)
    lcl_result = convertTextRecordToJsonUsingSchema(record, premiumSchema,lossSchema);
    uniqueExposures.add(lcl_result.exposure)
    //uniqueExposures.add(lcl_result.policyIdentification)
    results.push(lcl_result);
  }
  console.log(uniqueExposures)
  return results;
}

function getTransactionCode(record, schema) {
  var start = schema.properties["transactionCode"].start;
  var end = start + 1;
  var transactionCode = record.substring(start, end).trim();
  return transactionCode;
}


function convertTextRecordToJsonUsingSchema(record, premiumSchema,lossSchema) {
  let result = {};
  var transactionCode = getTransactionCode(record,premiumSchema)
  var premium = false;
  let schema = null
  let loss = null
  
  //console.log('transaction code: '+transactionCode)

  if (transactionCode == '1' || transactionCode == '8'){
    premium = true
    schema = premiumSchema
  }

  if (transactionCode =="2" || transactionCode =="3" || transactionCode =="6" || transactionCode =="7") {
    schema = lossSchema
    console.log('loss record found')
    console.log(record)
    loss=true

  }

    for (let fieldName in schema.properties) {
      let field = schema.properties[fieldName];
      var start = field.start;
      var end = start + field.length;
      var type = field.type;
      var acted = false;
      // if (type == 'date') {
      //   console.log('type: '+type)
      // }



      if (record.length > start) {
        var value = record.substring(start, end).trim();
        if (type == "number") {
          positive = 1;
          if (value.charAt(value.length - 1) == "}") {
            value = value.slice(0, -1);
            positive = positive * -10;
          }
          if (value.charAt(value.length - 1) == "{") {
            value = value.slice(0, -1);
          }

          result[field.name] = Number(value) * positive;
          acted = true;
        }
        if (type == "string") {
          result[field.name] = value;
          acted = true;

        }
        if (type == "date") {
          result[field.name] = value;
          acted = true;
        }
        if (!acted) {
          result[field.name] = "Unhandled Data Type in processor.js";
          result["error"] = true;
        }
      }
    }

    
    return result
  }


exports.process = async function (records) {
  let resultRecords = [];

  // each record is a whole file with many actual individual text records in it.
  records.forEach((inputRecord) => {
    let record = inputRecord.body;
    //console.log("We have a new submission");
    //console.log(record)
    let jsonRecords = convertTextRecordsToJsonUsingSchema(record, premiumSchema, lossSchema);
    for (jsonRecord of jsonRecords) {
      //console.log(jsonRecord);
      resultRecords.push(converter(jsonRecord));
    }
  });
  return resultRecords;
};
