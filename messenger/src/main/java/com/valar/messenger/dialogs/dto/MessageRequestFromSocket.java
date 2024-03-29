package com.valar.messenger.dialogs.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestFromSocket {

    private String dialogId;
    private String senderId;
    private String text;
    private String dateTime;

}
