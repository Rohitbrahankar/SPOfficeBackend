package com.Backend.Utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordChecker {

    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordChecker() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public String encodePassword(String rawPassword){
        return passwordEncoder.encode(rawPassword);
    }

    public boolean checkPassword(String rawPassword, String storedHash) {
        return passwordEncoder.matches(rawPassword, storedHash);
    }
}
