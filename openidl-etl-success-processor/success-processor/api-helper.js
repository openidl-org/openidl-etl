const fetch = require('node-fetch').default;
const log4js = require("log4js");
const config = require('../config/config.json');
const logger = log4js.getLogger('success-processor api-helpers');
logger.level = config.logLevel;
module.exports.login = async (baseURL, username, password) => {
    logger.info("Inside login")
    try {
        let fullUrl = baseURL + "openidl/api/app-user-login"
        let response = await fetch(fullUrl, {
            method: "POST",
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ "username": username, "password": password }),
        });
        if (response.status !== 200) {
            logger.error(response)
            if (response.status !== 504) {
                process.exit(0)
            }
        }
        result = await response.json()
        let userToken = result.result.userToken
        return userToken
    } catch (error) {
        logger.error("Error with login " + error);
        return;
    }
}

module.exports.buildURL = (config, nodeName, service) => {
    return `https://${config[nodeName][service].url}/`

}

