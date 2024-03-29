package com.valar.messenger.dialogs.dto;

import com.valar.messenger.users.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DialogResponseForSocket {

    private long id;

    private User firstUser;

    private User secondUser;

    private MessageShortResponseForSocket lastMessage;

}
