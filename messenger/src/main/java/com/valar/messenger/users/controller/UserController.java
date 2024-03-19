package com.valar.messenger.users.controller;

import com.valar.messenger.users.dto.model.UserDto;
import com.valar.messenger.users.dto.model.UserShortDto;
import com.valar.messenger.auth.entity.UserDetailsImpl;
import com.valar.messenger.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@Slf4j
@RequestMapping(path = "messenger/user")
public class UserController {
    private UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserShortDto> getAllUsers() {
        log.info("get users");
        return userService.getAllUsers();
    }

    @GetMapping("/profile")
    public UserShortDto getProfile() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return UserShortDto.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .build();
    }

    @GetMapping("/add/{test}")
    public UserDto addNewUser(@PathVariable("test") String test) {
        log.info("addNewUser");
        return userService.addUser(UserDto.builder().username(test).email(test + "@test.ts").build());
    }

    @PostMapping
    public String test(){
        return "Her";
    }


}
