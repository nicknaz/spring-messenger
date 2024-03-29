package com.valar.messenger.dialogs.dto;

import com.valar.messenger.users.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DialogWithMessagesResponse {

    private long id;

    private User firstUser;

    private User secondUser;

    private List<MessageShortResponse> messages;

}
