package com.valar.messenger.dialogs.service;

import com.valar.messenger.dialogs.dto.*;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public interface MessageService {

    DialogWithMessagesResponse sendMessage(MessageRequest messageRequest);
    DialogWithMessagesResponse sendFirstMessage(MessageForNewDialogRequest messageRequest);

    List<MessageShortResponse> getMessagesFromDialog(Long dialogId, Pageable pageable);

    void sendMessageFromSocket(MessageRequestFromSocket messageRequestFromSocket);

}
