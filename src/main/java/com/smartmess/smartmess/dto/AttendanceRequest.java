package com.smartmess.smartmess.dto;

import com.smartmess.smartmess.model.AttendanceStatus;

// Student ye bhejega: kis menu ke liye, EATING ya NOT_EATING, aur reason (sirf NOT_EATING mein)
public record AttendanceRequest(Long menuId, AttendanceStatus status, String reason) {}