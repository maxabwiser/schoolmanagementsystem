package com.example.schoolmanagement.auth.service;

import com.example.schoolmanagement.auth.domain.model.entity.User;
import com.example.schoolmanagement.auth.domain.repository.UserRepository;
import com.example.schoolmanagement.auth.dto.request.LoginRequest;
import com.example.schoolmanagement.auth.dto.response.AuthResponse;
import com.example.schoolmanagement.exception.AuthenticationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUser {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse execute(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthenticationFailedException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthenticationFailedException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .build();
    }
}
