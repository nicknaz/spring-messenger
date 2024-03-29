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

    @NotBlank(message = "Поле username не должно быть пустым!")
    @Size(min = 3, max = 50, message = "Длина поля username должна быть от 3 до 50 знаков")
    private String username;

    @Email(message = "Неверный формат Email!")
    @NotBlank(message = "Поле email не должно быть пустым!")
    @Size(min = 5, max = 254, message = "Длина поля email должна быть от 5 до 254 знаков")
    private String email;

    @NotBlank(message = "Поле password не должно быть пустым!")
    @Size(min = 6, max = 250, message = "Длина поля password должна быть от 6 до 250 знаков")
    private String password;

    private Set<String> roles;
}
