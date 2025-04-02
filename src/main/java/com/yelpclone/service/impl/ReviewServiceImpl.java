package com.yelpclone.service.impl;

import com.yelpclone.dto.ReviewDTO;
import com.yelpclone.model.Business;
import com.yelpclone.model.Review;
import com.yelpclone.model.User;
import com.yelpclone.repository.BusinessRepository;
import com.yelpclone.repository.ReviewRepository;
import com.yelpclone.repository.UserRepository;
import com.yelpclone.service.ReviewService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;
    
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                           BusinessRepository businessRepository,
                           UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
    }
    
    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Business business = businessRepository.findById(reviewDTO.getBusinessId())
            .orElseThrow(() -> new RuntimeException("Business not found"));
            
        User user = userRepository.findById(reviewDTO.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        Review review = new Review();
        BeanUtils.copyProperties(reviewDTO, review);
        review.setBusiness(business);
        review.setUser(user);
        
        Review savedReview = reviewRepository.save(review);
        
        // Update business average rating and total reviews
        updateBusinessRating(business);
        
        return convertToDTO(savedReview);
    }
    
    @Override
    public ReviewDTO getReview(Long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found"));
        return convertToDTO(review);
    }
    
    @Override
    public List<ReviewDTO> getReviewsByBusiness(Long businessId) {
        return reviewRepository.findByBusinessIdOrderByCreatedAtDesc(businessId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<ReviewDTO> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found"));
            
        BeanUtils.copyProperties(reviewDTO, review, "id", "business", "user");
        
        Review updatedReview = reviewRepository.save(review);
        
        // Update business average rating
        updateBusinessRating(review.getBusiness());
        
        return convertToDTO(updatedReview);
    }
    
    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found"));
            
        Business business = review.getBusiness();
        reviewRepository.deleteById(id);
        
        // Update business average rating
        updateBusinessRating(business);
    }
    
    private void updateBusinessRating(Business business) {
        List<Review> reviews = reviewRepository.findByBusinessId(business.getId());
        
        if (reviews.isEmpty()) {
            business.setAverageRating(0.0);
            business.setTotalReviews(0);
        } else {
            double averageRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
                
            business.setAverageRating(averageRating);
            business.setTotalReviews(reviews.size());
        }
        
        businessRepository.save(business);
    }
    
    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        BeanUtils.copyProperties(review, dto);
        dto.setBusinessId(review.getBusiness().getId());
        dto.setUserId(review.getUser().getId());
        dto.setUsername(review.getUser().getUsername());
        return dto;
    }
} 