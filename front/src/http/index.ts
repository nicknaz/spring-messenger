import axios from "axios";

export const SERVER_URL = `http://localhost:5600/messenger`;

const $api = axios.create({
    withCredentials: true,
    baseURL: SERVER_URL
});

$api.interceptors.request.use(
    (config) => {
        config.headers.Authorization = `Bearer ${localStorage.getItem('token')}`;
        
        return config;
    }
)

export default $api;