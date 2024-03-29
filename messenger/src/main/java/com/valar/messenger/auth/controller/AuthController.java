package com.valar.messenger.auth.controller;

import com.valar.messenger.auth.dto.JwtResponse;
import com.valar.messenger.auth.dto.LoginRequest;
import com.valar.messenger.auth.dto.MessegeResponse;
import com.valar.messenger.config.jwt.JwtUtils;
import com.valar.messenger.users.dto.model.*;
import com.valar.messenger.auth.entity.RefreshToken;
import com.valar.messenger.users.entity.Role;
import com.valar.messenger.users.entity.User;
import com.valar.messenger.auth.entity.UserDetailsImpl;
import com.valar.messenger.auth.repository.RefreshTokenRepositoryJPA;
import com.valar.messenger.users.repository.UserRepositoryJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/messenger/auth")
@Slf4j
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepositoryJPA userRespository;

    @Autowired
    RefreshTokenRepositoryJPA refreshTokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(HttpServletResponse response, @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        String refresh = jwtUtils.generateJwtRefreshToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        refreshTokenRepository.save(RefreshToken.builder()
                .user_id(userDetails.getId())
                .refresh_token(refresh)
                .build());


        Cookie cookie = new Cookie("refresh", refresh);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(864000);
        response.addCookie(cookie);


        return ResponseEntity.ok(JwtResponse.builder()
                .token(jwt)
                .user(
                        UserShortDto.builder()
                                .id(userDetails.getId())
                                .username(userDetails.getUsername())
                                .email(userDetails.getEmail())
                                .build()
                )
                .build());
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletResponse response, @CookieValue(value = "refresh") String data) throws AuthException {
        if (jwtUtils.validateJwtRefreshToken(data)) {
            String username = jwtUtils.getUserNameFromRefreshToken(data);
            User user = userRespository.findByUsername(username).get();
            RefreshToken refreshTokenSaved = refreshTokenRepository.findById(user.getId()).get();
            if (data.equals(refreshTokenSaved.getRefresh_token())) {

                String jwt = jwtUtils.generateJwtToken(username);
                String refresh = jwtUtils.generateJwtRefreshToken(username);


                refreshTokenRepository.save(RefreshToken.builder()
                        .user_id(user.getId())
                        .refresh_token(refresh)
                        .build());


                Cookie cookie = new Cookie("refresh", refresh);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setMaxAge(864000);
                response.addCookie(cookie);


                return ResponseEntity.ok(JwtResponse.builder()
                        .token(jwt)
                        .user(
                                UserShortDto.builder()
                                        .id(user.getId())
                                        .username(username)
                                        .email(user.getEmail())
                                        .build()
                        )
                        .build());
            }
        }
        throw new AuthException("Refresh token no valid");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {

        if (userRespository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessegeResponse("Error: Username is exist"));
        }

        if (userRespository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessegeResponse("Error: Email is exist"));
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        Set<String> reqRoles = userDto.getRoles();
        Set<Role> roles = new HashSet<>();

        if (reqRoles == null) {
            roles.add(Role.USER);
        } else {
            reqRoles.forEach(r -> {
                switch (r) {
                    case "admin":
                        roles.add(Role.ADMIN);

                        break;
                    case "mod":
                        roles.add(Role.MODERATOR);
                        break;

                    default:
                        roles.add(Role.USER);
                }
            });
        }

        user.setRoles(roles);
        userRespository.save(user);
        return ResponseEntity.ok(new MessegeResponse("User CREATED"));

    }


}