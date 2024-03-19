package com.valar.messenger.auth.service;

import com.valar.messenger.users.entity.User;
import com.valar.messenger.auth.entity.UserDetailsImpl;
import com.valar.messenger.users.repository.UserRepositoryJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepositoryJPA userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with this username not found"));
        UserDetails userDetails = UserDetailsImpl.build(user);
        return userDetails;
    }
}
