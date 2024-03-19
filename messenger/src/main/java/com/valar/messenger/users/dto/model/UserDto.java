package com.valar.messenger.users.dto.model;

import lombok.Getter;

import lombok.Builder;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Builder
public class UserDto {
    private long id;

    @NotBlank
    @Size(min = 2, max = 250)
    private String username;

    @Email
    @NotBlank
    @Size(min = 5, max = 254)
    private String email;

    @NotBlank
    private String password;

    private Set<String> roles;
}
