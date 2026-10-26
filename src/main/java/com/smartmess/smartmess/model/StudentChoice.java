package com.smartmess.smartmess.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

// Dish-level choice: student is dish ko khayega (true) ya nahi (false)
@Entity
@Table(name = "student_choices",
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "menu_dish_id"}))
public class StudentChoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "menu_dish_id", nullable = false)
    private Long menuDishId;

    @Column(name = "will_eat", nullable = false)
    private boolean willEat;

    public Long getId() { return id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getMenuDishId() { return menuDishId; }
    public void setMenuDishId(Long menuDishId) { this.menuDishId = menuDishId; }
    public boolean isWillEat() { return willEat; }
    public void setWillEat(boolean willEat) { this.willEat = willEat; }
}