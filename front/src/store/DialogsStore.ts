import { makeAutoObservable } from "mobx";
import { IUser } from "../models/IUser";
import { DialogShortResponse } from "../models/DialogShortResponse";
import $api, { SERVER_URL } from "../http";
import DialogService from "../services/DialogService";
import UserService from "../services/UserService";
import { MessageShortResponse } from "../models/MessageShortResponse";
import { DialogResponse } from "../models/DialogResponse";
import { userStore } from "..";
import MessageService from "../services/MessageService";
import dayjs from "dayjs";

export default class DialogsStore {
    currentRecepient = {} as IUser;
    currentDialog = {} as DialogResponse;
    messages = [] as MessageShortResponse[];

    usersSearchResults = [] as IUser[];
    userDialogs = [] as DialogShortResponse[];
    isSearches = false as boolean;
    isDialog = false as boolean;
    isNewDialog = false as boolean;

    stompClient : any = {};


    constructor() {
        makeAutoObservable(this);
        this.connect();
        this.onMessageReceived.bind(this);
        this.onChatsReceived.bind(this);
    }

    async connect() {
        const Stomp = require("stompjs");
        var SockJS = require("sockjs-client");

        console.log("Подключение");
        SockJS = new SockJS("https://petbrager.ru/inapi/ws");
        this.stompClient = Stomp.over(SockJS);
        this.stompClient.connect({}, this.onError);
        setTimeout(() => {
            this.stompClient.subscribe(
                "/user/" + userStore.user.id + "/queue/chats",
                this.onChatsReceived.bind(this)
              );
         }, 4000);
    };

    subscribeDialog() {
        console.log("Подписка");

        this.stompClient.subscribe(
          "/user/" + userStore.user.id + "/" + this.currentDialog.id + "/queue/messages",
          this.onMessageReceived.bind(this)
        );
    };

    subscribeChats() {
        console.log("Подписка");

        this.stompClient.subscribe(
          "/user/" + userStore.user.id + "/queue/chats",
          this.onChatsReceived.bind(this)
        );
    };
    
    onError(err : any) {
        console.log(err);
    };

    onChatsReceived(msg : any) {
        console.log("Получен новый список чатов");
        console.log(this.messages);

        const newDialogs : DialogShortResponse[] = JSON.parse(msg.body);
        //message.dateTime = dayjs(JSON.parse(msg.body)['dateTime']).toDate()
        this.setDialogs(newDialogs);
    };

    onMessageReceived(msg : any) {
        console.log("Получено новое сообщение");
        console.log(this.messages);

        const message : MessageShortResponse = JSON.parse(msg.body);
        message.dateTime = dayjs(JSON.parse(msg.body)['dateTime']).toDate()
        this.messages.unshift(message);
    };

    sendMessageInWS(msg : string) {
        if (msg.trim() !== "") {
          const message = {
            dialogId: ""+this.currentDialog.id,
            senderId: ""+userStore.user.id,
            text: msg,
            dateTime: dayjs().toISOString(),
          };
          this.messages.unshift({
            "id" : this.currentDialog.id,
            "senderUsername" : userStore.user.username,
            "text" : msg,
            "dateTime" : dayjs().toDate()
        });
          this.stompClient.send("/app/chat", {}, JSON.stringify(message));
        }
      };

    setUsersSearchResults(usersSearchResults: IUser[]) {
        this.usersSearchResults = usersSearchResults;
    }

    setDialogs(dialogs: DialogShortResponse[]) {
        dialogs.sort(function(obj1, obj2){
            return obj1.lastMessage.dateTime > obj2.lastMessage.dateTime ? -1 : 1;
            });
        this.userDialogs = dialogs;
    }

    setCurrentDialog(dialog: DialogResponse) {
        this.currentDialog = dialog;
        this.subscribeDialog();
    }

    setMessages(messages: MessageShortResponse[]) {
        this.messages = messages;
        console.log("time:" + this.messages.at(0)!.dateTime);
    }

    setIsSearched(value : boolean) {
        this.isSearches = value;
    }

    setIsDialog(value : boolean) {
        this.isDialog = value;
    }

    setIsNewDialog(value : boolean) {
        this.isNewDialog = value;
    }

    chooseUser(user : IUser) {

        if (this.userDialogs.some((ud) => {return ud.firstUser.id == user.id || ud.secondUser.id == user.id})) {
            this.chooseDialog(this.userDialogs.filter((ud) => {return ud.firstUser.id == user.id || ud.secondUser.id == user.id}).at(0)!);
        } else {
            this.currentRecepient = user;
            this.setIsDialog(true);
            this.setIsNewDialog(true);
        }
    }

    chooseDialog(shortDialog : DialogShortResponse) {
        this.currentRecepient = shortDialog.firstUser.username == userStore.user.username ? shortDialog.secondUser : shortDialog.firstUser;
        this.getDialog(shortDialog.id);
        this.setIsDialog(true);
        this.setIsNewDialog(false);
    }

    async getUserDialogs() {
        try {
            const response = await DialogService.getAllDialogsByUserId();
            this.setDialogs(response.data);     
            console.log("Получены диалоги");
        } catch (e: any) {
            console.log(e.response?.data?.message);
        }
    }

    async getDialog(id : number) {
        try {
            const response = await DialogService.getDialogById(id);
            this.setCurrentDialog(response.data);       
            this.setMessages(this.currentDialog.messages);   
            console.log("Получен диалог");
        } catch (e: any) {
            console.log(e.response?.data?.message);
        }
    }

    async sendMessage(text : string) {
        try {
            var response;
            if (this.isNewDialog) {
                response = await MessageService.sendFirstMessage(this.currentRecepient.id, text);
                this.setIsNewDialog(false);
                this.setCurrentDialog(response.data);       
                this.setMessages(this.currentDialog.messages); 
            } else {
                this.sendMessageInWS(text);
                //response = await MessageService.sendMessage(this.currentDialog.id, text);
            }  
            console.log("Получен диалог");
        } catch (e: any) {
            console.log(e.response?.data?.message);
        }
    }

    async searchUsers(searchRequest : String) {
        try {
            const response = await UserService.searchUsers(searchRequest);
            console.log("Поиск");
            this.setUsersSearchResults(response.data);       
        } catch (e: any) {
            console.log(e.response?.data?.message);
        }
    }

  
}