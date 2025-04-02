package com.yelpclone.dto;

import lombok.Data;

@Data
public class BusinessHoursDTO {
    private Long id;
    private String dayOfWeek;
    private String openTime;
    private String closeTime;
    private boolean isClosed;
} 