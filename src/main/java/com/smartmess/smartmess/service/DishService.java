package com.smartmess.smartmess.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.smartmess.smartmess.dto.DishRequest;
import com.smartmess.smartmess.dto.DishResponse;
import com.smartmess.smartmess.model.Dish;
import com.smartmess.smartmess.repository.DishRepository;

// Business logic (rules) yahan likhte hain. Controller sirf request leta hai aur yahan bhej deta hai.
@Service
public class DishService {

    private final DishRepository dishRepository;

    // Constructor injection: Spring khud DishRepository la kar de deta hai.
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public DishResponse addDish(DishRequest request) {
        // Rule 1: naam khali nahi hona chahiye
        if (request.name() == null || request.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dish name is required");
        }
        String name = request.name().trim();

        // Rule 2: same naam ki dish dobara nahi
        if (dishRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Dish already exists: " + name);
        }

        // Rule 3: limit aur portion di hain to 0 se bade hone chahiye
        if (request.defaultLimitKg() != null && request.defaultLimitKg() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Limit must be greater than 0");
        }
        if (request.portionPerPerson() != null && request.portionPerPerson() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Portion must be greater than 0");
        }

        Dish dish = new Dish();
        dish.setName(name);
        dish.setCategory(request.category());
        // Value nahi di to Dish entity ke default (5.0 aur 0.15) hi rahenge
        if (request.defaultLimitKg() != null) dish.setDefaultLimitKg(request.defaultLimitKg());
        if (request.portionPerPerson() != null) dish.setPortionPerPerson(request.portionPerPerson());

        Dish saved = dishRepository.save(dish);   // INSERT query chalti hai
        return DishResponse.from(saved);
    }

    public List<DishResponse> listDishes() {
        // Sab dishes lao aur har ek ko DishResponse mein badal do
        return dishRepository.findAll().stream().map(DishResponse::from).toList();
    }
}