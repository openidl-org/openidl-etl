/**
 * @description MDSApi Module. This will have all the API's needed
 * to interact with the backend API's for the Member data submissions
 * @module apis/MDSApi
 * @since 1.0.0
 * @author Findlay Clarke <findlayc@aaisonline.com>
 * @public
 */

import { API } from "aws-amplify";

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
