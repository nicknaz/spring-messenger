package com.valar.messenger.dialogs.dto.mapper;

import com.valar.messenger.dialogs.dto.DialogResponse;
import com.valar.messenger.dialogs.dto.DialogResponseForSocket;
import com.valar.messenger.dialogs.dto.DialogWithMessagesResponse;
import com.valar.messenger.dialogs.dto.MessageShortResponse;
import com.valar.messenger.dialogs.entity.Dialog;
import com.valar.messenger.users.dto.model.UserDto;
import com.valar.messenger.users.entity.User;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class DialogMapper {

    public static Dialog toDialog(DialogResponse dialogResponse) {
        return Dialog.builder()
                .id(dialogResponse.getId())
                .firstUser(dialogResponse.getFirstUser())
                .secondUser(dialogResponse.getSecondUser())
                .build();
    }

    public static DialogResponse toDialogResponse(Dialog dialog, MessageShortResponse messageShortResponse) {
        return DialogResponse.builder()
                .id(dialog.getId())
                .firstUser(dialog.getFirstUser())
                .secondUser(dialog.getSecondUser())
                .lastMessage(messageShortResponse)
                .build();
    }

    public static DialogWithMessagesResponse toDialogWithMessagesResponse(Dialog dialog, List<MessageShortResponse> messages) {
        return DialogWithMessagesResponse.builder()
                .id(dialog.getId())
                .firstUser(dialog.getFirstUser())
                .secondUser(dialog.getSecondUser())
                .messages(messages)
                .build();
    }

    public static DialogWithMessagesResponse dialogResponseToDialogWithMessagesResponse(DialogResponse dialog, List<MessageShortResponse> messages) {
        return DialogWithMessagesResponse.builder()
                .id(dialog.getId())
                .firstUser(dialog.getFirstUser())
                .secondUser(dialog.getSecondUser())
                .messages(messages)
                .build();
    }

    public static DialogWithMessagesResponse dialogResponseToDialogWithMessagesResponse(Dialog dialog, List<MessageShortResponse> messages) {
        return DialogWithMessagesResponse.builder()
                .id(dialog.getId())
                .firstUser(dialog.getFirstUser())
                .secondUser(dialog.getSecondUser())
                .messages(messages)
                .build();
    }

    public static DialogResponseForSocket toDialogResponseForSocket(DialogResponse dialogResponse) {
        return DialogResponseForSocket.builder()
                .id(dialogResponse.getId())
                .firstUser(dialogResponse.getFirstUser())
                .secondUser(dialogResponse.getSecondUser())
                .lastMessage(MessageMapper.toMessageShortResponseForSocket(dialogResponse.getLastMessage()))
                .build();
    }

}
