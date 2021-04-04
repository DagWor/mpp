import axios from "axios";
import authHeader from "./auth-header";

const SUPER_ADMIN_API_URL = "http://localhost:8080/api/superadmin/";

class SuperAdminService {

    getBranches = () => {
        return axios.get(SUPER_ADMIN_API_URL + 'branches', { headers: authHeader() });
    }

    getManagers = () => {
        return axios.get(SUPER_ADMIN_API_URL + 'branch-managers', { headers: authHeader() });
    }
}


export default new SuperAdminService();