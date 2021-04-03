import axios from 'axios';
import authHeader from './auth-header';
import paramController from "./paramController";

const API_URL = 'http://localhost:8080/api/test/';
const CUSTOMER_API_URL = 'http://localhost:8080/api/customer/';

class UserService {

    getPublicContent() {
        return axios.get(API_URL + 'all');
    }

    getUserBoard() {
        return axios.get(API_URL + 'user', { headers: authHeader() });
    }

    getModeratorBoard() {
        return axios.get(API_URL + 'mod', { headers: authHeader() });
    }

    getAdminBoard() {
        return axios.get(API_URL + 'admin', { headers: authHeader() });
    }

    getCustomerAccounts() {
        return axios.get(CUSTOMER_API_URL + 'accounts', { headers: authHeader() });
    }

    getCustomerTransactions = () => {
        return axios.get(CUSTOMER_API_URL + `transactions`, { headers: authHeader() });
    }
}

export default new UserService();