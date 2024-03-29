import { AxiosResponse } from "axios";
import $api from "../http";
import { AuthResponse } from "../models/AuthReaponse";
import { IUser } from "../models/IUser";
import { DialogShortResponse } from "../models/DialogShortResponse";
import { DialogResponse } from "../models/DialogResponse";
import { MessageRequest } from "../models/MessageRequest";
import dayjs from "dayjs";

export default class MessageService {
    static sendMessage(dialogId : number, text : string): Promise<AxiosResponse<DialogResponse>> {
        const dateTime = dayjs().toISOString();
        return $api.post<DialogResponse>('/dialogs/messages', {dialogId, text, dateTime});
    }

    static sendFirstMessage(recipientId : number, text : string): Promise<AxiosResponse<DialogResponse>> {
        const dateTime = dayjs().toISOString();
        return $api.post<DialogResponse>('/dialogs/messages/newDialog', {recipientId, text, dateTime});
    }
}