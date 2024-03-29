package com.valar.messenger.dialogs.service;

import com.valar.messenger.dialogs.dto.*;
import com.valar.messenger.dialogs.dto.mapper.DialogMapper;
import com.valar.messenger.dialogs.dto.mapper.MessageMapper;
import com.valar.messenger.dialogs.entity.Dialog;
import com.valar.messenger.dialogs.entity.Message;
import com.valar.messenger.dialogs.repository.MessageRepositoryJPA;
import com.valar.messenger.exception.DialogExistException;
import com.valar.messenger.dialogs.repository.DialogRepositoryJPA;
import com.valar.messenger.users.entity.User;
import com.valar.messenger.exception.NotFoundedException;
import com.valar.messenger.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DialogServiceImpl implements DialogService{

    private DialogRepositoryJPA dialogRepository;
    private UserService userService;
    private MessageRepositoryJPA messageRepository;
    private SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional
    public DialogResponse createDialog(User firstUser, User secondUser) {

        if (dialogRepository.findDialogByUsers(firstUser.getId(), secondUser.getId()) != null) {
            throw new DialogExistException("Dialog already exist!");
        }

        return DialogMapper.toDialogResponse(dialogRepository.save(Dialog.builder()
                                                                        .firstUser(firstUser)
                                                                        .secondUser(secondUser)
                                                                        .build()), null);
    }

    @Override
    public List<DialogResponse> getAllDialogsByUserId(Long userId) {
        User user = userService.getUserById(userId);
        List<Dialog> dialogs = dialogRepository.findDialogsByUser(userId);

        List<DialogResponse> dialogsResponse = dialogs.stream().map(
                x ->
                    DialogMapper.toDialogResponse(
                            x,
                            MessageMapper.toMessageShortResponse(
                                    Optional.ofNullable(
                                            messageRepository
                                                    .findAllByIdDialogOrderByDateTimeDesc(x, PageRequest.of(0, 10))
                                                    .get(0)
                                    ).orElse(Message.builder().build())
                            )
                    )
        ).collect(Collectors.toList());

        return dialogsResponse;
    }

    @Override
    public boolean checkAccessByUserId(Long userId, Long dialogId) {
        User user = userService.getUserById(userId);
        List<Dialog> dialogs = dialogRepository.findDialogsByUserAndDialogId(userId, dialogId);
        return !dialogs.isEmpty();
    }

    @Override
    public DialogWithMessagesResponse getDialogWithMessagesById(Long dialogId) {
        Dialog dialog = getDialogById(dialogId);
        List<MessageShortResponse> messages =
                messageRepository.findAllByIdDialogOrderByDateTimeDesc(dialog,
                                                                        PageRequest.of(0, 100)
                                                                        )
                                                                        .stream()
                                                                        .map(MessageMapper::toMessageShortResponse)
                                                                        .collect(Collectors.toList());
        return DialogMapper.toDialogWithMessagesResponse(dialog, messages);
    }

    @Override
    public Dialog getDialogById(Long dialogId) {
        return dialogRepository.findById(dialogId).orElseThrow(() -> new NotFoundedException("Dialog not found!"));
    }

    @Override
    public void getNewListDialogsToSocket(Long dialogId) {
        Dialog dialog = dialogRepository.findById(dialogId)
                .orElseThrow(() -> new NotFoundedException("Dialog not found!"));

        List<DialogResponseForSocket> dialogsUser1 = getAllDialogsByUserId(dialog.getFirstUser().getId())
                .stream()
                .map(DialogMapper::toDialogResponseForSocket)
                .collect(Collectors.toList());
        List<DialogResponseForSocket> dialogsUser2 = getAllDialogsByUserId(dialog.getSecondUser().getId())
                .stream()
                .map(DialogMapper::toDialogResponseForSocket)
                .collect(Collectors.toList());

        messagingTemplate.convertAndSendToUser(
                String.valueOf(dialog.getFirstUser().getId()),
                "/queue/chats",
                dialogsUser1);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(dialog.getSecondUser().getId()),
                "/queue/chats",
                dialogsUser2);
    }
}
