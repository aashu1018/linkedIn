package com.linkedIn.user_service.service;

import com.linkedIn.user_service.PasswordUtil;
import com.linkedIn.user_service.dto.LoginRequestDTO;
import com.linkedIn.user_service.dto.SignupRequestDTO;
import com.linkedIn.user_service.dto.UserDTO;
import com.linkedIn.user_service.entity.User;
import com.linkedIn.user_service.exception.BadRequestException;
import com.linkedIn.user_service.exception.ResourceNotFoundException;
import com.linkedIn.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;

    public UserDTO signup(SignupRequestDTO signupRequestDTO) {

        boolean exists = userRepository.existsByEmail(signupRequestDTO.getEmail());
        if(exists){
            throw new BadRequestException("User with the email " + signupRequestDTO.getEmail() + " is already present");
        }

        User user = modelMapper.map(signupRequestDTO, User.class);
        user.setPassword(PasswordUtil.hashPassword(signupRequestDTO.getPassword()));

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);

    }

    public String login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User with email: " + loginRequestDTO.getEmail() + " not found"));

        boolean isPasswordMatch = PasswordUtil.checkPassword(loginRequestDTO.getPassword(), user.getPassword());
        if(!isPasswordMatch){
            throw new BadRequestException("Password mismatch!");
        }

        return jwtService.generateAccessToken(user);
    }
}
