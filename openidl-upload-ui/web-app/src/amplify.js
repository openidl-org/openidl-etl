import Amplify, { Auth } from "aws-amplify";

Amplify.configure({
  Auth: {
    //identityPoolId: process.env.REACT_APP_AMPLIFY_AUTH_IDENTITY_POOL_ID,
    region: process.env.REACT_APP_AMPLIFY_AUTH_REGION,
    userPoolId: process.env.REACT_APP_AMPLIFY_AUTH_USER_POOL_ID,
    userPoolWebClientId:
      process.env.REACT_APP_AMPLIFY_AUTH_USER_POOL_WEB_CLIENT_ID,
    mandatorySignIn: true,
  },
  API: {
    endpoints: [
      {
        name: "MDS",
        region: process.env.REACT_APP_AMPLIFY_API_ENDPOINT_REGION,
        endpoint: process.env.REACT_APP_AMPLIFY_API_ENDPOINT_MDS_URL,
        custom_header: async () => {
          return {
            Authorization: (await Auth.currentSession()).idToken.jwtToken,
          };
        },
      },
    ],
  },
});
