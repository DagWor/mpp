import React, { Component } from "react";

import SuperAdminService from "../../services/superadmin.service";
import {Card, CardContent, Container, Typography} from "@material-ui/core";
import Divider from "@material-ui/core/Divider";

export default class Branches extends Component {
    constructor(props) {
        super(props);
        this.state = {
            open : false,
            setOpen : false,
            content: [{
                id: "",
                branchName: "",
                address: {
                    street: "",
                    city: "",
                    postalCode: "",
                    zipCode: 0,
                    country: ""
                }
            }]
        };
    }


    componentDidMount() {
        SuperAdminService.getBranches().then(
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
                <Typography style={{fontSize: 20, alignSelf: "center"}} color="textPrimary">No Transactions Available yet</Typography>
            )

        } else {
            return  (

                <Container>
                    <Typography style={{fontSize: 20, alignSelf: "center"}} color="textPrimary">Branches</Typography>
                    {this.state.content.map(trans => {
                        return (
                            <Card style={{minWidth: 275, maxWidth: 400, margin: 7, display: "inline-block"}} variant="outlined">
                                <CardContent>
                                    <Typography style={{fontSize: 14}} color="textSecondary" gutterBottom align={"center"}>
                                        <strong>Name</strong> {trans.branchName}
                                    </Typography>
                                    <Divider variant="middle" />
                                    <Typography variant="h5" component="h2" align={"center"}>
                                        Address
                                    </Typography>
                                    <Typography variant="body2" component="p" align={"center"}>
                                        {trans.address.street}
                                        <br />
                                    </Typography>
                                    <Typography variant="body2" component="p" align={"center"}>
                                        {trans.address.city}
                                        <br />
                                    </Typography>
                                    <Typography variant="body2" component="p" align={"center"}>
                                        {trans.address.zipCode}
                                        <br />
                                    </Typography>
                                    <Typography variant="body2" component="p" align={"center"}>
                                        {trans.address.country}
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