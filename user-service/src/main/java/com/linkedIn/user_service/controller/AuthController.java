package com.linkedIn.user_service.controller;

import com.linkedIn.user_service.dto.LoginRequestDTO;
import com.linkedIn.user_service.dto.SignupRequestDTO;
import com.linkedIn.user_service.dto.UserDTO;
import com.linkedIn.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    private ResponseEntity<UserDTO> signup(@RequestBody SignupRequestDTO signupRequestDTO){
        UserDTO userDTO = authService.signup(signupRequestDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO){
        String token = authService.login(loginRequestDTO);
        return ResponseEntity.ok(token);
    }
}
