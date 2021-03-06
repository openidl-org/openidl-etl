const crypto = require('crypto')
const stateCodes = require('./reference/stateCodes.json')
const transactionCodes = require('./reference/transactionCodes.json')
const programCodes = require('./reference/programCodes.json')
const coverageCodes = require('./reference/coverageCodes.json')
const sublineCodes = require('./reference/sublineCodes.json')
const operatorAgeCodes = require('./reference/operatorAgeCodes.json')
const sexAndMaritalStatusCodes = require('./reference/sexAndMaritalStatusCodes.json')
const vehicleUseCodes = require('./reference/vechicleUseCodes.json')
const vehiclePerformanceCodes = require('./reference/vehiclePerformanceCodes.json')
const privatePassengerDriversTrainingGoodStudentCodes = require('./reference/privatePassengerDriversTrainingGoodStudentCodes.json')
const penaltyPointsCodes = require('./reference/penaltyPointsCodes.json')
const liabilityLimitCodes = require('./reference/liabilityLimitCodes.json')
const deductibleCodes = require('./reference/deductibleCodes.json')
const numberTypeCodes = require('./reference/numberTypeCodes.json')
const vehicleClassCodes = require('./reference/vehicleClassCodes.json')
const umUimCodes = require('./reference/umUimCodes.json')
const terrorismIndicatorCodes = require('./reference/terrorismIndicatorCodes.json')
const singleMultiCarCodes = require('./reference/singleMultiCarCodes.json')
const packageCodes = require('./reference/packageCodes.json')
const poolAffiliationCodes = require('./reference/poolAffiliationCodes.json')
const ncProgramEnhancementCodes = require('./reference/ncProgramEnhancementCodes.json')
const umuimStackingCodes = require('./reference/umuimStackingCodes.json')
const passiveRestraintDiscountCodes = require('./reference/passiveRestraintDiscountCodes.json')
const antiLockBrakesDiscountCodes = require('./reference/antiLockBrakesDiscountCodes.json')
const antiTheftDeviceDiscountCodes = require('./reference/antiTheftDeviceDiscountCodes.json')
const defensiveDriverDiscountCodes = require('./reference/defensiveDriverDiscountCodes.json')
const pipLimitsDeductibleCodes = require('./reference/pipLimitsDeductibleCodes.json')
const experienceRatingModificationFactorCodes = require('./reference/experiencedRatingModificationFactorCodes.json')
const stateExceptionCodes = require('./reference/stateExceptionCodes.json')
const causeOfLossCodes = require('./reference/causeOfLossCodes.json')
const limitedCodingLossTransactionCodes = require('./reference/limitedCodingLossTransactionCodes.json')

const NOT_PROVIDED = 'Not Provided'

function hashString(text) {
    let shasum = crypto.createHash('sha1')
    shasum.update(text)
    return shasum.digest('hex')
};

function convertAccountingDate(dateString) {
    return `202${dateString.substring(2, 3)}-${dateString.substring(0, 2)}-01`
}

function convertAccidentDate(dateString) {
    let year = parseInt(dateString.substring(2, 4)) + 2000
    let thisYear = new Date().getFullYear()
    let century = (thisYear < year ? '19' : '20')
    return `${century}${dateString.substring(2, 4)}-${dateString.substring(0, 2)}-01`
}

function convertDate(dateString) {
    if (!dateString) return ''
    return `${dateString.substring(0, 4)}-${dateString.substring(4, 6)}-${dateString.substring(6)}`
}

convertStringToFloat = (numberString) => {
    let num = numberString.trim()
    if (num.length === 0) {
        return 0
    } else if (num.includes('.')) {
        return parseFloat(num)
    }
    let code = num[num.length - 1]
    let base = num.substring(0, num.length - 1)
    newLastDigit = numberTypeCodes[code].digit
    let multiplier = numberTypeCodes[code].multiplier
    return multiplier * parseFloat(base + newLastDigit) / 100
}

function decodeStateException(outputObject, inputRecord, exceptionName, state) {
    let stateExceptionObject = stateExceptionCodes[exceptionName]
    if (!stateExceptionObject) return
    let stateException = stateExceptionObject[state]
    if (!stateException) return
    if (stateException.type === 'code') object[stateException.hdsName] = stateException.codes[inputRecord[stateException.statPlanName]]
    if (stateException.type === 'copy') object[stateException.hdsName] = inputRecord[stateException.statPlanName]
}

