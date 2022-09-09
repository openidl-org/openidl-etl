/**
 * @description This is the Bucket detailes and record files screen.
 * @author Findlay Clarke <findlayc@aaisonline.com>
 * @author Reza Khazali <rezak@aaisonline.com>
 * @since 1.0.0
 * @module screens/BucketScreen
 */
import React, { useState, useEffect, useRef } from "react";
import { makeStyles } from "@material-ui/core/styles";
import { useParams } from "react-router-dom";
import * as MdsApi from "../apis/MdsApi";
import LinearProgress from "@material-ui/core/LinearProgress";
import Container from "@material-ui/core/Container";
import { Button, Paper, Typography } from "@material-ui/core";
import Grid from "@material-ui/core/Grid";

const DEFAULT_TIMEOUT = 1000 * 5;

export default function BucketScreen(props) {
  const classes = useStyles();
  const [loading, setLoading] = useState(false);
  const uploadRef = useRef();

  //used for the polling of the file status
  const timeout = useRef(DEFAULT_TIMEOUT);
  const [randomNumber, setRandomNumber] = useState(Math.random());

  //exponential backoff to check for changes
  useEffect(() => {
    const timer = setTimeout(() => {
      setRandomNumber(Math.random());
      // getBucketFiles();
    }, timeout.current);

    return () => clearTimeout(timer);
  }, [randomNumber]);

  function handleStartLoading(turnOn) {
    if (turnOn) setLoading(true);
    else setLoading(false);
  }

  function resetPolling() {
    timeout.current = DEFAULT_TIMEOUT; //reset the time everytime you upload a new file
    setRandomNumber(Math.random());
  }

  function handleFileUploadProgress(e) {
    console.log("upload progress", e);
  }

  function uploadFileClicked() {
    uploadRef.current.click();
  }

  async function handleFileChanged(e) {
    setLoading(true);

    const file = uploadRef.current.files[0];

    //reset the file so that you can upload the same file again
    e.target.value = null;

    const data = await MdsApi.getPresignedURL(file.name);

    const formData = new FormData();
    formData.append("Content-Type", file.type);
    Object.entries(data.fields).forEach(([key, value]) => {
      formData.append(key, value);
    });

    formData.append("file", file); //must be the last one

    try {
      //upload the file
      const request = new XMLHttpRequest();
      request.open("POST", data.url);

      // upload progress event
      request.upload.addEventListener("progress", (event) => {
        // upload progress as percentage
        const progress = (event.loaded / event.total) * 100;
        //TODO show the progress on the screen
      });

      request.addEventListener("load", (event) => {
        console.log("load", event);
        // HTTP status message (200, 404 etc)
        console.log(request.status);
        if (request.status >= 400) {
          alert(`Upload Failed`);
        }
      });

      request.addEventListener("abort", (event) => {
        console.log("abort", event);
      });

      request.addEventListener("loadstart", (event) => {
        console.log("loadstart", event);
      });

      request.addEventListener("loadend", (event) => {
        console.log("loadend", event);
      });

      request.addEventListener("error", (event) => {
        console.log("error", event);
      });

      // send POST request to server
      request.send(formData);

      resetPolling();
    } catch (error) {
      console.error("error uploading file", error);
    } finally {
      setLoading(false);
    }
  }

  return (
    <React.Fragment>
      <Container className={classes.container} maxWidth={false}>
        {loading && <LinearProgress />}
        <Typography variant="h1">Upload</Typography>
      </Container>
      <Container className={classes.container} maxWidth={false}>
        <Paper className={classes.paper} elevation={3}>
          <Grid container justify="space-between" alignItems="center">
            <Grid item>
              <Typography variant="h5">Files </Typography>
            </Grid>
            <Grid item>
              <Button
                variant="contained"
                color="primary"
                onClick={uploadFileClicked}
              >
                Upload File
              </Button>
            </Grid>
          </Grid>
        </Paper>
      </Container>

      <input
        type="file"
        hidden
        ref={uploadRef}
        onChange={handleFileChanged}
        onProgress={handleFileUploadProgress}
        accept=".txt"
      ></input>
    </React.Fragment>
  );
}

const useStyles = makeStyles((theme) => ({
  container: {
    marginTop: theme.spacing(3),
  },
  paper: {
    padding: theme.spacing(1),
  },
  titles: {
    color: theme.palette.grey[500],
  },
  bucketName: {
    cursor: "pointer",
  },
}));
