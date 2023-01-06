const loadInsuranceData = require('./load-insurance-data').loadInsuranceData
const config = require('../config/config.json')
const log4js = require("log4js");
const logger = log4js.getLogger('success-processor processor');
logger.level = config.logLevel;
exports.process = async function (records) {
    logger.info("Calling insurance data manager")
    await loadInsuranceData(records)
    return {}
}