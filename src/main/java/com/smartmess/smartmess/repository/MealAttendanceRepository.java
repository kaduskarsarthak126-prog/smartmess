package com.smartmess.smartmess.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmess.smartmess.model.MealAttendance;

public interface MealAttendanceRepository extends JpaRepository<MealAttendance, Long> {
    Optional<MealAttendance> findByStudentIdAndMenuId(Long studentId, Long menuId);
}