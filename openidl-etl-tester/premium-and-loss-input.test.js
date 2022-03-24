const Validator = require("jsonschema").Validator;
const fs = require('fs')
const schema = require("../openidl-etl-statplan-processor/schemas/personalAutoStatPlan.json");

const convertToJson = require('../openidl-etl-statplan-processor/processor').convertTextRecordsToJson

test('verfiy input data is correct', () => {
    // let testPremiumRecordsText = fs.readFileSync('./test/paPremiumRecords.txt', 'utf-8')
    let testPremiumRecordsText = fs.readFileSync('./test/PA_Premium_Output_v1.1.txt', 'utf-8')
    let records = convertToJson(testPremiumRecordsText)
    expect(records.length).toBe(10)
    let record = records[0]
    expect(record.lineOfInsurance).toBe('56')
    expect(record.accountingDate).toBe('010')
    expect(record.companyCode).toBe('TRVI')
    expect(record.stateCode).toBe('06')
    expect(record.territory).toBe('00 ')
    expect(record.optionalZipCodeIndicator).toBe('Y')
    expect(record.transactionCode).toBe('1')
    expect(record.premiumAmount).toBe('8967      ')
    expect(record.program).toBe('3')
    expect(record.coverage).toBe('1')
    expect(record.subline).toBe('1')
    expect(record.operatorsAge).toBe('7')
    expect(record.sexAndMaritalStatus).toBe('1')
    expect(record.vehicleUse).toBe('5')
    expect(record.vehiclePerformance).toBe('1')
    expect(record.privatePassengerDriversTrainingGoodStudent).toBe('5')
    expect(record.privatePassengerPenaltyPoints).toBe('0')
    expect(record.liabilityLimitsAmount).toBe('4')
    expect(record.deductibleAmount).toBe('D')
    expect(record.vehicleClass).toBe('081')
    expect(record.bodyStyle).toBe('08')
    expect(record.bodySize).toBe('1')
})


test('verfiy premium input data is correct', () => {
    let v = new Validator();

    let testPremiumRecordsText = fs.readFileSync('./test/PA_Premium_Output_v1.1.txt', 'utf-8')
    let records = convertToJson(testPremiumRecordsText)
    expect(records.length).toBe(10)
    let record = records[0]

    let validationResult = v.validate(record, schema);
    expect(validationResult.errors.length).toBe(0)
})

test('verfiy loss input data is correct', () => {
    let v = new Validator();

    let testLossRecordsText = fs.readFileSync('./test/PA_Loss_Output_v1.1.txt', 'utf-8')
    let records = convertToJson(testLossRecordsText)
    expect(records.length).toBe(10)
    let record = records[0]

    let validationResult = v.validate(record, schema);
    expect(validationResult.errors.length).toBe(0)
})