module.exports.converter = function (jsonRecord) {
    let convertedRecord = { "Policy": {}, "Coverage": {}, "Driver": {}, "Vehicle": {}, "Claim": {} }
    let policy = convertedRecord.Policy
    let claim = convertedRecord.Claim
    let coverage = convertedRecord.Coverage
    let driver = convertedRecord.Driver
    let vehicle = convertedRecord.Vehicle
    convertedRecord.createdTime = new Date().toISOString()
    policy.LineOfBusiness = 'Auto'
    policy.Subline = jsonRecord.subline.trim() ? sublineCodes[jsonRecord.subline].name : NOT_PROVIDED
    policy.SublineCategory = jsonRecord.subline.trim() ? sublineCodes[jsonRecord.subline].category : NOT_PROVIDED
    policy.AccountingDate = convertAccountingDate(jsonRecord.accountingDate)
    policy.CompanyCode = jsonRecord.companyCode
    policy.CompanyID = jsonRecord.companyCode
    policy.State = stateCodes.codes[jsonRecord.stateCode]
    policy.Territory = jsonRecord.territory
    policy.OptionalZipCodeIndicator = jsonRecord.optionalZipCodeIndicator
    convertedRecord.RecordType = jsonRecord.transactionCode.trim() ? transactionCodes[jsonRecord.transactionCode].type : NOT_PROVIDED
    convertedRecord.TransactionType = jsonRecord.transactionCode.trim() ? transactionCodes[jsonRecord.transactionCode].name : NOT_PROVIDED
    if (jsonRecord.transactionCode.trim()) {
        if (convertedRecord.RecordType === 'Premium') {
            policy.PremiumAmount = convertStringToFloat(jsonRecord.premiumAmount)
        } else {
            claim.LossAmount = convertStringToFloat(jsonRecord.lossAmount)
        }
    }
    policy.Program = jsonRecord.program.trim() ? programCodes[jsonRecord.program] : NOT_PROVIDED
    let coverageCodesState = coverageCodes[policy.State]
    if (!coverageCodesState) coverageCodesState = coverageCodes['MU']
    coverage.CoverageCategory = coverageCodesState[jsonRecord.coverage].category
    coverage.Coverage = coverageCodesState[jsonRecord.coverage].name
    driver.OperatorAge = operatorAgeCodes[jsonRecord.operatorsAge]
    driver.Gender = jsonRecord.sexAndMaritalStatus.trim() ? sexAndMaritalStatusCodes[jsonRecord.sexAndMaritalStatus].gender : NOT_PROVIDED
    driver.MaritalStatus = jsonRecord.sexAndMaritalStatus.trim() ? sexAndMaritalStatusCodes[jsonRecord.sexAndMaritalStatus].maritalStatus : NOT_PROVIDED
    driver.PrincipalSecondary = jsonRecord.sexAndMaritalStatus.trim() ? sexAndMaritalStatusCodes[jsonRecord.sexAndMaritalStatus].principalSecondary : NOT_PROVIDED
    vehicle.VehicleUse = jsonRecord.vehicleUse.trim() ? vehicleUseCodes[jsonRecord.vehicleUse].use : NOT_PROVIDED
    vehicle.VehicleUseOperator = jsonRecord.vehicleUse.trim() ? vehicleUseCodes[jsonRecord.vehicleUse].operator : NOT_PROVIDED
    vehicle.CommuteDistance = jsonRecord.vehicleUse.trim() ? vehicleUseCodes[jsonRecord.vehicleUse].commuteDistance : NOT_PROVIDED
    vehicle.AnnualDistance = jsonRecord.vehicleUse.trim() ? vehicleUseCodes[jsonRecord.vehicleUse].annualDistance : NOT_PROVIDED
    vehicle.VehiclePerformance = jsonRecord.vehiclePerformance.trim() ? vehiclePerformanceCodes[jsonRecord.vehiclePerformance] : NOT_PROVIDED
    driver.DriversTraining = jsonRecord.privatePassengerDriversTrainingGoodStudent.trim() ? privatePassengerDriversTrainingGoodStudentCodes[jsonRecord.privatePassengerDriversTrainingGoodStudent].driversTraining : NOT_PROVIDED
    driver.GoodStudentDiscount = jsonRecord.privatePassengerDriversTrainingGoodStudent.trim() ? privatePassengerDriversTrainingGoodStudentCodes[jsonRecord.privatePassengerDriversTrainingGoodStudent].goodStudentDiscount : NOT_PROVIDED
    driver.PenaltyPoints = jsonRecord.privatePassengerPenaltyPoints.trim() ? penaltyPointsCodes[jsonRecord.privatePassengerPenaltyPoints] : NOT_PROVIDED
    let liabilityLimitState = liabilityLimitCodes.state[policy.State]
    if (!liabilityLimitState) liabilityLimitState = liabilityLimitCodes.state['MU']
    coverage.LiabilityLimitsName = liabilityLimitState.coverage[jsonRecord.coverage].name
    coverage.LiabilityLimitsAmount = liabilityLimitState.coverage[jsonRecord.coverage][jsonRecord.liabilityLimitsAmount]
    coverage.DeductibleAmount = deductibleCodes[jsonRecord.deductibleAmount]
    policy.EffectiveDate = convertDate(jsonRecord.effectiveDate)
    policy.ExpirationDate = convertDate(jsonRecord.expirationDate)
    vehicle.BodyStyle = vehicleClassCodes.bodyStyle[jsonRecord.bodyStyle]
    vehicle.BodySize = vehicleClassCodes.bodySize[jsonRecord.bodySize]
    vehicle.ModelYear = jsonRecord.modelYear
    let umUimState = umUimCodes.state[policy.State]
    if (!umUimState) umuimState = umUimCodes.state['MU']
    coverage.UninsuredUnderinsuredMotorist = umuimState[jsonRecord.uninsuredUnderinsuredMotorist]
    if (convertedRecord.RecordType === 'Premium') {
        coverage.Exposure = parseInt(jsonRecord.exposure)
        coverage.MonthsCovered = parseInt(jsonRecord.monthsCovered)
        coverage.SingleMultiCarRating = singleMultiCarCodes[jsonRecord.singleMultiCar]
        policy.PolicyIdentifier = jsonRecord.policyIdentification.trim()
        coverage.ExperienceRatingModificationFactor = experienceRatingModificationFactorCodes[jsonRecord.experienceRatingModificationFactor]
    }
    else {
        claim.ClaimCount = parseInt(jsonRecord.claimCount)
        claim.CauseOfLoss = causeOfLossCodes.coverage[jsonRecord.coverage][jsonRecord.causeOfLoss]
        claim.AccidentDate = convertAccidentDate(jsonRecord.accidentDate)
        claim.OccurrenceIdentifier = jsonRecord.occurrenceIdentification.trim()
        claim.ClaimIdentifier = jsonRecord.claimIdentifier.trim()
        claim.LimitedCodingLossTransaction = jsonRecord.limitedCodingLossTransaction.trim() ? limitedCodingLossTransactionCodes[jsonRecord.limitedCodingLossTransaction] : 'N/A'
    }
    coverage.Terrorism = (!jsonRecord.terrorismIndicator.trim() ? 'N/A' : terrorismIndicatorCodes[jsonRecord.terrorismIndicator])
    coverage.Packaging = packageCodes[jsonRecord.packageCode]
    coverage.PoolAffiliation = poolAffiliationCodes[jsonRecord.poolAffiliation]
    policy.NCProgramEnhancement = (jsonRecord.ncProgramEnhancementIndicator.trim() ? ncProgramEnhancementCodes[jsonRecord.ncProgramEnhancementIndicator] : 'N/A')
    policy.ZipCode = jsonRecord.zipCode.trim()
    policy.ZipCodeSuffix = jsonRecord.zipCodeSuffix.trim()
    coverage.UMUIMStacking = (jsonRecord.umUimStackingIndicator ? umuimStackingCodes[jsonRecord.umUimStackingIndicator] : 'N/A')
    vehicle.Symbol = jsonRecord.symbolCode
    coverage.PassiveRestraintDiscount = passiveRestraintDiscountCodes[jsonRecord.passiveRestraintDiscountCode]
    coverage.AntiLockBrakesDiscount = antiLockBrakesDiscountCodes[jsonRecord.antiLockBrakesDiscountCode]
    let antiTheftDeviceDiscountState = antiTheftDeviceDiscountCodes.state[policy.State]
    if (!antiTheftDeviceDiscountState) antiTheftDeviceDiscountState = antiTheftDeviceDiscountCodes.state['MU']
    coverage.AntiTheftDeviceDiscount = antiTheftDeviceDiscountState[jsonRecord.antiTheftDeviceDiscountCode]
    coverage.DefensiveDriverDiscount = defensiveDriverDiscountCodes[jsonRecord.defensiveDriverDiscountCode]
    let pipLimitsDeductibleState = pipLimitsDeductibleCodes.state[policy.State]
    if (!pipLimitsDeductibleState) pipLimitsDeductibleState = pipLimitsDeductibleCodes.state['MU']
    coverage.PIPLimitsDeductible = pipLimitsDeductibleState[jsonRecord.pipLimitsDeductibleCode]
    coverage.RateClassCode = jsonRecord.rateClassCode.trim()
    decodeStateException(coverage, jsonRecord, 'stateExceptionA', policy.State)
    decodeStateException(coverage, jsonRecord, 'stateExceptionB', policy.State)
    decodeStateException(coverage, jsonRecord, 'stateExceptionC', policy.State)
    decodeStateException(coverage, jsonRecord, 'stateExceptionD', policy.State)
    convertedRecord.CompanyUse = jsonRecord.companyUse.trim()
    vehicle.VIN = jsonRecord.vehicleIdentificationVIN.trim()
    vehicle.VINHash = hashString(jsonRecord.vehicleIdentificationVIN)

    return convertedRecord
}


