package com.valar.messenger.dialogs.service;

import com.valar.messenger.dialogs.dto.*;
import com.valar.messenger.dialogs.dto.mapper.DialogMapper;
import com.valar.messenger.dialogs.dto.mapper.MessageMapper;
import com.valar.messenger.dialogs.entity.Dialog;
import com.valar.messenger.dialogs.entity.Message;
import com.valar.messenger.dialogs.repository.DialogRepositoryJPA;
import com.valar.messenger.dialogs.repository.MessageRepositoryJPA;
import com.valar.messenger.exception.DialogExistException;
import com.valar.messenger.exception.NotFoundedException;
import com.valar.messenger.users.entity.User;
import com.valar.messenger.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private MessageRepositoryJPA messageRepository;
    private DialogRepositoryJPA dialogRepository;
    private UserService userService;
    private SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional
    public DialogWithMessagesResponse sendMessage(MessageRequest messageRequest) {
                Dialog dialog = dialogRepository.findById(messageRequest.getDialogId())
                        .orElseThrow(() -> new NotFoundedException("Dialog not found!"));
                User user = userService.getUserById(messageRequest.getSenderId());
                messageRepository.save(MessageMapper.toMessage(messageRequest, user, dialog));
                List<MessageShortResponse> messages = getMessagesFromDialog(messageRequest.getDialogId(),
                                                                            PageRequest.of(0, 100));
                return DialogMapper.toDialogWithMessagesResponse(dialog, messages);
    }

    @Override
    @Transactional
    public DialogWithMessagesResponse sendFirstMessage(MessageForNewDialogRequest messageRequest) {
        User sender = userService.getUserById(messageRequest.getSenderId());
        User recipient = userService.getUserById(messageRequest.getRecipientId());

        if (dialogRepository.findDialogByUsers(messageRequest.getSenderId(), messageRequest.getRecipientId()) != null) {
            throw new DialogExistException("Dialog already exist!");
        }

        Dialog dialog = dialogRepository.save(Dialog.builder()
                .firstUser(sender)
                .secondUser(recipient)
                .build());

        MessageShortResponse messageShortResponse = MessageMapper.toMessageShortResponse(
                messageRepository.save(MessageMapper.toMessage(
                        MessageMapper.toMessageRequest(messageRequest, dialog.getId()), sender, dialog)
                )
        );

        DialogWithMessagesResponse dialogWithMessagesResponse = DialogMapper.dialogResponseToDialogWithMessagesResponse(
                dialog, List.of(messageShortResponse));

        return dialogWithMessagesResponse;
    }

    @Override
    public List<MessageShortResponse> getMessagesFromDialog(Long dialogId, Pageable pageable) {
        Dialog dialog = dialogRepository.findById(dialogId)
                .orElseThrow(() -> new NotFoundedException("Dialog not found!"));

        List<Message> messages = messageRepository.findAllByIdDialogOrderByDateTimeDesc(dialog, pageable);

        return messages.stream().map(MessageMapper::toMessageShortResponse).collect(Collectors.toList());
    }

    @Override
    public void sendMessageFromSocket(MessageRequestFromSocket messageRequestFromSocket) {
        MessageRequest messageRequest = MessageMapper.toMessageRequest(messageRequestFromSocket);
        messageRequest.setDateTime(LocalDateTime.now());

        Dialog dialog = dialogRepository.findById(messageRequest.getDialogId())
                .orElseThrow(() -> new NotFoundedException("Dialog not found!"));
        User user = userService.getUserById(messageRequest.getSenderId());
        Message message = messageRepository.save(MessageMapper.toMessage(messageRequest, user, dialog));

        messagingTemplate.convertAndSendToUser(
                String.valueOf(dialog.getFirstUser().getId() == user.getId()
                        ? dialog.getSecondUser().getId() : dialog.getFirstUser().getId()),
                "/" + dialog.getId() + "/queue/messages",
                MessageMapper.toMessageShortResponseForSocket(message));
    }
}
