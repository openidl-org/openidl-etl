import React from "react";
import { withAuthenticator } from "aws-amplify-react";
import { MuiThemeProvider, createMuiTheme } from "@material-ui/core/styles";
import { AaisTheme } from "./styles/AaisTheme";
import { AmplifyTheme } from "./styles/AmplifyTheme";
import { AmplifySignUpConfig } from "./config/AmplifySignupConfig";
import { HashRouter, Route, Switch, Redirect } from "react-router-dom";

import UploadScreen from "./screens/UploadScreen";
import SearchScreen from "./screens/SearchScreen";

require("./amplify");
function App(props) {
  return (
    <MuiThemeProvider theme={createMuiTheme(AaisTheme)}>
      <HashRouter>
        <Switch>
          <Route exact path="/">
            <Redirect to="/search" component={SearchScreen} />
          </Route>
          <Route exact path="/search" component={SearchScreen} />
          <Route exact path="/upload" component={UploadScreen} />
        </Switch>
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
