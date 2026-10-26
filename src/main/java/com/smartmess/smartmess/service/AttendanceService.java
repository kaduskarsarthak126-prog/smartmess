package com.smartmess.smartmess.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.smartmess.smartmess.dto.AttendanceRequest;
import com.smartmess.smartmess.dto.AttendanceResponse;
import com.smartmess.smartmess.model.AttendanceStatus;
import com.smartmess.smartmess.model.MealAttendance;
import com.smartmess.smartmess.model.Menu;
import com.smartmess.smartmess.repository.MealAttendanceRepository;
import com.smartmess.smartmess.repository.MenuRepository;

@Service
public class AttendanceService {

    // Allowed reasons (NOT_EATING ke liye)
    private static final Set<String> REASONS = Set.of("home", "restaurant", "other");

    private final MenuRepository menuRepository;
    private final MealAttendanceRepository attendanceRepository;

    public AttendanceService(MenuRepository menuRepository,
                             MealAttendanceRepository attendanceRepository) {
        this.menuRepository = menuRepository;
        this.attendanceRepository = attendanceRepository;
    }

    // Status set/update karna (pehli baar = insert, baad mein = update)
    public AttendanceResponse setAttendance(Long studentId, AttendanceRequest req) {
        // 1) Basic input check
        if (req.menuId() == null || req.status() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "menuId aur status zaroori hain");
        }
        // NO_RESPONSE student khud set nahi kar sakta, wo default hai
        if (req.status() == AttendanceStatus.NO_RESPONSE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status sirf EATING ya NOT_EATING ho sakta hai");
        }

        // 2) Menu exist karta hai?
        Menu menu = menuRepository.findById(req.menuId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu nahi mila"));

        // 3) Deadline nikal gayi? Tab student kuch change nahi kar sakta
        if (LocalDateTime.now().isAfter(menu.getChoiceDeadline())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deadline nikal gayi, ab change nahi ho sakta");
        }

        // 4) Reason ka rule
        String reason = null;
        if (req.status() == AttendanceStatus.NOT_EATING) {
            if (req.reason() == null || !REASONS.contains(req.reason().toLowerCase())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reason: home, restaurant ya other");
            }
            reason = req.reason().toLowerCase();
        }

        // 5) Purani row hai to update, nahi to nayi banao
        MealAttendance row = attendanceRepository
                .findByStudentIdAndMenuId(studentId, menu.getId())
                .orElseGet(MealAttendance::new);
        row.setStudentId(studentId);
        row.setMenuId(menu.getId());
        row.setStatus(req.status());
        row.setReason(reason);
        attendanceRepository.save(row);

        return new AttendanceResponse(menu.getId(), menu.getMealType(), row.getStatus(), row.getReason());
    }

    // Ek date ke saare meals ka status (row na ho to NO_RESPONSE)
    public List<AttendanceResponse> getAttendance(Long studentId, LocalDate date) {
        return menuRepository.findByMenuDate(date).stream().map(menu -> {
            MealAttendance row = attendanceRepository
                    .findByStudentIdAndMenuId(studentId, menu.getId()).orElse(null);
            if (row == null) {
                return new AttendanceResponse(menu.getId(), menu.getMealType(), AttendanceStatus.NO_RESPONSE, null);
            }
            return new AttendanceResponse(menu.getId(), menu.getMealType(), row.getStatus(), row.getReason());
        }).toList();
    }
}