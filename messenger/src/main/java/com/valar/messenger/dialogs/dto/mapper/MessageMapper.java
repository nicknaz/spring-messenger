package com.valar.messenger.dialogs.dto.mapper;

import com.valar.messenger.dialogs.dto.*;
import com.valar.messenger.dialogs.entity.Dialog;
import com.valar.messenger.dialogs.entity.Message;
import com.valar.messenger.users.entity.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class MessageMapper {

    public static MessageShortResponse toMessageShortResponse(Message message) {
        return MessageShortResponse.builder()
                .id(message.getId())
                .senderUsername(message.getIdSender().getUsername())
                .text(message.getText())
                .dateTime(message.getDateTime())
                .build();
    }

    public static MessageShortResponseForSocket toMessageShortResponseForSocket(Message message) {
        return MessageShortResponseForSocket.builder()
                .id(message.getId())
                .senderUsername(message.getIdSender().getUsername())
                .text(message.getText())
                .dateTime(message.getDateTime().toString())
                .build();
    }

    public static MessageShortResponseForSocket toMessageShortResponseForSocket(MessageShortResponse message) {
        return MessageShortResponseForSocket.builder()
                .id(message.getId())
                .senderUsername(message.getSenderUsername())
                .text(message.getText())
                .dateTime(message.getDateTime().toString())
                .build();
    }

    public static Message toMessage(MessageRequest request, User user, Dialog dialog) {
        return Message.builder()
                .idDialog(dialog)
                .idSender(user)
                .text(request.getText())
                .dateTime(request.getDateTime())
                .build();
    }

    public static MessageRequest toMessageRequest(MessageForNewDialogRequest request, Long dialogId) {
        return MessageRequest.builder()
                .dialogId(dialogId)
                .senderId(request.getSenderId())
                .text(request.getText())
                .dateTime(request.getDateTime())
                .build();
    }

    public static MessageRequest toMessageRequest(MessageRequestFromSocket request) {
        return MessageRequest.builder()
                .dialogId(Long.parseLong(request.getDialogId()))
                .senderId(Long.parseLong(request.getSenderId()))
                .text(request.getText())
                .dateTime(LocalDateTime.parse(request.getDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")))
                .build();
    }

}
