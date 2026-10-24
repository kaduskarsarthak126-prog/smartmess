package com.smartmess.smartmess.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmess.smartmess.model.MenuDish;

public interface MenuDishRepository extends JpaRepository<MenuDish, Long> {

    // Ek menu ki saari dishes. "MenuId" ka matlab menu.id par filter.
    List<MenuDish> findByMenuId(Long menuId);
}