import axios from "axios";
import authHeader from "./auth-header";

const TELLER_API_URL = "http://localhost:8080/api/teller/";

class TellerService {

    registerCustomer(username, email, password, firstName, lastName, ssn, street, city, postalCode, zipCode, country, intialAmount) {
        return axios.post(TELLER_API_URL + "create-customer", {
                username,
                email,
                password,
                firstName,
                lastName,
                ssn,
                street,
                city,
                postalCode,
                zipCode,
                country,
                intialAmount
            },
            {
                headers: authHeader()
            });
    }

    makeDeposit(amount, toAccount){
        return axios.post(TELLER_API_URL + "deposit", {
                amount,
                toAccount
        }, { headers : authHeader()})
    }

    makeWithdrawal(amount, fromAccount){
        return axios.post(TELLER_API_URL + "withdrawal", {
            amount,
            fromAccount
        }, { headers : authHeader()})
    }

    makeTransfer(amount, fromAccount, toAccount){
        return axios.post(TELLER_API_URL + "transfer", {
            amount,
            fromAccount,
            toAccount
        }, { headers : authHeader()})
    }

}


export default new TellerService();