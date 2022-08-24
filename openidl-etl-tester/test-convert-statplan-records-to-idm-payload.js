const fs = require("fs");
const convertToJson =
  require("../openidl-etl-statplan-processor/processor").convertTextRecordsToJson;
const autoConverter =
  require("../openidl-etl-statplan-processor/converters/autoConverter").converter;
const buildPayload =
  require("../openidl-etl-success-processor/load-insurance-data").buildPayload;

//let testPremiumRecordsText = fs.readFileSync('../../con-data/auto_6350.txt', 'utf-8')
//let testPremiumRecordsText = fs.readFileSync('../../con-data/single-policy.txt', 'utf-8')
let testPremiumRecordsText = fs.readFileSync("../../con-data/auto_1000.txt","utf-8");

let jsonPremiumRecords = convertToJson(testPremiumRecordsText);
// console.log(
//   "idm converter| jsonpremium records length: " + jsonPremiumRecords.length
// );
//cat()
let hdsPremiumRecords = [];
let errorRecords = []
for (let jsonPremiumRecord of jsonPremiumRecords) {
  console.table(jsonPremiumRecord)

  let id;
  if ("policyIdentification" in jsonPremiumRecord){id = jsonPremiumRecord.policyIdentification}
  if ("occurrenceIdentification" in jsonPremiumRecord) {id = jsonPremiumRecord.occurrenceIdentification}

  console.log('id: '+id+' transactionCode: '+jsonPremiumRecord.transactionCode)
  //transaction code 8,1 = premium

  hdsPremiumRecords.push(autoConverter(jsonPremiumRecord));

  // if (1==1){
       

  //     try {
            
  //             hdsPremiumRecords.push(autoConverter(jsonPremiumRecord));
            
  //         } catch (error) {
  //           //console.table(jsonPremiumRecords)
  //           console.log("UnLogged Error on: " + jsonPremiumRecord.policyIdentification);
  //           errorRecords.push(jsonPremiumRecord)
  //           console.log(error);
  //         }
  // }
}
console.log('hdsPremium length: '+hdsPremiumRecords.length)
if (hdsPremiumRecords.length > 0) {
  let premiumPayload = buildPayload(hdsPremiumRecords);
  fs.writeFileSync("../../con-data/auto.json", JSON.stringify(premiumPayload));
}
fs.writeFileSync("../../con-data/error.json", JSON.stringify(errorRecords));









//fs.writeFileSync('./test/idmPremiumRecords.json', JSON.stringify(premiumPayload))
// let testLossRecordsText = fs.readFileSync('./test/PA_Loss_Output_v1.1.txt', 'utf-8')
// let jsonLossRecords = convertToJson(testLossRecordsText)
// let hdsLossRecords = []
// for (jsonLossRecord of jsonLossRecords) {
//     hdsLossRecords.push(autoConverter(jsonLossRecord))
// }

// let lossPayload = buildPayload(hdsLossRecords)

// fs.writeFileSync('./test/idmLossRecords.json', JSON.stringify(lossPayload))
