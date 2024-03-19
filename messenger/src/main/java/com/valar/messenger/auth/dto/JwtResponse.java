package com.valar.messenger.auth.dto;

import com.valar.messenger.users.dto.model.UserShortDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {

    private String token;
    private String refreshToken;

    @Builder.Default
    private String type = "Bearer";

    private UserShortDto user;

}
