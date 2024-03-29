package com.valar.messenger.dialogs.service;

import com.valar.messenger.dialogs.dto.DialogResponse;
import com.valar.messenger.dialogs.dto.DialogWithMessagesResponse;
import com.valar.messenger.dialogs.dto.MessageRequestFromSocket;
import com.valar.messenger.dialogs.entity.Dialog;
import com.valar.messenger.users.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DialogService {

    DialogResponse createDialog(User firstUser, User secondUser);
    List<DialogResponse> getAllDialogsByUserId(Long userId);
    DialogWithMessagesResponse getDialogWithMessagesById(Long dialogId);
    Dialog getDialogById(Long dialogId);

    boolean checkAccessByUserId(Long userId, Long dialogId);

    void getNewListDialogsToSocket(Long dialogId);


}
