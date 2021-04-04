import React, { Component } from "react";

import AdminService from "../../services/admin.service";
import {Button, Card, CardContent, Container, Typography} from "@material-ui/core";
import Dialog from "@material-ui/core/Dialog";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";
import Divider from "@material-ui/core/Divider";
import Slide from "@material-ui/core/Slide";
import {Message} from "@material-ui/icons";


export default class AdminTellers extends Component {
    constructor(props) {
        super(props);
        this.state = {
            open : false,
            setOpen : false,
            content: [{
                id: "",
                username: "",
                email: "",
                branchName: "",
                address: {
                    street: "",
                    city: "",
                    postalCode: "",
                    zipCode: 0,
                    country: ""
                },
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
        AdminService.totalTellers().then(
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
                console.log("error")
            }

        );
    }

    render() {
        console.log(this.state.content)
        return (
            <Container>
                <Typography style={{fontSize: 20, alignSelf: "center"}} color="textPrimary">Branch Tellers</Typography>
                {this.state.content.map(value => {
                    return (
                        <Card style={{minWidth: 400, maxWidth: 400, margin: 7, display: "inline-block"}} variant="outlined">
                            <CardContent>
                                <Typography style={{fontSize: 14}} color="textSecondary" gutterBottom align={"center"}>
                                    <strong>Username: </strong>{value.username}
                                </Typography>
                                <Divider variant="middle" />
                                <Typography variant="h5" component="h2" align={"center"}>
                                    {value.email}
                                </Typography>
                                <Typography variant="body2" component="p" align={"center"}>
                                    Branch : {value.branchName}
                                    <br />
                                </Typography>
                                <br />
                                {(value.address != null) && (
                                    <div>
                                        <Typography variant="body2" component="p" align={"center"}>
                                            <strong>Address</strong>
                                        </Typography>
                                        <Divider variant="middle" />
                                        <Typography variant="body2" component="p" align={"center"}>
                                            {value.address.street}, {value.address.city}, {value.address.country}
                                        </Typography>
                                    </div>
                                )}
                            </CardContent>
                        </Card>
                    )
                })}
            </Container>
        );
    }
}