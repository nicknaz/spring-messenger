package com.valar.messenger.users.service;

import com.valar.messenger.users.dto.model.UserDto;
import com.valar.messenger.users.dto.model.UserShortDto;
import com.valar.messenger.users.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<UserShortDto> getUsersByUsername(String partOfName);

    UserDto addUser(UserDto user);

    User getUserById(Long userId);
}
