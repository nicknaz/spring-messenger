package com.valar.messenger.dialogs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MessageRequest {

    private Long dialogId;
    private Long senderId;
    private String text;
    private LocalDateTime dateTime;

}
