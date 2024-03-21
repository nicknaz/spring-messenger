import axios from "axios";
import { AuthResponse } from "../models/AuthReaponse";

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

$api.interceptors.response.use((config) => {
    return config;
},async (error) => {
    const originalRequest = error.config;
    if (error.response.status == 401 && error.config && !error.config._isRetry) {
        originalRequest._isRetry = true;
        try {
            const response = await axios.get<AuthResponse>(`${SERVER_URL}/auth/refresh`, {withCredentials: true})
            localStorage.setItem('token', response.data.token);
            return $api.request(originalRequest);
        } catch (e) {
            console.log('НЕ АВТОРИЗОВАН')
        }
    }
    throw error;
})

export default $api;