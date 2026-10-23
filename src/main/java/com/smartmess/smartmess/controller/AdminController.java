package com.smartmess.smartmess.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartmess.smartmess.dto.UserResponse;
import com.smartmess.smartmess.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/students/pending")
    public List<UserResponse> pendingStudents() {
        return userService.getPendingStudents();
    }

    @PostMapping("/students/{id}/approve")
    public UserResponse approve(@PathVariable Long id) {
        return userService.approveStudent(id);
    }
}