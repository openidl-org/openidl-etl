const fs = require("fs");
const convertToJson =
  require("../openidl-etl-statplan-processor/dp-processor").convertTextRecordsToJson;
const dwellingPropertiesConverter =
  require("../openidl-etl-statplan-processor/converters/dwellingPropertiesConverter").converter;
const buildPayload =
  require("../openidl-etl-success-processor/load-insurance-data").buildPayload;

const config = require("./config/config.json");

// open text file, read data into record string
// let testPremiumRecordsText = fs.readFileSync(config.inbound, "utf-8");
let testPremiumRecordsText = fs.readFileSync(config.dwellingProperties.inbound, "utf-8");

// convert record string to JSON
let jsonPremiumRecords = convertToJson(testPremiumRecordsText);

let hdsDwellingPropertiesRecords = [];
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

  hdsDwellingPropertiesRecords.push(dwellingPropertiesConverter(jsonPremiumRecord));
}
console.log("hdsDwellingProperties length: " + hdsDwellingPropertiesRecords.length);
if (hdsDwellingPropertiesRecords.length > 0) {
  let premiumPayload = buildPayload(hdsDwellingPropertiesRecords);
  fs.writeFileSync(config.dwellingProperties.outbound, JSON.stringify(premiumPayload));
}
fs.writeFileSync(config.dwellingProperties.error, JSON.stringify(errorRecords));
