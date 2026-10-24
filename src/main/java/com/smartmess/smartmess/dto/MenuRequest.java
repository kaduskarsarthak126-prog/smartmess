package com.smartmess.smartmess.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.smartmess.smartmess.model.MealType;

// Manager ye data bhejta hai menu banane ke liye.
// dishIds = jo dishes is menu mein hongi unki id ki list, jaise [1, 2, 3]
public record MenuRequest(
        LocalDate menuDate,
        MealType mealType,
        LocalDateTime choiceDeadline,
        List<Long> dishIds
) {}