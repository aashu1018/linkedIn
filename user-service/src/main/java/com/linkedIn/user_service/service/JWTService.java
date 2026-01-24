package com.linkedIn.user_service.service;

import com.linkedIn.user_service.entity.User;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

    public String generateAccessToken(User user) {
        return "abc";
    }
}
