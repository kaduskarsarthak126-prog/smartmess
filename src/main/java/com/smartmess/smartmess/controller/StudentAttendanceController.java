package com.smartmess.smartmess.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.smartmess.smartmess.dto.AttendanceRequest;
import com.smartmess.smartmess.dto.AttendanceResponse;
import com.smartmess.smartmess.model.User;
import com.smartmess.smartmess.repository.UserRepository;
import com.smartmess.smartmess.service.AttendanceService;

@RestController
@RequestMapping("/api/student")
public class StudentAttendanceController {

    private final AttendanceService attendanceService;
    private final UserRepository userRepository;

    public StudentAttendanceController(AttendanceService attendanceService,
                                       UserRepository userRepository) {
        this.attendanceService = attendanceService;
        this.userRepository = userRepository;
    }

    // Logged-in student ki id nikalna. studentId kabhi request se nahi lete
    // (warna koi dusre student ki id daal ke uska status badal dega).
    private Long currentStudentId(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User nahi mila"));
        return user.getId();
    }

    // POST /api/student/attendance   body: {"menuId":1,"status":"EATING"}
    @PostMapping("/attendance")
    public AttendanceResponse setAttendance(@RequestBody AttendanceRequest req, Authentication auth) {
        return attendanceService.setAttendance(currentStudentId(auth), req);
    }

    // GET /api/student/attendance?date=2026-10-25
    @GetMapping("/attendance")
    public List<AttendanceResponse> getAttendance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Authentication auth) {
        return attendanceService.getAttendance(currentStudentId(auth), date);
    }
}