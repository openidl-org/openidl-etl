const fetch = require('node-fetch').default;
const config = require('./config/config.json');
const log4js = require("log4js");
const logger = log4js.getLogger('api-helpers');
logger.level = config.logLevel;
module.exports.login = async (baseURL, username, password) => {
    logger.info("Inside login")
    try {
        let fullUrl = baseURL + "openidl/api/app-user-login"
        logger.debug("About to send fetch from " + fullUrl)
        let response = await fetch(fullUrl, {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ "username": username, "password": password }),
        });
        logger.debug('Response Status: '+response.status)
        if (response.status !== 200) {
            logger.error('Error on login.')
            return response
        }
        result = await response.json()
        let userToken = {"token": result.result.userToken, "status": 200}
        return userToken
    } catch (error) {
        logger.error("Error with login " + error);
        return;
    }
}

module.exports.buildURL = (config, nodeName, service) => {
    return `https://${config[nodeName][service].url}/`

}

