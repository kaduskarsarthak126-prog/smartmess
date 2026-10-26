package com.smartmess.smartmess.dto;

import com.smartmess.smartmess.model.AttendanceStatus;
import com.smartmess.smartmess.model.MealType;

// Student ko wapas dikhane ke liye
public record AttendanceResponse(Long menuId, MealType mealType, AttendanceStatus status, String reason) {}