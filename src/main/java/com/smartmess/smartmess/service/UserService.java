package com.smartmess.smartmess.service;

import com.smartmess.smartmess.dto.RegisterRequest;
import com.smartmess.smartmess.dto.UserResponse;
import com.smartmess.smartmess.model.Role;
import com.smartmess.smartmess.model.User;
import com.smartmess.smartmess.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setHostelRoom(request.getHostelRoom());
        user.setRole(Role.STUDENT);
        user.setApproved(false);
        return userRepository.save(user);
    }

    // Students waiting for approval
    public List<UserResponse> getPendingStudents() {
        return userRepository.findByRoleAndApproved(Role.STUDENT, false)
                .stream()
                .map(UserResponse::new)
                .toList();
    }

    // Approve one student by id
    public UserResponse approveStudent(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (user.getRole() != Role.STUDENT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only students can be approved here");
        }
        user.setApproved(true);
        return new UserResponse(userRepository.save(user));
    }
}