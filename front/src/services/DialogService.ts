import { AxiosResponse } from "axios";
import $api from "../http";
import { AuthResponse } from "../models/AuthReaponse";
import { IUser } from "../models/IUser";
import { DialogShortResponse } from "../models/DialogShortResponse";
import { DialogResponse } from "../models/DialogResponse";

export default class DialogService {
    static getAllDialogsByUserId(): Promise<AxiosResponse<DialogShortResponse[]>> {
        return $api.get<DialogShortResponse[]>('/dialogs');
    }

    static getDialogById(id : number): Promise<AxiosResponse<DialogResponse>> {
        return $api.get<DialogResponse>('/dialogs/' + id);
    }
}