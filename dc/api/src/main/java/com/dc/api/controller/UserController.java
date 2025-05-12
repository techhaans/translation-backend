package com.dc.api.controller;

import com.domain.model.UserTable;
import com.domain.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @PostMapping("/register")
    public ResponseEntity<UserTable> registerUser(@RequestBody UserTable userTable) {
        return ResponseEntity.ok(userServiceImpl.saveUsernameandpassword(userTable));
    }

}
