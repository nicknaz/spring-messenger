package com.valar.messenger.dialogs.controller;

import com.valar.messenger.auth.entity.UserDetailsImpl;
import com.valar.messenger.dialogs.dto.*;
import com.valar.messenger.dialogs.dto.mapper.DialogMapper;
import com.valar.messenger.dialogs.dto.mapper.MessageMapper;
import com.valar.messenger.dialogs.entity.Dialog;
import com.valar.messenger.dialogs.entity.Message;
import com.valar.messenger.dialogs.repository.DialogRepositoryJPA;
import com.valar.messenger.dialogs.repository.MessageRepositoryJPA;
import com.valar.messenger.exception.AccessDeniedException;
import com.valar.messenger.dialogs.service.DialogService;
import com.valar.messenger.dialogs.service.MessageService;
import com.valar.messenger.exception.NotFoundedException;
import com.valar.messenger.users.entity.User;
import com.valar.messenger.users.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(path = "messenger/dialogs/messages")
public class MessageController {

    private MessageService messageService;
    private DialogService dialogService;


    @GetMapping
    public List<MessageShortResponse> getMessagesByDialogId(@RequestParam Long dialogId,
                                                            @RequestParam(defaultValue = "0") int from,
                                                            @RequestParam(defaultValue = "100") int size) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (!dialogService.checkAccessByUserId(userDetails.getId(), dialogId)) {
            throw new AccessDeniedException("You do not have access to the selected dialog!");
        }

        return messageService.getMessagesFromDialog(dialogId, PageRequest.of(from / size, size));
    }

    @PostMapping("/newDialog")
    public DialogWithMessagesResponse sendMessage(@RequestBody MessageForNewDialogRequest messageRequest) {
        log.info(messageRequest.getDateTime() +  ": User with id = " + messageRequest.getSenderId()
                + "send message to user with id = " + messageRequest.getRecipientId());

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        messageRequest.setSenderId(userDetails.getId());
        DialogWithMessagesResponse dialog = messageService.sendFirstMessage(messageRequest);
        dialogService.getNewListDialogsToSocket(dialog.getId());

        return dialog;
    }

    @PostMapping
    public DialogWithMessagesResponse sendMessage(@RequestBody MessageRequest messageRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        log.info("ID AUTH: " + userDetails.getId());

        if (!dialogService.checkAccessByUserId(userDetails.getId(), messageRequest.getDialogId())) {
            throw new AccessDeniedException("You do not have access to the selected dialog!");
        }

        messageRequest.setSenderId(userDetails.getId());

        log.info("User with id = " + messageRequest.getSenderId()
                + "send message in dialog with id = " + messageRequest.getDialogId());
        return messageService.sendMessage(messageRequest);
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload MessageRequestFromSocket messageRequestFromSocket) {
        log.info("Send message to WebSocket!");
        messageService.sendMessageFromSocket(messageRequestFromSocket);
        dialogService.getNewListDialogsToSocket(Long.parseLong(messageRequestFromSocket.getDialogId()));
    }

}