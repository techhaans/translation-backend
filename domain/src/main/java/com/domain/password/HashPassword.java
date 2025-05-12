package com.domain.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashPassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String plainPassword = "alice123";
        String hashedPassword = passwordEncoder.encode(plainPassword);
        System.out.println(hashedPassword);
    }
}

