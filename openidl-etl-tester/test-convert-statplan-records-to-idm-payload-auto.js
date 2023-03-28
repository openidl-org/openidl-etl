const fs = require("fs");
const convertToJson =
  require("../openidl-etl-statplan-processor/personal-auto-processor").convertTextRecordsToJson;
const autoConverter =
  require("../openidl-etl-statplan-processor/converters/personalAutoConverter").converter;
const buildPayload =
  require("../openidl-etl-success-processor/load-insurance-data").buildPayload;

const config = require("./config/config.json");

// open text file, read data into record string
let testPremiumRecordsText = fs.readFileSync(config.personalAuto.inbound, "utf-8");

// convert record string to JSON
let jsonPremiumRecords = convertToJson(testPremiumRecordsText);

let hdsAutoRecords = [];
let errorRecords = [];
for (let jsonPremiumRecord of jsonPremiumRecords) {
  // establish id for log statement
  let id;
  if ("policyIdentification" in jsonPremiumRecord) {
    id = jsonPremiumRecord.policyIdentification;
  } else if ("occurrenceIdentification" in jsonPremiumRecord) {
    id = jsonPremiumRecord.occurrenceIdentification;
  } else {
    id = 'undefined'
    // console.log('Undefined Record Found')
  }

  console.log(
    "id: " + id + " transactionCode: " + jsonPremiumRecord.transactionCode
  );

  // if (id == '8a896f5a8804e3') {
  //   console.log(jsonPremiumRecord)
  //   d()
  // }
  hdsAutoRecords.push(autoConverter(jsonPremiumRecord));
}
console.log("hdsAuto length: " + hdsAutoRecords.length);
if (hdsAutoRecords.length > 0) {
  let premiumPayload = buildPayload(hdsAutoRecords);
  fs.writeFileSync(config.personalAuto.outbound, JSON.stringify(premiumPayload));
}
fs.writeFileSync(config.personalAuto.error, JSON.stringify(errorRecords));
