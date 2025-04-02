package com.yelpclone.service.impl;

import com.yelpclone.dto.BusinessDTO;
import com.yelpclone.dto.BusinessHoursDTO;
import com.yelpclone.model.Business;
import com.yelpclone.model.BusinessHours;
import com.yelpclone.repository.BusinessRepository;
import com.yelpclone.service.BusinessService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BusinessServiceImpl implements BusinessService {
    
    private final BusinessRepository businessRepository;
    
    public BusinessServiceImpl(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }
    
    @Override
    public BusinessDTO createBusiness(BusinessDTO businessDTO) {
        Business business = new Business();
        BeanUtils.copyProperties(businessDTO, business);
        
        if (businessDTO.getBusinessHours() != null) {
            List<BusinessHours> businessHours = businessDTO.getBusinessHours().stream()
                .map(hoursDTO -> {
                    BusinessHours hours = new BusinessHours();
                    BeanUtils.copyProperties(hoursDTO, hours);
                    hours.setBusiness(business);
                    return hours;
                })
                .collect(Collectors.toList());
            business.setBusinessHours(businessHours);
        }
        
        Business savedBusiness = businessRepository.save(business);
        return convertToDTO(savedBusiness);
    }
    
    @Override
    public BusinessDTO getBusiness(Long id) {
        Business business = businessRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Business not found"));
        return convertToDTO(business);
    }
    
    @Override
    public List<BusinessDTO> searchBusinesses(String query) {
        return businessRepository.searchBusinesses(query).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<BusinessDTO> getBusinessesByCategory(String category) {
        return businessRepository.findByCategory(category).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<BusinessDTO> getTopRatedBusinesses() {
        return businessRepository.findByOrderByAverageRatingDesc().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<BusinessDTO> getMostReviewedBusinesses() {
        return businessRepository.findByOrderByTotalReviewsDesc().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public BusinessDTO updateBusiness(Long id, BusinessDTO businessDTO) {
        Business business = businessRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Business not found"));
        
        BeanUtils.copyProperties(businessDTO, business, "id", "businessHours");
        
        if (businessDTO.getBusinessHours() != null) {
            business.getBusinessHours().clear();
            List<BusinessHours> businessHours = businessDTO.getBusinessHours().stream()
                .map(hoursDTO -> {
                    BusinessHours hours = new BusinessHours();
                    BeanUtils.copyProperties(hoursDTO, hours);
                    hours.setBusiness(business);
                    return hours;
                })
                .collect(Collectors.toList());
            business.getBusinessHours().addAll(businessHours);
        }
        
        Business updatedBusiness = businessRepository.save(business);
        return convertToDTO(updatedBusiness);
    }
    
    @Override
    public void deleteBusiness(Long id) {
        businessRepository.deleteById(id);
    }
    
    private BusinessDTO convertToDTO(Business business) {
        BusinessDTO dto = new BusinessDTO();
        BeanUtils.copyProperties(business, dto);
        
        List<BusinessHoursDTO> hoursDTOs = business.getBusinessHours().stream()
            .map(hours -> {
                BusinessHoursDTO hoursDTO = new BusinessHoursDTO();
                BeanUtils.copyProperties(hours, hoursDTO);
                return hoursDTO;
            })
            .collect(Collectors.toList());
        
        dto.setBusinessHours(hoursDTOs);
        return dto;
    }
} 