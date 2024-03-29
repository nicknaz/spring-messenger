package com.valar.messenger.dialogs.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MessageShortResponse {

    private Long id;
    private String senderUsername;
    private String text;
    private LocalDateTime dateTime;

}
