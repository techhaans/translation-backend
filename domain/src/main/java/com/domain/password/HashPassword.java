package com.domain.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashPassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("R@hul1git");
        System.out.println(hash);
        System.out.println("HELLOOO");
    }
}
