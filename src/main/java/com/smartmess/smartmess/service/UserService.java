package com.smartmess.smartmess.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartmess.smartmess.dto.RegisterRequest;
import com.smartmess.smartmess.model.Role;
import com.smartmess.smartmess.model.User;
import com.smartmess.smartmess.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerStudent(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // hash it
        user.setHostelRoom(request.getHostelRoom());
        user.setRole(Role.STUDENT);   // self-registration is always a student
        user.setApproved(false);      // manager/admin approves later
        return userRepository.save(user);
    }
}