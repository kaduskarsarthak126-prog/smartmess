package com.smartmess.smartmess.dto;

import com.smartmess.smartmess.model.Dish;

// Ye woh data hai jo server wapas bhejta hai (output).
// Entity seedha bhejne ke bajaye DTO bhejte hain, taaki sirf wahi fields bahar jayein jo chahiye.
public record DishResponse(
        Long id,
        String name,
        String category,
        Double defaultLimitKg,
        Double portionPerPerson
) {
    // Dish entity ko DishResponse mein badalne ka chhota helper
    public static DishResponse from(Dish dish) {
        return new DishResponse(
                dish.getId(),
                dish.getName(),
                dish.getCategory(),
                dish.getDefaultLimitKg(),
                dish.getPortionPerPerson()
        );
    }
}