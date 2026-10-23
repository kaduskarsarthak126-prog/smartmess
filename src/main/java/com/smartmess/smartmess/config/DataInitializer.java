package com.smartmess.smartmess.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.smartmess.smartmess.model.Role;
import com.smartmess.smartmess.model.User;
import com.smartmess.smartmess.repository.UserRepository;

// Runs once every time the app starts
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Values come from application-local.properties (empty if missing)
    @Value("${smartmess.admin.email:}")
    private String adminEmail;
    @Value("${smartmess.admin.password:}")
    private String adminPassword;
    @Value("${smartmess.manager.email:}")
    private String managerEmail;
    @Value("${smartmess.manager.password:}")
    private String managerPassword;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createIfMissing("Admin", adminEmail, adminPassword, Role.ADMIN);
        createIfMissing("Mess Manager", managerEmail, managerPassword, Role.MANAGER);
    }

    private void createIfMissing(String name, String email, String password, Role role) {
        // Skip if the settings are missing or the account already exists
        if (email.isBlank() || password.isBlank() || userRepository.existsByEmail(email)) {
            return;
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));  // hashed, never plain text
        user.setRole(role);
        user.setApproved(true);                              // staff accounts need no approval
        userRepository.save(user);
    }
}