"use strict";

const AWSXRay = require("aws-xray-sdk-core");
const AWS = AWSXRay.captureAWS(require("aws-sdk"));
const responders = require("../helpers/responders");

/**
 * @description Gets a items from dynamodb
 * @since 1.0
 * @author Findlay Clarke <findlayc@aaisonline.com>
 * @returns {* | null} the items from the control table
 */
exports.handler = async (event, context, callback) => {
  console.log(JSON.stringify(event));

  const dynamoDb = new AWS.DynamoDB.DocumentClient();

  const params = {
    TableName: process.env.ETL_CONTROL_TABLE,
  };

  console.log("Sending params to dynamoDB", JSON.stringify(params));

  try {
    const items = (await dynamoDb.scan(params).promise()).Items;

    console.log("Got the items from the db", JSON.stringify(items));
    return callback(null, responders.get2XX(items));
  } catch (e) {
    console.log("failed to get data", e);
    return callback(
      null,
      responders.getNon2XX(500, "An error occured. Please try again later")
    );
  }
};
