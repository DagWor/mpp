import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import { isEmail } from "validator";

import TellerService from "../../services/teller.service";
import {Typography} from "@material-ui/core";

const required = value => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};

export default class TellerTransfer extends Component {
    constructor(props) {
        super(props);
        this.handleMakeTransfer = this.handleMakeTransfer.bind(this);
        this.onChangeAmount = this.onChangeAmount.bind(this);
        this.onChangeFromAccount = this.onChangeFromAccount().bind(this);
        this.onChangeToAccount = this.onChangeToAccount.bind(this);


        this.state = {
            amount:0,
            toAccount:0,
            fromAccount:0
        };
    }

    onChangeAmount(e) {
        this.setState({
            amount: e.target.value
        });
    }

    onChangeToAccount(e) {
        this.setState({
            toAccount: e.target.value
        });
    }

    onChangeFromAccount(e) {
        this.setState({
            fromAccount: e.target.value
        });
    }

    handleMakeTransfer(e) {
        e.preventDefault();

        this.setState({
            message: "",
            successful: false
        });

        this.form.validateAll();

        if (this.checkBtn.context._errors.length === 0) {
            TellerService.makeTransfer(
                this.state.amount,
                this.state.toAccount,
                this.state.fromAccount
            ).then(
                response => {
                    this.setState({
                        message: response.data.message,
                        successful: true
                    });
                },
                error => {
                    const resMessage =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();

                    this.setState({
                        successful: false,
                        message: resMessage
                    });
                }
            );
        }
    }

    render() {
        return (
            <div className="col-md-12">

                <Typography style={{fontSize: 24}} color="textSecondary" gutterBottom align={"center"}>
                    <strong>Make A Transfer</strong>
                    <br />
                </Typography>
                <div className="card card-container">
                    <img
                        src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                        alt="profile-img"
                        className="profile-img-card"
                    />

                    <Form
                        onSubmit={this.handleMakeTransfer}
                        ref={c => {
                            this.form = c;
                        }}
                    >
                        {!this.state.successful && (
                            <div>
                                <div className="form-group">
                                    <label htmlFor="amount">Amount</label>
                                    <Input
                                        type="number"
                                        className="form-control"
                                        name="amount"
                                        value={this.state.amount}
                                        onChange={this.onChangeAmount}
                                        validations={[required]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="toAccount">To Account Number</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="toAccount"
                                        value={this.state.toAccount}
                                        onChange={this.onChangeToAccount}
                                        validations={[required]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="fromAccount">From Account Number</label>
                                    <Input
                                        type="number"
                                        className="form-control"
                                        name="fromAccount"
                                        value={this.state.password}
                                        onChange={this.onChangePassword}
                                        validations={[required]}
                                    />
                                </div>

                                <div className="form-group">
                                    <button className="btn btn-primary btn-block">Perform Transfer</button>
                                </div>
                            </div>
                        )}

                        {this.state.message && (
                            <div className="form-group">
                                <div
                                    className={
                                        this.state.successful
                                            ? "alert alert-success"
                                            : "alert alert-danger"
                                    }
                                    role="alert"
                                >
                                    {this.state.message}
                                </div>
                            </div>
                        )}
                        <CheckButton
                            style={{ display: "none" }}
                            ref={c => {
                                this.checkBtn = c;
                            }}
                        />
                    </Form>
                </div>
            </div>
        );
    }
}