package com.smartmess.smartmess.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.smartmess.smartmess.dto.MenuRequest;
import com.smartmess.smartmess.dto.MenuResponse;
import com.smartmess.smartmess.service.MenuService;

@RestController
@RequestMapping("/api/manager/menus")
public class ManagerMenuController {

    private final MenuService menuService;

    public ManagerMenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuResponse createMenu(@RequestBody MenuRequest request) {
        return menuService.createMenu(request);
    }

    // URL: /api/manager/menus?date=2026-10-25
    // @DateTimeFormat batata hai ki date yyyy-MM-dd format mein aayegi.
    @GetMapping
    public List<MenuResponse> getMenus(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return menuService.getMenusByDate(date);
    }
}