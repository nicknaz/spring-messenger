package com.valar.messenger.users.service;

import com.valar.messenger.users.dto.mapper.UserMapper;
import com.valar.messenger.users.repository.UserRepositoryJPA;
import com.valar.messenger.users.dto.model.UserDto;
import com.valar.messenger.users.dto.model.UserShortDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = false)
public class UserServiceImpl implements UserService {

    private UserRepositoryJPA userRepository;

    @Autowired
    public UserServiceImpl(UserRepositoryJPA userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserShortDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toUserShortDto).collect(Collectors.toList());
    }

    @Override
    public UserDto addUser(UserDto user) {
        if (userRepository.findByEmail(user.getEmail()) != null
            || userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException();
        }
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(user)));
    }
}
