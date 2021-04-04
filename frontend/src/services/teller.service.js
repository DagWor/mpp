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

}


export default new TellerService();