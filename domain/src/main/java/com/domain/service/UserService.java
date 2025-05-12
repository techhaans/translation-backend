package com.domain.service;

import com.domain.model.UserTable;

import java.util.Optional;

public interface UserService {
     Optional<UserTable> login(String name, String password, String role);

     UserTable saveUsernameandpassword(UserTable userTable);
}
