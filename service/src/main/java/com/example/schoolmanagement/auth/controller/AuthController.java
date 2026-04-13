package com.example.schoolmanagement.auth.controller;

import com.example.schoolmanagement.auth.dto.request.LoginRequest;
import com.example.schoolmanagement.auth.dto.request.RegisterRequest;
import com.example.schoolmanagement.auth.dto.response.AuthResponse;
import com.example.schoolmanagement.auth.service.LoginUser;
import com.example.schoolmanagement.auth.service.RegisterUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUser registerUser;
    private final LoginUser loginUser;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return registerUser.execute(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return loginUser.execute(request);
    }
}
