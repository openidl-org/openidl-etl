const fs = require("fs");
const convertToJson =
  require("../openidl-etl-statplan-processor/processor").convertTextRecordsToJson;
const autoConverter =
  require("../openidl-etl-statplan-processor/converters/autoConverter").converter;
const buildPayload =
  require("../openidl-etl-success-processor/load-insurance-data").buildPayload;

const config = require("./config/config.json");
let testPremiumRecordsText = fs.readFileSync(config.inbound, "utf-8");

let jsonPremiumRecords = convertToJson(testPremiumRecordsText);

let hdsPremiumRecords = [];
let errorRecords = [];
for (let jsonPremiumRecord of jsonPremiumRecords) {
  console.table(jsonPremiumRecord);

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

  hdsPremiumRecords.push(autoConverter(jsonPremiumRecord));
}
console.log("hdsPremium length: " + hdsPremiumRecords.length);
if (hdsPremiumRecords.length > 0) {
  let premiumPayload = buildPayload(hdsPremiumRecords);
  fs.writeFileSync(config.outbound, JSON.stringify(premiumPayload));
}
fs.writeFileSync(config.error, JSON.stringify(errorRecords));
