const fs = require("fs");
const convertToJson =
  require("../openidl-etl-statplan-processor/processor").convertTextRecordsToJson;
const autoConverter =
  require("../openidl-etl-statplan-processor/converters/autoConverter").converter;
const buildPayload =
  require("../openidl-etl-success-processor/load-insurance-data").buildPayload;

const config = require("./config/config.json");

// open text file, read data into record string
let testPremiumRecordsText = fs.readFileSync(config.inbound, "utf-8");

// convert record string to JSON
let jsonPremiumRecords = convertToJson(testPremiumRecordsText);

let hdsAutoRecords = [];
let errorRecords = [];
for (let jsonPremiumRecord of jsonPremiumRecords) {
  // establish id for log statement
  let id;
  if ("policyIdentification" in jsonPremiumRecord) {
    id = jsonPremiumRecord.policyIdentification;
  }
  if ("occurrenceIdentification" in jsonPremiumRecord) {
    id = jsonPremiumRecord.occurrenceIdentification;
  }

  console.log(
    "id: " + id + " transactionCode: " + jsonPremiumRecord.transactionCode
  );

  hdsAutoRecords.push(autoConverter(jsonPremiumRecord));
}
console.log("hdsAuto length: " + hdsAutoRecords.length);
if (hdsAutoRecords.length > 0) {
  let premiumPayload = buildPayload(hdsAutoRecords);
  fs.writeFileSync(config.outbound, JSON.stringify(premiumPayload));
}
fs.writeFileSync(config.error, JSON.stringify(errorRecords));
