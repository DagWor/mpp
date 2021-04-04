import React, { Component } from "react";

import UserService from "../../services/user.service";
import {Button, Card, CardContent, Container, Typography} from "@material-ui/core";
import Dialog from "@material-ui/core/Dialog";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";
import Divider from "@material-ui/core/Divider";
import Slide from "@material-ui/core/Slide";
import {Message} from "@material-ui/icons";


export default class TellerAccounts extends Component {
    constructor(props) {
        super(props);
        this.state = {
            open : false,
            setOpen : false,
            content: [{
                accountNumber: 0,
                customerId: 0,
                type: 0,
                balance: 0,
                transactionList: [{
                    transactionId: "",
                    transactionDate: "",
                    amount: 10.0,
                    toAccount: 0,
                    fromAccount: 1006,
                    branchId: 123,
                    type: "WITHDRAWL"
                }]
            }
            ]
        };
    }


    componentDidMount() {
        UserService.getTellerAccounts().then(
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
        return (
            <Container>
                <Typography style={{fontSize: 20, alignSelf: "center"}} color="textPrimary">Accounts</Typography>
                {this.state.content.map(value => {
                    return (
                        <Card style={{minWidth: 250, maxWidth: 400, margin: 7, display: "inline-block"}} variant="outlined">
                            <CardContent>
                                <Typography style={{fontSize: 14}} color="textSecondary" gutterBottom align={"center"}>
                                    {value.accountNumber}
                                </Typography>
                                <Divider variant="middle" />
                                <Typography variant="h5" component="h2" align={"center"}>
                                    $ {value.balance}
                                </Typography>
                                <Typography variant="body2" component="p" align={"center"}>
                                    {value.type} Account
                                    <br />
                                </Typography>
                            </CardContent>
                        </Card>
                    )
                })}
            </Container>
        );
    }
}