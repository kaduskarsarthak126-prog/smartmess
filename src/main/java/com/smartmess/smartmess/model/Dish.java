package com.smartmess.smartmess.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity ka matlab: ye Java class database ki ek table banegi.
@Entity
@Table(name = "dishes")
public class Dish {

    // Primary key. Database khud 1, 2, 3... number deta hai (AUTO_INCREMENT).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dish ka naam, jaise "Paneer Curry". unique = true: same naam do baar nahi aa sakta.
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    // Category, jaise "Sabzi", "Dal", "Rice", "Roti", "Sweet"
    @Column(length = 50)
    private String category;

    // Itna leftover (kg) se zyada bacha to NGO alert jayega (Module 6 mein use hoga)
    @Column(name = "default_limit_kg", nullable = false)
    private Double defaultLimitKg = 5.0;

    // Ek student ke liye kitna banana hai (kg mein), jaise dal = 0.15 kg (Module 5 mein use hoga)
    @Column(name = "portion_per_person", nullable = false)
    private Double portionPerPerson = 0.15;

    // JPA ko khali constructor chahiye hota hai
    public Dish() {}

    // Getters aur setters: baaki code in se value padhta/badalta hai
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getDefaultLimitKg() { return defaultLimitKg; }
    public void setDefaultLimitKg(Double defaultLimitKg) { this.defaultLimitKg = defaultLimitKg; }

    public Double getPortionPerPerson() { return portionPerPerson; }
    public void setPortionPerPerson(Double portionPerPerson) { this.portionPerPerson = portionPerPerson; }
}