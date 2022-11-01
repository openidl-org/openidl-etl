/**
 * @description MDSApi Module. This will have all the API's needed
 * to interact with the backend API's for the Member data submissions
 * @module apis/MDSApi
 * @since 1.0.0
 * @author Findlay Clarke <findlayc@aaisonline.com>
 * @public
 */

import Amplify, { Auth, API } from "aws-amplify";

export async function getPresignedURL(filename) {
  if (!filename) throw Error("filename is required");

  const path = `/getSignedUrl`;
  const body = {
    filename: filename,
  };

  const init = { body };

  try {
    return await API.post("MDS", path, init);
  } catch (error) {
    console.log(error);
  }
}

export async function getUploadStatus() {
  const path = `/control-table`;
  const body = {};

  const init = { body };

  try {
    return await API.get("MDS", path, init);
  } catch (error) {
    console.log(error);
  }
}

/**
 * Gets the user token and stores it for future calls
 * @returns jwt token
 */
export async function getUserToken() {
  const path = `/app-user-login`;

  const username = prompt("username");
  const password = prompt("password");

  const body = {
    username,
    password,
  };

  const init = { body };

  try {
    const token = (await API.post("IDL_UTIL", path, init)).result.userToken;
    localStorage.setItem("userToken", token);
    return token;
  } catch (error) {
    console.log(error);
  }
}

export async function getDataByCriteria(orgId, month) {
  const path = `/get-insurance-data-by-criteria?organizationId=${orgId}&transactionMonth=${month}`;

  const body = {};

  const init = {
    body,
    headers: {
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    },
  };

  try {
    return await API.get("IDL_IDM", path, init);
  } catch (error) {
    console.log(error);
    getUserToken();
  }
}
