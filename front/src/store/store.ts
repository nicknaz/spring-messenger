import { makeAutoObservable } from "mobx";
import { IUser } from "../models/IUser";
import $api, { SERVER_URL } from "../http";
import axios from "axios";
import AuthService from "../services/AuthServise";
import { AuthResponse } from "../models/AuthReaponse";
import UserService from "../services/UserService";

export default class Store {
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
    }

    setLoading(bool: boolean) {
        this.isLoading = bool;
    }

    async login(username: string, password: string) {
        try {
            const response = await AuthService.login(username, password);
            localStorage.setItem('token', response.data.token);
            this.setAuth(true);
            this.setUser(response.data.user);
        } catch (e: any) {
            console.log(e.response?.data?.message);
        }
    }

    async registration(username: string, password: string) {
        try {
            const response = await AuthService.registration(username, password);
            //localStorage.setItem('token', response.data.token);
            //this.setAuth(true);
            //this.setUser(response.data.user);
        } catch (e: any) {
            console.log(e.response?.data?.message);
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

    async getProfile() {
        try {
            const response = await UserService.getProfile();
            this.setUser(response.data);
        } catch (e: any) {
            console.log(e.response?.data?.message);
        }
    }
}