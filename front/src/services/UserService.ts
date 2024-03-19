import { AxiosResponse } from "axios";
import $api from "../http";
import { AuthResponse } from "../models/AuthReaponse";
import { IUser } from "../models/IUser";

export default class UserService {
    static fetchUsers(): Promise<AxiosResponse<IUser[]>> {
        return $api.get<IUser[]>('/user');
    }

    static getProfile(): Promise<AxiosResponse<IUser>> {
        return $api.get<IUser>('/user/profile');
    }
}