import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/test/';
const CUSTOMER_API_URL = 'http://localhost:8080/api/customer/';
const ADMIN_API_URL = 'http://localhost:8080/api/admin/';
const TELLER_API_URL = 'http://localhost:8080/api/teller/';

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
        return axios.get(ADMIN_API_URL, { headers: authHeader() });
    }

    getCustomerAccounts() {
        return axios.get(CUSTOMER_API_URL + 'accounts', { headers: authHeader() });
    }

    getCustomerTransactions = () => {
        return axios.get(CUSTOMER_API_URL + 'transactions', { headers: authHeader() });
    }

    getTellerTransactions = () => {
        return axios.get(TELLER_API_URL + 'listoftransaction', { headers: authHeader() });
    }

    getTellerAccounts = () => {
        return axios.get(TELLER_API_URL + 'listofaccount', { headers: authHeader() });
    }

    createTeller = (something) => {
        return axios.post(TELLER_API_URL + 'create-teller', {something}, { headers: authHeader() });
    }


}

export default new UserService();