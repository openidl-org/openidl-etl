//imports node.js filesystem module, which allows for reading and writing files
const fs = require("fs");

const convertToJson =
  require("../openidl-etl-statplan-processor/fo-processor").convertTextRecordsToJson;
const farmownerConverter =
  require("../openidl-etl-statplan-processor/converters/farmownerConverter").converter;
  const buildPayload =
  require("../openidl-etl-success-processor/load-insurance-data").buildPayload;
const config = require("./config/config.json");

// open text file, read data into record string
let testPremiumRecordsText = fs.readFileSync(
  config.farmowners.inbound,
  "utf-8"
);

// convert record string to JSON
let jsonPremiumRecords = convertToJson(testPremiumRecordsText);
let hdsFarmownerRecords = [];
let errorRecords = [];

for (let jsonPremiumRecord of jsonPremiumRecords) {
  // establish id for log statement
  let id;
  if ("policyNumber" in jsonPremiumRecord) {
    id = jsonPremiumRecord.policyNumber;
  }
  if ("claimNumber" in jsonPremiumRecord) {
    id = jsonPremiumRecord.claimNumber;
  }

  console.log(
    "id: " + id + " transactionCode: " + jsonPremiumRecord.transactionCode
  );

  hdsFarmownerRecords.push(farmownerConverter(jsonPremiumRecord));
}
console.log("hdsFarmowner length: " + hdsFarmownerRecords.length);
if (hdsFarmownerRecords.length > 0) {
  let premiumPayload = buildPayload(hdsFarmownerRecords);
  fs.writeFileSync(config.farmowners.outbound, JSON.stringify(premiumPayload));
}
fs.writeFileSync(config.farmowners.error, JSON.stringify(errorRecords));
