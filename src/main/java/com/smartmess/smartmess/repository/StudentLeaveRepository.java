package com.smartmess.smartmess.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmess.smartmess.model.StudentLeave;

public interface StudentLeaveRepository extends JpaRepository<StudentLeave, Long> {
    // Kya is date par student chutti par hai? (from <= date <= to)
    List<StudentLeave> findByStudentIdAndFromDateLessThanEqualAndToDateGreaterThanEqual(
            Long studentId, LocalDate date1, LocalDate date2);
}