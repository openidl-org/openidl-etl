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

let policy = 0
let claim = 0
let target = 101
final_results = {'records': []}
for (let jsonPremiumRecord of jsonPremiumRecords){
    let id;
    if ("policyIdentification" in jsonPremiumRecord) {
      id = jsonPremiumRecord.policyIdentification;
      policy +=1
      if (policy < target){
        final_results['records'].push(jsonPremiumRecord)
      }
      
    }
    if ("occurrenceIdentification" in jsonPremiumRecord) {
      id = jsonPremiumRecord.occurrenceIdentification;
      claim +=1
      if (claim < target){
        final_results['records'].push(jsonPremiumRecord)
      }
    }

}
  

fs.writeFileSync(config.personalAuto.outbound2, JSON.stringify(final_results));