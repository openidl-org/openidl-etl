/**
 * @description This is the search screen
 * @author Findlay Clarke <findlayc@aaisonline.com>
 * @since 1.0.0
 * @module screens/SearchScreen
 */
import React, { useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import LinearProgress from "@material-ui/core/LinearProgress";
import Container from "@material-ui/core/Container";
import TextField from "@material-ui/core/TextField";
import { Button, Paper, Typography } from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TablePagination from "@material-ui/core/TablePagination";
import TableRow from "@material-ui/core/TableRow";
import * as IDLApi from "../apis/MdsApi";
import LoginDialog from "../components/LoginDialog";

const columns = [
  { id: "organizationID", label: "Org ID", minWidth: 100 },
  {
    id: "VINHash",
    label: "VINHash",
    minWidth: 170,
  },
  {
    id: "state",
    label: "State",
  },
  {
    id: "transactionDate",
    label: "Transaction Date",
  },
];

export default function SearchScreen(props) {
  const classes = useStyles();
  const [loading, setLoading] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [orgId, setOrgId] = useState("");
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [results, setResults] = useState([]);
  const [showModal, setShowModal] = useState(false);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  async function handleSearch() {
    if (orgId === "" || searchTerm === "") {
      alert("Please enter both Org ID and Month");
      return;
    }
    setLoading(true);
    try {
      const data = await IDLApi.getDataByCriteria(orgId, searchTerm);
      setResults(data?.result);
    } catch (error) {
      console.log(error);
      setShowModal(true);
    } finally {
      setLoading(false);
    }
  }

  return (
    <React.Fragment>
      {showModal && (
        <LoginDialog handleClose={() => setShowModal(false)}></LoginDialog>
      )}
      <Container className={classes.container} maxWidth={false}>
        {loading && <LinearProgress />}
      </Container>
      <Container className={classes.container} maxWidth={false}>
        <Paper className={classes.paper} elevation={3}>
          <Grid container justify="space-between" alignItems="center">
            <Grid
              item
              container
              justify="flex-start"
              alignItems="center"
              spacing={2}
              xs={6}
            >
              <Grid item>
                <TextField
                  id="date"
                  label="Date"
                  type="month"
                  variant="outlined"
                  required
                  className={classes.textField}
                  InputLabelProps={{
                    shrink: true,
                  }}
                  onChange={(event) => setSearchTerm(event.target.value)}
                />
              </Grid>
              <Grid item>
                <TextField
                  id="orgId"
                  label="Org ID"
                  type="text"
                  variant="outlined"
                  required
                  className={classes.textField}
                  InputLabelProps={{
                    shrink: true,
                  }}
                  onChange={(event) => setOrgId(event.target.value)}
                />
              </Grid>
            </Grid>
            <Grid item>
              <Button
                variant="contained"
                color="primary"
                onClick={handleSearch}
              >
                Search
              </Button>
            </Grid>
          </Grid>
        </Paper>
        <Paper className={classes.root}>
          <TableContainer className={classes.container}>
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
                        key={row._id}
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
    </React.Fragment>
  );
}

const useStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
  },
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
