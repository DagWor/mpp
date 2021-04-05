import React, { Component } from "react";

import AdminService from "../../services/admin.service";
import {Card, CardContent, Container, Typography} from "@material-ui/core";
import Divider from "@material-ui/core/Divider";


export default class BranchDetails extends Component {
    constructor(props) {
        super(props);
        this.state = {
            open : false,
            setOpen : false,
            content: {
                tellers: 0,
                deposit: 0,
                customers: 0,
                withdrawals: 0
            }
        };
    }


    componentDidMount() {
        AdminService.branchDetails().then(
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
                <Typography style={{fontSize: 20, alignSelf: "center"}} color="textPrimary">Branch Details</Typography>
                <Divider variant="middle" />
                        <Card style={{minWidth: 400, maxWidth: 400, margin: 7, display: "inline-block", width: 1000}} variant="outlined">
                            <CardContent>
                                <Typography style={{fontSize: 14}} color="textSecondary" gutterBottom align={"center"}>
                                    <strong>Username: </strong>{this.state.content.username}
                                </Typography>
                                <Divider variant="middle" />
                                <Typography style={{fontSize: 14}} color="textSecondary" gutterBottom align={"center"}>
                                    <strong>Number of Branch Tellers : </strong> {this.state.content.tellers}
                                </Typography>
                                <Typography style={{fontSize: 14}} color="textSecondary" gutterBottom align={"center"}>
                                    <strong>Number of Branch Customers : </strong> {this.state.content.customers}
                                    <br />
                                </Typography>
                                <br />
                                <Typography style={{fontSize: 14}} color="textSecondary" gutterBottom align={"center"}>
                                    <strong>Total Branch Deposits : </strong> {this.state.content.deposit}
                                    <br />
                                </Typography>

                                <Typography style={{fontSize: 14}} color="textSecondary" gutterBottom align={"center"}>
                                    <strong>Total Branch Withdrawals : </strong> {this.state.content.withdrawals}
                                    <br />
                                </Typography>
                            </CardContent>
                        </Card>
            </Container>
        );
    }
}