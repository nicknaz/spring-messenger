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

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

    @GetMapping("/search")
    public List<UserShortDto> getUsersByUsername(@RequestParam @NotNull String searchRequest) {
        log.info("Search users by username with request: " + searchRequest);
        if (searchRequest.length() < 2) {
           return new ArrayList<UserShortDto>();
        }
        return userService.getUsersByUsername(searchRequest);
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



}
