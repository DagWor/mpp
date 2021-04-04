import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/auth/";
const TELLER_API_URL = "http://localhost:8080/api/teller/";
const ADMIN_API_URL = "http://localhost:8080/api/admin/";

class AuthService {
    login(username, password) {
        return axios
            .post(API_URL + "login", {
                username,
                password
            })
            .then(response => {
                if (response.data.accessToken) {
                    localStorage.setItem("user", JSON.stringify(response.data));
                }

                return response.data;
            });
    }

    logout() {
        localStorage.removeItem("user");
    }

    register(username, email, password) {
        return axios.post(API_URL + "signup", {
            username,
            email,
            password
        });
    }
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
        },  {
            headers: authHeader()
        } );
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));;
    }
}

export default new AuthService();