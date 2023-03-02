/**
 * @description This checks the progress of the upload and allows uploading of
 * files
 * @author Findlay Clarke <findlayc@aaisonline.com>
 * @since 1.0.0
 * @module screens/UploadScreen
 */
import React, { useState, useEffect, useRef } from "react";
import { makeStyles } from "@material-ui/core/styles";
import { useParams } from "react-router-dom";
import * as MdsApi from "../apis/MdsApi";
import LinearProgress from "@material-ui/core/LinearProgress";
import Container from "@material-ui/core/Container";
import { Button, Paper, Typography } from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TablePagination from "@material-ui/core/TablePagination";
import TableRow from "@material-ui/core/TableRow";

const DEFAULT_TIMEOUT = 1000 * 5;

const columns = [
  { id: "SubmissionFileName", label: "Submission File Name", minWidth: 170 },
  { id: "IDMLoaderStatus", label: "IDM Loader Status", minWidth: 100 },
  {
    id: "IntakeStatus",
    label: "Intake Status",
  },
  {
    id: "UploadStatusDetail",
    label: "Detail",
    minWidth: 100,
  },
];

export default function UploadScreen(props) {
  const classes = useStyles();
  const [loading, setLoading] = useState(true);
  const uploadRef = useRef();
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);
  const [results, setResults] = useState([]);

  //used for the polling of the file status
  const timeout = useRef(DEFAULT_TIMEOUT);
  const [randomNumber, setRandomNumber] = useState(Math.random());

  //exponential backoff to check for changes
  useEffect(() => {
    const timer = setTimeout(() => {
      setRandomNumber(Math.random());
      getResults();
    }, timeout.current);

    return () => clearTimeout(timer);
  }, [randomNumber]);

  async function getResults() {
    setLoading(true);
    const data = await MdsApi.getUploadStatus();
    setResults(data);
    setLoading(false);
  }

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

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
        <Paper className={classes.root}>
          <TableContainer className={classes.container}>
            {loading && <LinearProgress />}
            <Table stickyHeader aria-label="sticky table">
              <TableHead>
                <TableRow>
                  {columns.map((column) => (
                    <TableCell
                      key={column.id}
                      align={column.align}
                      style={{ minWidth: column.minWidth }}
                    >
                      {column.label}
                    </TableCell>
                  ))}
                </TableRow>
              </TableHead>
              <TableBody>
                {results
                  .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  .map((row) => {
                    return (
                      <TableRow
                        hover
                        role="checkbox"
                        tabIndex={-1}
                        key={row.SubmissionFileName}
                      >
                        {columns.map((column) => {
                          const value = row[column.id];
                          return (
                            <TableCell key={column.id} align={column.align}>
                              {column.format && typeof value === "number"
                                ? column.format(value)
                                : value}
                            </TableCell>
                          );
                        })}
                      </TableRow>
                    );
                  })}
              </TableBody>
            </Table>
          </TableContainer>
          <TablePagination
            rowsPerPageOptions={[10, 25, 100]}
            component="div"
            count={results.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onChangePage={handleChangePage}
            onChangeRowsPerPage={handleChangeRowsPerPage}
          />
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
