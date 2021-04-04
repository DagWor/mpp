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


const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
});


export default class Transactions extends Component {
    constructor(props) {
        super(props);
        this.state = {
            open : false,
            setOpen : false,
            content: [{
                transactionDate: "",
                amount: 0,
                toAccount: 0,
                fromAccount: 0,
                type: "WITHDRAWL"
                }]
        };
    }


    componentDidMount() {
        UserService.getCustomerTransactions().then(
            response => {
                this.setState({
                    content: response.data,
                    open: true
                });
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
                console.log("that ")
            }
        );
    }

    render() {
        console.log("length is : ", this.state.content.length)
        if(this.state.content.length === 0){

            return (
                <Typography style={{fontSize: 20, alignSelf: "center"}} color="textPrimary">Transactions</Typography>
            )

        } else {
            return  (

            <Container>
                <Typography style={{fontSize: 20, alignSelf: "center"}} color="textPrimary">Transactions</Typography>
                {this.state.content.map(trans => {
                    return (
                        <Card style={{minWidth: 275, maxWidth: 400, margin: 7, display: "inline-block"}} variant="outlined">
                            <CardContent>
                                <Typography style={{fontSize: 14}} color="textSecondary" gutterBottom>
                                    {trans.type}
                                </Typography>
                                <Typography variant="h5" component="h2">
                                    $ {trans.amount}
                                </Typography>
                                {(trans.type === "TRANSFER") && (

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
                    )
                })}
            </Container>
        );
        }

    }
}