package com.smartmess.smartmess.dto;

// Ye woh data hai jo manager Postman/browser se bhejta hai (input).
// Double (capital D) isliye ki value na bheji ho to null aa sake, aur hum default laga sakein.
public record DishRequest(
        String name,
        String category,
        Double defaultLimitKg,
        Double portionPerPerson
) {}
