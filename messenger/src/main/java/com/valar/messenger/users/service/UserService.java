package com.valar.messenger.users.service;

import com.valar.messenger.users.dto.model.UserDto;
import com.valar.messenger.users.dto.model.UserShortDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<UserShortDto> getAllUsers();

    UserDto addUser(UserDto user);

}
