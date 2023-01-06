const getCredentials = require('./secrets-utility').getCredentials
const login = require('./api-helper').login
const buildURL = require('./api-helper').buildURL
const fetch = require('node-fetch');
const fs = require('fs')
const config = require('./config/config.json');
const { toUnicode } = require('punycode');
const log4js = require("log4js");
const logger = log4js.getLogger('load-insurance-data');
logger.level = config.logLevel;
module.exports.buildPayload = function (records) {
    let payload = {
        "records": [],
        "sourceId": config.sourceId,
        "batchId": config.batchId,
        "chunkId": config.chunkId,
        //"carrierId": records[0].Policy.CompanyID,
        "carrierId": config.carrierId,
        "policyNo": "91111111",
        "errFlg": false,
        "errrLst": [],
        "SquenceNum": 1001,
        "_id": "9sample"
    }
    for (record of records) {
        //TODO: understand why chunkid is here, also isnt chunkId above?
        record.chunkId = config.chunkId
        record.chunkid = config.chunkId
        payload.records.push(record)
    }
    return payload;
}
async function callAPI(apiUrl, payload, token) {
    logger.info('call api for IDM')
    try {
        let response = await fetch(apiUrl + "openidl/api/load-insurance-data", {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token,
            },
            body: JSON.stringify(payload),
        });
        logger.info('after idm response')
        logger.debug(response)
        return response
    } catch (error) {
        logger.error("Error with post of insurance data " + error);
        return;
    }
}

async function writeInsuranceDataToFileSystem(payload) {
    fs.writeFileSync('payload.json', JSON.stringify(payload))
}


let commandArgs = process.argv.slice(2)
let dryRunArg = commandArgs[0]
let dryRun = (dryRunArg && (dryRunArg === 'dry-run' || dryRunArg === 'true'))

logger.info(commandArgs)
logger.info('dryRun: ' + dryRun)

module.exports.loadInsuranceData = async function (records) {
    logger.info("Inside process records.  " + records.length + " records.")
    let baseURL = buildURL(config, 'carrier', 'utilities')
    logger.debug(`Logging in`)
    // let credentials = await getCredentials()
    let userToken = await login(baseURL, config.username, config.password)
    logger.info(`Status: ${userToken.status}`)
    if (userToken.status == 200){
        baseURL = buildURL(config, 'carrier', 'insurance-data-manager')
        // logger.info(JSON.stringify(dataToLoad))
        let payload = module.exports.buildPayload(records)
        //logger.info('load insurance 79')
        let response = await callAPI(baseURL, payload, userToken.token)
        return response
    }
    const status = {'status': userToken.status}
    return status
}
