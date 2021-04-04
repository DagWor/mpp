import React, { Component } from "react";

import SuperAdminService from "../../services/superadmin.service";
import {Card, CardContent, Container, Typography} from "@material-ui/core";
import Divider from "@material-ui/core/Divider";

export default class Managers extends Component {
    constructor(props) {
        super(props);
        this.state = {
            open : false,
            setOpen : false,
            content: [
                {
                    id: null,
                    username: "",
                    email: "",
                    branchName: "",
                    address: null,
                    password: "",
                    balance: 0.0,
                    roles: [
                        {
                            id: "",
                            name: ""
                        }
                    ]
                }
            ]
        };
    }


    componentDidMount() {
        SuperAdminService.getManagers().then(
            response => {
                this.setState({
                    content: response.data
                });
                console.log(response.data)
            },
            error => {
                this.setState({
                    content:
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString()
                });
            }

        );
    }

    handleClickOpen() {
        this.setState({
            open: true,
            setOpen: true
        })
    };

    handleClose = () => {
        console.log("there")
        this.setState({
            open: false,
            setOpen: false
        })
    };


    render() {
        if(this.state.content.length === 0){

            return (
                <Typography style={{fontSize: 20, alignSelf: "center"}} color="textPrimary">No Branch Managers Available yet</Typography>
            )

        } else {
            return  (

                <Container>
                    <Typography style={{fontSize: 20, alignSelf: "center"}} color="textPrimary">Branch Managers</Typography>
                    {this.state.content.map(trans => {
                        return (
                            <Card style={{minWidth: 275, maxWidth: 400, margin: 7, display: "inline-block"}} variant="outlined">
                                <CardContent>
                                    <Typography style={{fontSize: 14}} color="textSecondary" gutterBottom align={"center"}>
                                        <strong>Username : </strong> {trans.username}
                                    </Typography>
                                    <Typography style={{fontSize: 14}} color="textSecondary" gutterBottom align={"center"}>
                                        <strong>Branch : </strong> {trans.branchName}
                                    </Typography>
                                    <Typography style={{fontSize: 14}} color="textSecondary" gutterBottom align={"center"}>
                                        <strong>Email : </strong> {trans.email}
                                    </Typography>
                                    <Divider variant="middle" />
                                    {(trans.address != null) && (
                                        <div>
                                            <Typography variant="body2" component="p" align={"center"}>
                                                <strong>Address</strong>
                                            </Typography>
                                            <Divider variant="middle" />
                                            <Typography variant="body2" component="p" align={"center"}>
                                                {trans.address.city}, {trans.address.country}
                                            </Typography>
                                        </div>
                                    )}
                                </CardContent>
                                {/*<CardActions>*/}
                                {/*    <Button size="small">View Account Transactions</Button>*/}
                                {/*</CardActions>*/}
                            </Card>
                        )
                    })}
                </Container>
            );
        }
    }
}