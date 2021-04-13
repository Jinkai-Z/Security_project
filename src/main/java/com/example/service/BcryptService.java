package com.example.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Using Bcrypt Service to encrypt and decrypt password
 */
public class BcryptService {
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String encodePassword(String password) {
        return encoder.encode(password);
    }

    public boolean checkPasswordMatch(String password, String encodedPassword) {
        return encoder.matches(password, encodedPassword);
    }
}
