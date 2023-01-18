const fs = require("fs");
const convertToJson =
  require("../openidl-etl-statplan-processor/com-auto-processor").convertTextRecordsToJson;
const commercialAutoConverter =
  require("../openidl-etl-statplan-processor/converters/commercialAutoConverter").converter;
const buildPayload =
  require("../openidl-etl-success-processor/load-insurance-data").buildPayload;

const config = require("./config/config.json");

// open text file, read data into record string
let testPremiumRecordsText = fs.readFileSync(config.commercialAuto.inbound, "utf-8");

// convert record string to JSON
let jsonPremiumRecords = convertToJson(testPremiumRecordsText);

let hdsCommercialAutoRecords = [];
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

  hdsCommercialAutoRecords.push(commercialAutoConverter(jsonPremiumRecord));
}
console.log("hdsCommercialAuto length: " + hdsCommercialAutoRecords.length);
if (hdsCommercialAutoRecords.length > 0) {
  let premiumPayload = buildPayload(hdsCommercialAutoRecords);
  fs.writeFileSync(config.commercialAuto.outbound, JSON.stringify(premiumPayload));
}
fs.writeFileSync(config.error, JSON.stringify(errorRecords));
