package com.yelpclone.repository;

import com.yelpclone.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    List<Business> findByNameContainingIgnoreCase(String name);
    
    List<Business> findByCategory(String category);
    
    @Query("SELECT b FROM Business b WHERE " +
           "LOWER(b.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(b.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(b.category) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Business> searchBusinesses(@Param("query") String query);
    
    List<Business> findByOrderByAverageRatingDesc();
    
    List<Business> findByOrderByTotalReviewsDesc();
} 