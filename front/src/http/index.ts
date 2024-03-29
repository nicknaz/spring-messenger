import axios from "axios";
import { AuthResponse } from "../models/AuthReaponse";
import dayjs from "dayjs";

export const SERVER_URL = `https://petbrager.ru/inapi/`;

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
    handleDates(config  .data);
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

const isoDateFormat = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(?:\.\d*)?$/;

function isIsoDateString(value: any): boolean {
  return value && typeof value === "string" && isoDateFormat.test(value);
}

function handleDates(body: any) {
  if (body === null || body === undefined || typeof body !== "object")
    return body;

  for (const key of Object.keys(body)) {
    const value = body[key];
    if (isIsoDateString(value)) body[key] = dayjs(value).toDate();
    else if (typeof value === "object") handleDates(value);
  }
}

export default $api;