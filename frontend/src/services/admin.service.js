import axios from "axios";
import authHeader from "./auth-header";

const ADMIN_API_URL = "http://localhost:8080/api/admin/";

class AdminService {
    registerTeller(username, email, password, firstName, lastName, ssn, street, city, postalCode, zipCode, country) {
        return axios.post(ADMIN_API_URL + "create-teller", {
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
            country
        },
            {
                headers: authHeader()
            });
    }


    totalTellers = () => {
        return axios.get(ADMIN_API_URL + 'tellers', { headers: authHeader() });
    }

    branchDetails = () => {
        return axios.get(ADMIN_API_URL + 'branch_details', { headers: authHeader() });
    }
}


export default new AdminService();