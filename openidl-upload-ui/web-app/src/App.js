import React from "react";
import { withAuthenticator } from "aws-amplify-react";
import { MuiThemeProvider, createMuiTheme } from "@material-ui/core/styles";
import { AaisTheme } from "./styles/AaisTheme";
import { AmplifyTheme } from "./styles/AmplifyTheme";
import { AmplifySignUpConfig } from "./config/AmplifySignupConfig";
import { HashRouter } from "react-router-dom";

import UploadScreen from "./screens/UploadScreen";

require("./amplify");
function App(props) {
  return (
    <MuiThemeProvider theme={createMuiTheme(AaisTheme)}>
      <HashRouter>
        <UploadScreen />
      </HashRouter>
    </MuiThemeProvider>
  );
}

export default withAuthenticator(
  App,
  false,
  [],
  undefined,
  AmplifyTheme,
  AmplifySignUpConfig
);
