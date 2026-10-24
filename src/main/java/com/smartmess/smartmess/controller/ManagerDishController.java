package com.smartmess.smartmess.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.smartmess.smartmess.dto.DishRequest;
import com.smartmess.smartmess.dto.DishResponse;
import com.smartmess.smartmess.service.DishService;

// /api/manager/** ka lock SecurityConfig mein pehle se hai (MANAGER ya ADMIN),
// isliye yahan security ka alag code nahi chahiye.
@RestController
@RequestMapping("/api/manager/dishes")
public class ManagerDishController {

    private final DishService dishService;

    public ManagerDishController(DishService dishService) {
        this.dishService = dishService;
    }

    // POST: nayi dish add karo. @RequestBody = JSON ko DishRequest object banata hai.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)   // success par 201 code
    public DishResponse addDish(@RequestBody DishRequest request) {
        return dishService.addDish(request);
    }

    // GET: saari dishes ki list
    @GetMapping
    public List<DishResponse> listDishes() {
        return dishService.listDishes();
    }
}