package com.tech.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.model.User;
import com.tech.reprositroy.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public Optional<User> login(String name, String password, String role) {
		return userRepository.findByNameAndPasswordAndRole(name, password, role);
	}
}
