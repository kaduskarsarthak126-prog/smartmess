package com.smartmess.smartmess.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
// uniqueConstraints: same date ka same meal (jaise 25 Oct LUNCH) do baar nahi ban sakta.
@Table(name = "menus",
       uniqueConstraints = @UniqueConstraint(columnNames = {"menu_date", "meal_type"}))
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kis din ka menu hai
    @Column(name = "menu_date", nullable = false)
    private LocalDate menuDate;

    // EnumType.STRING: database mein "LUNCH" likha jayega (0,1,2 number nahi), padhne mein aasan.
    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type", nullable = false, length = 20)
    private MealType mealType;

    // Is time ke baad students choice nahi badal sakte
    @Column(name = "choice_deadline", nullable = false)
    private LocalDateTime choiceDeadline;

    public Menu() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getMenuDate() { return menuDate; }
    public void setMenuDate(LocalDate menuDate) { this.menuDate = menuDate; }

    public MealType getMealType() { return mealType; }
    public void setMealType(MealType mealType) { this.mealType = mealType; }

    public LocalDateTime getChoiceDeadline() { return choiceDeadline; }
    public void setChoiceDeadline(LocalDateTime choiceDeadline) { this.choiceDeadline = choiceDeadline; }
}