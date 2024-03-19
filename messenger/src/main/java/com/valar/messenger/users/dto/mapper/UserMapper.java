package com.valar.messenger.users.dto.mapper;

import com.valar.messenger.users.dto.model.UserDto;
import com.valar.messenger.users.dto.model.UserShortDto;
import com.valar.messenger.users.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static UserDto toUserDto(User newUser) {
        return UserDto.builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .build();
    }

    public static UserShortDto toUserShortDto(User newUser) {
        return UserShortDto.builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .build();
    }

    public static User toUser(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }
}
