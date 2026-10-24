package com.smartmess.smartmess.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.smartmess.smartmess.model.MealType;

// Server wapas menu aur uski dishes bhejta hai.
public record MenuResponse(
        Long id,
        LocalDate menuDate,
        MealType mealType,
        LocalDateTime choiceDeadline,
        List<DishResponse> dishes
) {}