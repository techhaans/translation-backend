package com.dc.api.controller;

import java.util.Optional;
import java.util.UUID;

import com.domain.dto.LoginResponseDTO;
import com.domain.model.Customer;
import com.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.model.UserTable;
import com.domain.service.UserService;
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")

public class AuthController
{
    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserTable user) {
        String name = user.getName();
        String password = user.getPassword();
        String role = user.getRole();

        if (name == null || password == null || role == null || name.isEmpty() || password.isEmpty() || role.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing username, password, or role");
        }

        Optional<UserTable> foundUser = userService.login(name, password, role);

        if (foundUser.isPresent()) {
            UserTable u = foundUser.get();
            UUID cuid = null;

            // Try to fetch Customer using the current user — if it exists, get the cuid
            Customer customer = customerService.findByUser(u);
            if (customer != null) {
                cuid = customer.getCuid();
            }

            LoginResponseDTO response = new LoginResponseDTO((long) u.getId(), u.getName(), u.getRole(), cuid);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}
