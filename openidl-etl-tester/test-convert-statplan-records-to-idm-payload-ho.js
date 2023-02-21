//imports node.js filesystem module, which allows for reading and writing files
const fs = require("fs");

const convertToJson =
  require("../openidl-etl-statplan-processor/ho-processor").convertTextRecordsToJson;
const homeownerConverter =
  require("../openidl-etl-statplan-processor/converters/homeownerConverter").converter;
  const buildPayload =
  require("../openidl-etl-success-processor/load-insurance-data").buildPayload;
const config = require("./config/config.json");

// open text file, read data into record string
let testPremiumRecordsText = fs.readFileSync(
  config.homeowners.inbound,
  "utf-8"
);

// convert record string to JSON
let jsonPremiumRecords = convertToJson(testPremiumRecordsText);
let hdsHomeownerRecords = [];
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

  hdsHomeownerRecords.push(homeownerConverter(jsonPremiumRecord));
}
console.log("hdsHomeowner length: " + hdsHomeownerRecords.length);
if (hdsHomeownerRecords.length > 0) {
  let premiumPayload = buildPayload(hdsHomeownerRecords);
  fs.writeFileSync(config.homeowners.outbound, JSON.stringify(premiumPayload));
}
fs.writeFileSync(config.homeowners.error, JSON.stringify(errorRecords));
