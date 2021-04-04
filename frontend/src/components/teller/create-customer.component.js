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

const email = value => {
    if (!isEmail(value)) {
        return (
            <div className="alert alert-danger" role="alert">
                This is not a valid email.
            </div>
        );
    }
};

const vusername = value => {
    if (value.length < 3 || value.length > 20) {
        return (
            <div className="alert alert-danger" role="alert">
                The username must be between 3 and 20 characters.
            </div>
        );
    }
};

const vpassword = value => {
    if (value.length < 6 || value.length > 40) {
        return (
            <div className="alert alert-danger" role="alert">
                The password must be between 6 and 40 characters.
            </div>
        );
    }
};

export default class RegisterCustomer extends Component {
    constructor(props) {
        super(props);
        this.handleRegisterCustomer = this.handleRegisterCustomer.bind(this);
        this.onChangeUsername = this.onChangeUsername.bind(this);
        this.onChangeEmail = this.onChangeEmail.bind(this);
        this.onChangePassword = this.onChangePassword.bind(this);
        this.onChangeFirstName = this.onChangeFirstName.bind(this);
        this.onChangeLastName = this.onChangeLastName.bind(this);
        this.onChangeSsn = this.onChangeSsn.bind(this);
        this.onChangeStreet = this.onChangeStreet.bind(this);
        this.onChangeCity = this.onChangeCity.bind(this);
        this.onChangePostalCode = this.onChangePostalCode.bind(this);
        this.onChangeZipCode = this.onChangeZipCode.bind(this);
        this.onChangeCountry = this.onChangeCountry.bind(this);
        this.onChangeInitialAmount = this.onChangeInitialAmount.bind(this);


        this.state = {
            accountTYpe:"",
            username:"",
            email:"",
            password:"",
            firstName:"",
            lastName:"",
            ssn:0,
            street:"",
            city:"",
            postalCode:"",
            zipCode:0,
            country:"",
            intialAmount:0,
            successful: false,
            message: ""
        };
    }

    onChangeUsername(e) {
        this.setState({
            username: e.target.value
        });
    }

    onChangeEmail(e) {
        this.setState({
            email: e.target.value
        });
    }

    onChangePassword(e) {
        this.setState({
            password: e.target.value
        });
    }

    onChangeFirstName(e) {
        this.setState({
            firstName: e.target.value
        });
    }

    onChangeLastName(e) {
        this.setState({
            lastName: e.target.value
        });
    }

    onChangeSsn(e) {
        this.setState({
            ssn: e.target.value
        });
    }


    onChangeStreet(e) {
        this.setState({
            street: e.target.value
        });
    }

    onChangeCity(e) {
        this.setState({
            city: e.target.value
        });
    }

    onChangePostalCode(e) {
        this.setState({
            postalCode: e.target.value
        });
    }

    onChangeZipCode(e) {
        this.setState({
            zipCode: e.target.value
        });
    }

    onChangeCountry(e) {
        this.setState({
            country: e.target.value
        });
    }

    onChangeInitialAmount(e) {
        this.setState({
            intialAmount: e.target.value
        });
    }

    handleRegisterCustomer(e) {
        e.preventDefault();

        this.setState({
            message: "",
            successful: false
        });

        this.form.validateAll();

        if (this.checkBtn.context._errors.length === 0) {
            TellerService.registerCustomer(
                this.state.username,
                this.state.email,
                this.state.password,
                this.state.firstName,
                this.state.lastName,
                this.state.ssn,
                this.state.street,
                this.state.city,
                this.state.postalCode,
                this.state.zipCode,
                this.state.country,
                this.state.intialAmount
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
                    <strong>Create Customer Account</strong>
                    <br />
                </Typography>
                <div className="card card-container">
                    <img
                        src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                        alt="profile-img"
                        className="profile-img-card"
                    />

                    <Form
                        onSubmit={this.handleRegisterCustomer}
                        ref={c => {
                            this.form = c;
                        }}
                    >
                        {!this.state.successful && (
                            <div>
                                <div className="form-group">
                                    <label htmlFor="username">Username</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="username"
                                        value={this.state.username}
                                        onChange={this.onChangeUsername}
                                        validations={[required, vusername]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="email">Email</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="email"
                                        value={this.state.email}
                                        onChange={this.onChangeEmail}
                                        validations={[required, email]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="password">Password</label>
                                    <Input
                                        type="password"
                                        className="form-control"
                                        name="password"
                                        value={this.state.password}
                                        onChange={this.onChangePassword}
                                        validations={[required, vpassword]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="firstname">First Name</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="firstname"
                                        value={this.state.firstName}
                                        onChange={this.onChangeFirstName}
                                        validations={[required]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="lastname">Last Name</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="lastname"
                                        value={this.state.lastName}
                                        onChange={this.onChangeLastName}
                                        validations={[required]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="ssn">Social Security Number</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="ssn"
                                        value={this.state.ssn}
                                        onChange={this.onChangeSsn}
                                        validations={[required]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="street">Street</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="street"
                                        value={this.state.street}
                                        onChange={this.onChangeStreet}
                                        validations={[required]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="city">City</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="city"
                                        value={this.state.city}
                                        onChange={this.onChangeCity}
                                        validations={[required]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="postalCode">Postal Code</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="postalCode"
                                        value={this.state.postalCode}
                                        onChange={this.onChangePostalCode}
                                        validations={[required]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="zipCode">ZIP Code</label>
                                    <Input
                                        type="number"
                                        className="form-control"
                                        name="zipCode"
                                        value={this.state.zipCode}
                                        onChange={this.onChangeZipCode}
                                        validations={[required]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="country">Country</label>
                                    <Input
                                        type="country"
                                        className="form-control"
                                        name="country"
                                        value={this.state.country}
                                        onChange={this.onChangeCountry}
                                        validations={[required]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="intialAmount">Initial Deposit</label>
                                    <Input
                                        type="number"
                                        className="form-control"
                                        name="intialAmount"
                                        value={this.state.intialAmount}
                                        onChange={this.onChangeInitialAmount}
                                        validations={[required]}
                                    />
                                </div>

                                <div className="form-group">
                                    <button className="btn btn-primary btn-block">Create Customer</button>
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