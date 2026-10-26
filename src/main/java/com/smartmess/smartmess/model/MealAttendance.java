package com.smartmess.smartmess.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

// Ek student ka ek meal (menu) ke liye status.
// unique: ek student ek menu ke liye sirf ek row rakh sakta hai.
@Entity
@Table(name = "meal_attendance",
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "menu_id"}))
public class MealAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Enumerated(EnumType.STRING)   // DB mein text ("EATING") save hota hai, number nahi
    @Column(nullable = false)
    private AttendanceStatus status;

    private String reason;         // home / restaurant / other (sirf NOT_EATING mein)

    public Long getId() { return id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getMenuId() { return menuId; }
    public void setMenuId(Long menuId) { this.menuId = menuId; }
    public AttendanceStatus getStatus() { return status; }
    public void setStatus(AttendanceStatus status) { this.status = status; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}