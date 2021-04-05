import React, { Component } from "react";
import { Switch, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/auth.service";

import Login from "./components/login.component";
import Register from "./components/register.component";
import Home from "./components/home.component";
import Profile from "./components/profile.component";
import BoardUser from "./components/user/board-user.component";
import Accounts from "./components/user/accounts";
import Transactions from "./components/user/transaction.component";
import TellerTransactions from "./components/teller/teller-transactions.component";
import RegisterTeller from "./components/admin/create-teller.component";
import TellerAccounts from "./components/teller/teller-accounts.component";
import AdminTellers from "./components/admin/tellers.component";
import BranchDetails from "./components/admin/branch-report.component";
import RegisterCustomer from "./components/teller/create-customer.component";
import TellerTransfer from "./components/teller/make-transfer.component";
import TellerDeposit from "./components/teller/make-deposit.component";
import TellerWithdrawal from "./components/teller/make-withdrawal.component";
import Branches from "./components/headoffice/branches.component";
import Managers from "./components/headoffice/admins.component";

class App extends Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {
      showTellerBoard: false,
      showAdminBoard: false,
      currentUser: undefined,
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();

    if (user) {
      let roles = user.roles;
      this.setState({
        currentUser: user,
        showCustomerBoard: roles.includes('ROLE_USER'),
        showTellerBoard: roles.includes('ROLE_TELLER'),
        showAdminBoard: roles.includes('ROLE_ADMIN'),
        showSuperAdminBoard: roles.includes('ROLE_HEAD_OFFICE')
      });
    }
  }

  logOut() {
    AuthService.logout();
  }

  render() {
    const { currentUser, showTellerBoard, showAdminBoard, showSuperAdminBoard, showCustomerBoard } = this.state;

    console.log(currentUser, showTellerBoard, showAdminBoard, showSuperAdminBoard, showCustomerBoard);
    return (
        <div>
          <nav className="navbar navbar-expand navbar-dark bg-dark"  style={{backgroundColor: "blue"}}>
            <Link to={"/"} className="navbar-brand">
              Trust Bank Inc.
            </Link>
            <div className="navbar-nav mr-auto">
              <li className="nav-item">
                <Link to={"/home"} className="nav-link">
                  Home
                </Link>
              </li>

              {showTellerBoard && !showSuperAdminBoard && (
                  <li className="nav-item">
                    <Link to={"/teller"} className="nav-link">
                      Teller Board
                    </Link>
                  </li>
              )}

              {showAdminBoard &&  !showSuperAdminBoard && (
                  <li className="nav-item">
                    <Link to={"/admin"} className="nav-link">
                      Admin Board
                    </Link>
                  </li>
              )}

              {currentUser &&  !showSuperAdminBoard && (
                  <li className="nav-item">
                    <Link to={"/user"} className="nav-link">
                      User
                    </Link>
                  </li>
              )}

              {showTellerBoard &&  !showSuperAdminBoard && (
                  <li className="nav-item">
                    <Link to={"/teller/accounts"} className="nav-link">
                      Accounts
                    </Link>
                  </li>
              )}

              {showTellerBoard &&  !showSuperAdminBoard && (
                  <li className="nav-item">
                    <Link to={"/teller/create-customer"} className="nav-link">
                      Create Account
                    </Link>
                  </li>
              )}

              {currentUser && !showTellerBoard && !showAdminBoard && !showSuperAdminBoard && (
                  <li className="nav-item">
                    <Link to={"/customer/accounts"} className="nav-link">
                      Accounts
                    </Link>
                  </li>
              )}

              {currentUser && !showTellerBoard && !showAdminBoard && !showSuperAdminBoard && (
                  <li className="nav-item">
                    <Link to={"/customer/transactions"} className="nav-link">
                      Transactions
                    </Link>
                  </li>
              )}

              {showSuperAdminBoard &&(
                  <li className="nav-item">
                    <Link to={"/superadmin/branches"} className="nav-link">
                      Branches
                    </Link>
                  </li>
              )}

              {showSuperAdminBoard &&(
                  <li className="nav-item">
                    <Link to={"/superadmin/admins"} className="nav-link">
                      Branch Managers
                    </Link>
                  </li>
              )}

              {!showSuperAdminBoard && showAdminBoard && (
                  <li className="nav-item">
                    <Link to={"/create-teller"} className="nav-link">
                      Create Teller
                    </Link>
                  </li>
              )}

              {!showSuperAdminBoard && showAdminBoard && (
                  <li className="nav-item">
                    <Link to={"/admin/tellers"} className="nav-link">
                      Tellers
                    </Link>
                  </li>
              )}

              {!showSuperAdminBoard && showAdminBoard && (
                  <li className="nav-item">
                    <Link to={"/admin/branch-report"} className="nav-link">
                      Branch Details
                    </Link>
                  </li>
              )}

              {!showSuperAdminBoard && !showCustomerBoard && showTellerBoard &&(
                  <li className="nav-item">
                    <Link to={"/teller/transactions"} className="nav-link">
                      Transactions
                    </Link>
                  </li>
              )}

              {!showSuperAdminBoard && showTellerBoard &&(
                  <li className="nav-item">
                    <Link to={"/teller/make-deposit"} className="nav-link">
                      Make Deposit
                    </Link>
                  </li>
              )}

              {!showSuperAdminBoard && showTellerBoard &&(
                  <li className="nav-item">
                    <Link to={"/teller/make-withdrawal"} className="nav-link">
                      Make Withdrawal
                    </Link>
                  </li>
              )}

              {!showSuperAdminBoard && showTellerBoard &&(
                  <li className="nav-item">
                    <Link to={"/teller/transfer"} className="nav-link">
                      Perform Transform
                    </Link>
                  </li>
              )}
            </div>

            {currentUser ? (
                <div className="navbar-nav ml-auto">
                  <li className="nav-item">
                    <Link to={"/profile"} className="nav-link">
                      {currentUser.username}
                    </Link>
                  </li>
                  <li className="nav-item">
                    <a href="/login" className="nav-link" onClick={this.logOut}>
                      LogOut
                    </a>
                  </li>
                </div>
            ) : (
                <div className="navbar-nav ml-auto">
                  <li className="nav-item">
                    <Link to={"/login"} className="nav-link">
                      Login
                    </Link>
                  </li>

                  <li className="nav-item">
                    <Link to={"/register"} className="nav-link">
                      Sign Up
                    </Link>
                  </li>
                </div>
            )}
          </nav>

          <div className="container mt-3">
            <Switch>
              <Route exact path={["/", "/home"]} component={Home} />
              <Route exact path="/login" component={Login} />
              <Route exact path="/register" component={Register} />
              <Route exact path="/profile" component={Profile} />
              <Route path="/user" component={BoardUser} />
              {/*<Route path="/mod" component={BoardTeller} />*/}
              {/*<Route path="/admin" component={BoardAdmin} />*/}
              <Route path="/customer/accounts" component={Accounts} />
              <Route path="/customer/transactions" component={Transactions} />
              <Route path="/teller/transactions" component={TellerTransactions} />
              <Route path="/teller/accounts" component={TellerAccounts} />
              <Route path="/teller/create-customer" component={RegisterCustomer} />
              <Route path="/create-teller" component={RegisterTeller} />
              <Route path="/admin/tellers" component={AdminTellers} />
              <Route path="/admin/branch-report" component={BranchDetails} />
              <Route path="/teller/make-deposit" component={TellerDeposit} />
              <Route path="/teller/make-withdrawal" component={TellerWithdrawal} />
              <Route path="/teller/transfer" component={TellerTransfer} />
              <Route path="/superadmin/branches" component={Branches} />
              <Route path="/superadmin/admins" component={Managers} />
            </Switch>
          </div>
        </div>
    );
  }
}

export default App;