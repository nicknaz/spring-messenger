import { makeAutoObservable } from "mobx";
import { IUser } from "../models/IUser";
import $api, { SERVER_URL } from "../http";
import axios, { AxiosResponse } from "axios";
import AuthService from "../services/AuthServise";
import { AuthResponse } from "../models/AuthReaponse";
import RegistrationResponse from "../models/RegistrationResponse";
import UserService from "../services/UserService";
import DialogsStore from "./DialogsStore";

export default class UserStore {
    dialogsStore : any;
    user = {} as IUser;
    isAuth = false;
    isLoading = false;

    constructor() {
        makeAutoObservable(this);
    }

    setAuth(bool: boolean) {
        this.isAuth = bool;
    }

    setUser(user: IUser) {
        this.user = user;
        this.dialogsStore = new DialogsStore();
    }

    setLoading(bool: boolean) {
        this.isLoading = bool;
    }

    async login(username: string, password: string) : Promise<Number> {
        try {
            const response = await AuthService.login(username, password);
            localStorage.setItem('token', response.data.token);
            this.setAuth(true);
            this.setUser(response.data.user);
            return response.status;
        } catch (e: any) {
            console.log(e.response?.data?.message);
            return e.response.status;
        }
    }

    async registration(username: string, password: string, email: string) : Promise<RegistrationResponse> {
        try {
            const response = await AuthService.registration(username, password, email);
            response.data.status = response.status;
            return response.data;
        } catch (e: any) {
            e.response.data.status = e.response.status;
            return e.response.data;
        }
    }

    async logout() {
        try {
            localStorage.removeItem('token');
            this.setAuth(false);
            this.setUser({} as IUser);
        } catch (e: any) {
            console.log(e.response?.data?.message);
        }
    }

    async checkAuth() {
        this.setLoading(true);
        try {
            const response = await axios.get<AuthResponse>(`${SERVER_URL}/auth/refresh`, {withCredentials: true});
            localStorage.setItem('token', response.data.token);
            this.setAuth(true);
            this.setUser(response.data.user);
        } catch (e: any) {
            console.log(e.response?.data?.message);
        } finally {
            this.setLoading(false);
        }
    }

    
}