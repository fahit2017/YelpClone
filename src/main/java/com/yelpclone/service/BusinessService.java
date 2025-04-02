package com.yelpclone.service;

import com.yelpclone.dto.BusinessDTO;
import java.util.List;

public interface BusinessService {
    BusinessDTO createBusiness(BusinessDTO businessDTO);
    
    BusinessDTO getBusiness(Long id);
    
    List<BusinessDTO> searchBusinesses(String query);
    
    List<BusinessDTO> getBusinessesByCategory(String category);
    
    List<BusinessDTO> getTopRatedBusinesses();
    
    List<BusinessDTO> getMostReviewedBusinesses();
    
    BusinessDTO updateBusiness(Long id, BusinessDTO businessDTO);
    
    void deleteBusiness(Long id);
} 