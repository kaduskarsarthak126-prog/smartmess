package com.smartmess.smartmess.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmess.smartmess.model.MealType;
import com.smartmess.smartmess.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    // Is date ka is meal ka menu pehle se hai kya?
    boolean existsByMenuDateAndMealType(LocalDate menuDate, MealType mealType);

    // Ek din ke saare menus (breakfast, lunch, dinner)
    List<Menu> findByMenuDate(LocalDate menuDate);
}