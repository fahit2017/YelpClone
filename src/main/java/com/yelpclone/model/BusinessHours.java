package com.yelpclone.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "business_hours")
public class BusinessHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @Column(nullable = false)
    private String dayOfWeek;

    @Column(nullable = false)
    private String openTime;

    @Column(nullable = false)
    private String closeTime;

    private boolean isClosed = false;
} 