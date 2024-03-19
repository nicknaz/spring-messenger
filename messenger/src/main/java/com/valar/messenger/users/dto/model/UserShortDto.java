package com.valar.messenger.users.dto.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class UserShortDto {
    private long id;

    private String username;
    private String email;

}
