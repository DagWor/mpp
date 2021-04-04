import React, { Component } from "react";
import AuthService from "../services/auth.service";
import {Card, CardContent, Typography} from "@material-ui/core";
import Divider from "@material-ui/core/Divider";

export default class Profile extends Component {
    constructor(props) {
        super(props);

        this.state = {
            currentUser: AuthService.getCurrentUser()
        };
    }

    render() {
        const { currentUser } = this.state;

        return (
            <div className="container">

                <header className="jumbotron">
                    <h3>
                        <strong>{currentUser.username}'s </strong> Profile
                    </h3>
                    <Typography style={{fontSize: 14}} color="textSecondary" gutterBottom align={"center"}>
                        <strong>Username : </strong> {currentUser.username}
                    </Typography>
                    <Divider variant="middle" />
                    <Typography variant="body2" component="p" align={"center"}>
                        <strong>Email Address : </strong> {currentUser.email}
                        <br />
                    </Typography>
                </header>
            </div>
        );
    }
}