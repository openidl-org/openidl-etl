const fs = require("fs");
const convertToJson =
  require("../openidl-etl-statplan-processor/mho-processor").convertTextRecordsToJson;
const mobileHomeownerConverter =
  require("../openidl-etl-statplan-processor/converters/mobileHomeownerConverter").converter;
const buildPayload =
  require("../openidl-etl-success-processor/load-insurance-data").buildPayload;

const config = require("./config/config.json");

// open text file, read data into record string
// let testPremiumRecordsText = fs.readFileSync(config.inbound, "utf-8");
let testPremiumRecordsText = fs.readFileSync(config.mobileHomeowners.inbound, "utf-8");

// convert record string to JSON
let jsonPremiumRecords = convertToJson(testPremiumRecordsText);

let hdsMobileHomeownerRecords = [];
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

  hdsMobileHomeownerRecords.push(mobileHomeownerConverter(jsonPremiumRecord));
}
console.log("hdsMobileHomeowner length: " + hdsMobileHomeownerRecords.length);
if (hdsMobileHomeownerRecords.length > 0) {
  let premiumPayload = buildPayload(hdsMobileHomeownerRecords);
  fs.writeFileSync(config.mobileHomeowners.outbound, JSON.stringify(premiumPayload));
}
fs.writeFileSync(config.mobileHomeowners.error, JSON.stringify(errorRecords));
