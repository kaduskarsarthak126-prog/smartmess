package com.smartmess.smartmess.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartmess.smartmess.service.MenuService;

// Student sirf menu DEKH sakta hai. /api/student/** sirf STUDENT role ke liye locked hai.
@RestController
@RequestMapping("/api/student")
public class StudentMenuController {

    private final MenuService menuService;

    public StudentMenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // GET /api/student/menus?date=2026-10-25
    @GetMapping("/menus")
    public Object getMenus(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        // Manager wala same service method reuse kar rahe hain (naya code nahi)
        return menuService.getMenusByDate(date);
    }
}