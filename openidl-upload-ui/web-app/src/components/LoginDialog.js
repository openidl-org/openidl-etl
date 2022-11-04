import React, { useState } from "react";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import { withStyles } from "@material-ui/core/styles";
import * as MdsApi from "../apis/MdsApi";
import { Typography } from "@material-ui/core";

function LoginDialog(props) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState();

  async function handleLogin() {
    const token = await MdsApi.getUserToken(username, password);
    if (token) {
      props.handleClose();
      setError();
    } else {
      setError("Login failed. Please try again.");
      // setPassword("");
    }
  }

  return (
    <Dialog
      maxWidth="sm"
      fullWidth={true}
      open={true}
      onClose={props.handleClose}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle id="form-dialog-title">Please login</DialogTitle>
      <DialogContent>
        <TextField
          autoFocus
          margin="dense"
          id="username"
          label="Username"
          value={username}
          placeholder="john.doe@example.com"
          type="text"
          fullWidth
          onChange={(event) => setUsername(event.target.value)}
        />
      </DialogContent>
      <DialogContent>
        <TextField
          autoFocus
          margin="dense"
          id="password"
          label="Password"
          value={password}
          type="password"
          fullWidth
          onChange={(event) => setPassword(event.target.value)}
        />
      </DialogContent>
      <DialogActions>
        <Typography align="left" color="error">
          {error}
        </Typography>
        <Button onClick={props.handleClose} color="primary">
          Cancel
        </Button>
        <Button
          variant="contained"
          onClick={() => handleLogin()}
          color="primary"
          disabled={!username || username.length <= 3 || !password}
        >
          Login
        </Button>
      </DialogActions>
    </Dialog>
  );
}

const styles = (theme) => ({
  root: {
    flexGrow: 1,
  },
  container: {
    flexGrow: 1,
    position: "relative",
  },
  paper: {
    position: "absolute",
    zIndex: 1,
    marginTop: theme.spacing(1),
    left: 0,
    right: 0,
  },
  chip: {
    margin: `${theme.spacing(1) / 2}px ${theme.spacing(1) / 4}px`,
  },
  inputRoot: {
    flexWrap: "wrap",
  },
  inputInput: {
    width: "auto",
    flexGrow: 1,
  },
  divider: {
    height: theme.spacing(2),
  },
});

export default withStyles(styles, { withTheme: true })(LoginDialog);
