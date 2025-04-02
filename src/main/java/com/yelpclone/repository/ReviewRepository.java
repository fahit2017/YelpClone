package com.yelpclone.repository;

import com.yelpclone.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBusinessId(Long businessId);
    
    List<Review> findByUserId(Long userId);
    
    List<Review> findByBusinessIdOrderByCreatedAtDesc(Long businessId);
    
    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);
} 