package com.smartmess.smartmess.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmess.smartmess.model.StudentChoice;

public interface StudentChoiceRepository extends JpaRepository<StudentChoice, Long> {
    Optional<StudentChoice> findByStudentIdAndMenuDishId(Long studentId, Long menuDishId);
}