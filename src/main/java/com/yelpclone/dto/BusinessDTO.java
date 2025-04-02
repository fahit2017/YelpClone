package com.yelpclone.dto;

import lombok.Data;
import java.util.List;

@Data
public class BusinessDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String website;
    private String description;
    private String category;
    private Double latitude;
    private Double longitude;
    private Double averageRating;
    private Integer totalReviews;
    private List<BusinessHoursDTO> businessHours;
} 