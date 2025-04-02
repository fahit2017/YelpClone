package com.yelpclone.repository;

import com.yelpclone.model.BusinessHours;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BusinessHoursRepository extends JpaRepository<BusinessHours, Long> {
    List<BusinessHours> findByBusinessId(Long businessId);
    
    List<BusinessHours> findByBusinessIdAndDayOfWeek(Long businessId, String dayOfWeek);
} 