package com.smartmess.smartmess.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmess.smartmess.model.Dish;

// JpaRepository se save, findAll, findById, delete jaise methods bina code likhe mil jate hain.
// <Dish, Long> ka matlab: entity Dish hai aur uski id ka type Long hai.
public interface DishRepository extends JpaRepository<Dish, Long> {

    // Spring method ke naam se khud query bana leta hai: SELECT ... WHERE name = ?
    boolean existsByName(String name);
}