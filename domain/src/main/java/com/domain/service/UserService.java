package com.domain.service;

import com.domain.model.UserTable;

public interface UserService {
    UserTable createUser(String username, String password, String proofreader);
}
