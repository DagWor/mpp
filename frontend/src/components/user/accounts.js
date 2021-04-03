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


const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
});


export default class Accounts extends Component {
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
        UserService.getCustomerAccounts().then(
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

                        <Button variant="outlined" color="primary" onClick={this.handleClickOpen}>
                            View Transaction History
                        </Button>
                        <div>
                            {value.transactionList.map(trans => {
                            return (
                                <Dialog fullScreen open={this.state.open} onClose={this.handleClose} TransitionComponent={Transition}>
                                    <AppBar style={{position: 'relative'}}>
                                        <Toolbar>
                                            <IconButton edge="start" color="inherit" onClick={this.handleClose} aria-label="close">
                                                <CloseIcon />
                                            </IconButton>
                                            <Typography variant="h6" style={{marginLeft: 2, flex: 1}}>
                                                {value.accountNumber}
                                            </Typography>
                                            <Button autoFocus color="inherit" onClick={this.handleClose}>
                                                Close
                                            </Button>
                                        </Toolbar>
                                    </AppBar>
                                    <Card style={{minWidth: 275, maxWidth: 400, margin: 7, display: "inline-block"}} variant="outlined">
                                        <CardContent>
                                            <Typography style={{fontSize: 14}} color="textSecondary" gutterBottom>
                                                {trans.type}
                                            </Typography>
                                            <Typography variant="h5" component="h2">
                                                $ {trans.amount}
                                            </Typography>
                                            {(value.type === "TRANSFER") && (

                                                <Typography variant="body2" component="p">
                                                    From Account : {trans.fromAccount}
                                                    <br/>
                                                    to Account : {trans.toAccount}
                                                    <br />
                                                </Typography>
                                            )
                                            }
                                            <Typography variant="body2" component="p">
                                                {trans.transactionDate}
                                                <br />
                                            </Typography>
                                        </CardContent>
                                        {/*<CardActions>*/}
                                        {/*    <Button size="small">View Account Transactions</Button>*/}
                                        {/*</CardActions>*/}
                                    </Card>
                                </Dialog>

                            )
                        })}
                        </div>
                    </Card>
                )
            })}
            </Container>
        );
    }
}