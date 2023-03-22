const csv = require('csvtojson');
const config = require('./config/config.json');
const crypto = require('crypto');
const log4js = require('log4js');
const logger = log4js.getLogger();
logger.level = config.logLevel;
const acceptedHeaders = ['Organization ID', 'State', 'Transaction Date', 'VIN Hash', 'VIN', ]// Do NOT change order
let inputHeaders;
async function convertToJson(recordsText) {
  logger.info('Entering convertToJson()');
  let errors = [];
  await csv()
    .on('header',(headers)=>{
      logger.info("Headers: ", headers)
      inputHeaders = headers;
      for (let i = 0; i < headers.length; i = i + 1) {
        if (!acceptedHeaders.includes(headers[i])) {
          errors.push({ type: 'csv parsing', message: "Unknown Header found (" + headers[i] + ")" });
        }
      }

      for (let j = 0; j < 3; j = j + 1) {
        if (!headers.includes(acceptedHeaders[j])) {
          errors.push({ type: 'csv parsing', message: "Header missing (" + acceptedHeaders[j] + ")" });
        }
      }
      if ( !headers.includes(acceptedHeaders[3]) && !headers.includes(acceptedHeaders[4])) {
        errors.push({ type: 'csv parsing', 
          message: "Header missing (" + acceptedHeaders[3] + "/" +acceptedHeaders[4] + ")" });
      }
    }) 
    .on('error', (err) => {
      errors.push({ type: 'csv parsing', message: err });
    })
    .fromString(recordsText)
    .then((jsonObj) => {
      outputRecords = jsonObj;
    });
  logger.debug('Successfully converted csv to json data');
  if (errors.length > 0) {
    logger.error('An error occurred while updating the outputRecords keys', errors)
    const summaryErrorMessage = await createErrorSummary(errors)
    return { valid: false, errors: errors, summaryErrorMessage };
  }
  if (outputRecords.length === 0) {
    return { valid: false, errors: [{ type: 'data', message: 'empty data' }] };
  }
  let resultRecords = [];
  logger.debug('Updating outputRecords keys');
  for (outputRecord of outputRecords) {
    let resultRecord = {};
    let recordError = false;

    if (outputRecord['Organization ID']) {
      resultRecord.organizationID = outputRecord['Organization ID'];
    }

    if (outputRecord['State']) {
      resultRecord.state = outputRecord['State'];
    } else {
      resultRecord.state = config.state;
    }

    if (outputRecord['VIN Hash']) {
      resultRecord.VINHash = outputRecord['VIN Hash'];
    }

    if (!outputRecord['VIN Hash'])
      if (!outputRecord.VIN) {
        errors.push({
          type: 'data',
          message: 'missing VIN',
          record: outputRecord,
        });
      } else {
        resultRecord.VINHash = crypto
          .createHash('sha256')
          .update(outputRecord.VIN)
          .digest('hex');
      }

    if (outputRecord['VIN']) {
      resultRecord.VIN = outputRecord.VIN
    }

    if (outputRecord['Transaction Date']) {
      let dateRecord;
      try {
        dateRecord = new Date(outputRecord['Transaction Date']).toISOString().split('T')[0];
      } catch (err) {
        errors.push({
          type: 'data',
          message: 'invalid Date Format',
          record: outputRecord,
        });
      }
      resultRecord.transactionDate = dateRecord ? dateRecord : outputRecord['Transaction Date'];

    }

    if (!resultRecord.organizationID) {
      errors.push({
        type: 'data',
        message: 'missing Organization ID',
        record: outputRecord,
      });
      logger.error('error on org');
      recordError = true;
    }
    if (!outputRecord.VIN) {
      if (!resultRecord['VINHash']) {
        errors.push({
          type: 'data',
          message: 'missing VIN/Vin Hash',
          record: outputRecord,
        });
        logger.error('error on vin');
        recordError = true;
      }
    }
    if (!resultRecord.transactionDate) {
      errors.push({
        type: 'data',
        message: 'missing Transaction Date',
        record: outputRecord,
      });
      logger.error('error on org');
      recordError = true;
    }

    if (!recordError) resultRecords.push(resultRecord);
  }
  if (errors.length > 0) {
    logger.error('An error occurred while updating the outputRecords keys', errors)
    const summaryErrorMessage = await createErrorSummary(errors)
    return { valid: false, errors: errors, summaryErrorMessage };
  }
  logger.debug('Successfully updated the outputRecords keys')
  return { valid: true, records: resultRecords };
}

async function createErrorSummary(errors) {
  logger.debug("inside createErrorSummary")
  let uniqueErrors = []  
  for (let i = 0; i < errors.length; i = i + 1) {
    if (!uniqueErrors.includes(errors[i].message)) {
      uniqueErrors.push(errors[i].message);
    }
  }
  let errorSummary = "Error(s): ";
  for (let j = 0; j < uniqueErrors.length; j = j + 1) {
    errorSummary = errorSummary + uniqueErrors[j] + ", "
  }
  errorSummary = errorSummary.slice(0, - 2) + "."
  logger.info("Error Summary: ", errorSummary)
  return errorSummary


}
module.exports.convertToJson = convertToJson;
