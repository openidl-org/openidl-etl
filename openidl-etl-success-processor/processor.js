const loadInsuranceData = require('./load-insurance-data').loadInsuranceData

exports.process = async function (records) {
    let response = await loadInsuranceData(records)
    return response
}