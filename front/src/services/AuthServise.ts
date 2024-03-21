import { AxiosResponse } from "axios";
import $api from "../http";
import { AuthResponse } from "../models/AuthReaponse";
import RegistrationResponse from "../models/RegistrationResponse";

export default class AuthService {
    static async login(username: string, password: string) : Promise<AxiosResponse<AuthResponse>> {
        return $api.post<AuthResponse>('/auth/signin', {username, password});
    }

    static async registration(username: string, password: string, email: string) : Promise<AxiosResponse<RegistrationResponse>> {
        return $api.post<RegistrationResponse>('/auth/signup', {username, password, email});
    }

    static async logout() : Promise<void> {
        return $api.post('/auth/logout');
    }
}