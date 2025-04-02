package com.yelpclone.service;

import com.yelpclone.dto.ReviewDTO;
import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(ReviewDTO reviewDTO);
    
    ReviewDTO getReview(Long id);
    
    List<ReviewDTO> getReviewsByBusiness(Long businessId);
    
    List<ReviewDTO> getReviewsByUser(Long userId);
    
    ReviewDTO updateReview(Long id, ReviewDTO reviewDTO);
    
    void deleteReview(Long id);
} 