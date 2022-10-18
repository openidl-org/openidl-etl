import React, { useState } from "react";
import { Auth } from "aws-amplify";
import { AppBar, Toolbar, IconButton, Typography } from "@material-ui/core";
import { makeStyles } from "@material-ui/core/styles";
import { Link as RouterLink } from "react-router-dom";
import Badge from "@material-ui/core/Badge";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import SearchIcon from "@material-ui/icons/Search";
import UploadIcon from "@material-ui/icons/Publish";
import MenuIcon from "@material-ui/icons/Menu";

export default function NavigationBar(props) {
  const classes = useStyles();
  const [anchorEl, setAnchorEl] = useState(null);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  async function handleSignOut() {
    await Auth.signOut();
  }

  return (
    <div className={classes.root}>
      <AppBar position="static" className={classes.appBar}>
        <Toolbar>
          <IconButton
            component={RouterLink}
            to="/"
            className={classes.menuButton}
            color="inherit"
            aria-label="Menu"
            data-test="AAISlogo"
          >
            <img src="/aais-logo-with-letters-32.png" alt="Home" />
          </IconButton>
          <div className={classes.grow} />
          <React.Fragment>
            <Badge
              color="secondary"
              variant="dot"
              overlap="circle"
              invisible={true}
            >
              <IconButton
                aria-label="account of current user"
                aria-controls="menu-appbar"
                aria-haspopup="true"
                onClick={handleClick}
                color="inherit"
              >
                <MenuIcon />
              </IconButton>
            </Badge>
            <Menu
              anchorEl={anchorEl}
              open={Boolean(anchorEl)}
              onClose={handleClose}
              color="primary"
              data-test="navBarMenu"
            >
              <MenuItem
                component={RouterLink}
                to="/search"
                onClick={handleClose}
              >
                <SearchIcon color="primary"></SearchIcon>
                <Typography color="primary" className={classes.menuTitle}>
                  Search
                </Typography>
              </MenuItem>
              <MenuItem
                component={RouterLink}
                to="/upload"
                onClick={handleClose}
              >
                <UploadIcon color="primary"></UploadIcon>
                <Typography color="primary" className={classes.menuTitle}>
                  Upload
                </Typography>
              </MenuItem>
            </Menu>
          </React.Fragment>
        </Toolbar>
      </AppBar>
    </div>
  );
}

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  grow: {
    flexGrow: 1,
  },
  search: {
    marginRight: theme.spacing(2),
  },
  menuTitle: {
    marginLeft: theme.spacing(1),
    // paddingRight: theme.spacing(1),
  },
  appBar: {
    backgroundColor: "white",
    color: theme.palette.primary.main,
  },
  menuButton: {
    marginLeft: -12,
    marginRight: 20,
  },
  activeButtonStyle: {
    borderBottom: "2px solid",
    borderBottomColor: theme.palette.primary.main,
    borderRadius: 0,
  },
  activeMeStyle: {
    border: "2px solid",
    borderColor: theme.palette.primary.main,
  },
}));
