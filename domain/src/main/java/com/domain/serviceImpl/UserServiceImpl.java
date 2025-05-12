package com.domain.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.domain.model.UserTable;
import com.domain.repo.UserRepository;
import com.domain.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Login method to check if the user's credentials (username, password, and role) match.
     *
     * @param name - Username
     * @param password - Raw password entered by the user
     * @param role - User role (e.g., ADMIN, CUSTOMER)
     * @return Optional<UserTable> - The user if credentials are valid, else empty.
     */
    @Override
    public Optional<UserTable> login(String name, String password, String role) {
        // Check if user exists with given name and role
        System.out.println("Login attempt: ");
        System.out.println("Username: " + name);
        System.out.println("Role: " + role);

        Optional<UserTable> userOpt = userRepository.findByNameAndRole(name, role);

        if (userOpt.isPresent()) {
            UserTable user = userOpt.get();

            // Log the stored hashed password and the raw password entered
            System.out.println("Stored Hashed Password: " + user.getPassword());
            System.out.println("Entered Password: " + password);

            // Compare entered password with stored password (bcrypt hashed)
            if (passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("Password matched! User logged in successfully.");
                return Optional.of(user); // Valid login
            } else {
                System.out.println("Password mismatch!");
            }
        } else {
            System.out.println("User not found!");
        }

        return Optional.empty(); // Invalid login credentials
    }

    /**
     * Save the user in the database after hashing the password.
     *
     * @param userTable - User entity to save
     * @return UserTable - The saved user entity
     */
    @Override
    public UserTable saveUsernameandpassword(UserTable userTable) {
        // Hash password before saving
        String rawPassword = userTable.getPassword();
        String hashedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("Saving user: " + userTable.getName());
        System.out.println("Raw password: " + rawPassword);
        System.out.println("Hashed password: " + hashedPassword);

        userTable.setPassword(hashedPassword);
        return userRepository.save(userTable);
    }
}
