package com.study.security.controller;

import com.study.security.dto.token.TokenDTO;
import com.study.security.dto.user.UserRequestDTO;
import com.study.security.exception.ErrorHandler;
import com.study.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController extends ErrorHandler {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        userService.create(userRequestDTO);
    }

    @PostMapping("/auth")
    public TokenDTO authenticate(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.login(userRequestDTO);
    }
}
