import { IUser } from "./IUser";
import { MessageShortResponse } from "./MessageShortResponse";

export interface DialogResponse {
    id : number;
    firstUser : IUser;
    secondUser : IUser;
    messages : MessageShortResponse[];
}