import { IUser } from "./IUser";
import { MessageShortResponse } from "./MessageShortResponse";

export interface DialogShortResponse {
    id : number;
    firstUser : IUser;
    secondUser : IUser;
    lastMessage : MessageShortResponse;
}