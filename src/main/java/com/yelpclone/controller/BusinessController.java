package com.yelpclone.controller;

import com.yelpclone.dto.BusinessDTO;
import com.yelpclone.service.BusinessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/businesses")
@CrossOrigin(origins = "*")
public class BusinessController {
    
    private final BusinessService businessService;
    
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }
    
    @PostMapping
    public ResponseEntity<BusinessDTO> createBusiness(@RequestBody BusinessDTO businessDTO) {
        return ResponseEntity.ok(businessService.createBusiness(businessDTO));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BusinessDTO> getBusiness(@PathVariable Long id) {
        return ResponseEntity.ok(businessService.getBusiness(id));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<BusinessDTO>> searchBusinesses(@RequestParam String query) {
        return ResponseEntity.ok(businessService.searchBusinesses(query));
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BusinessDTO>> getBusinessesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(businessService.getBusinessesByCategory(category));
    }
    
    @GetMapping("/top-rated")
    public ResponseEntity<List<BusinessDTO>> getTopRatedBusinesses() {
        return ResponseEntity.ok(businessService.getTopRatedBusinesses());
    }
    
    @GetMapping("/most-reviewed")
    public ResponseEntity<List<BusinessDTO>> getMostReviewedBusinesses() {
        return ResponseEntity.ok(businessService.getMostReviewedBusinesses());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BusinessDTO> updateBusiness(@PathVariable Long id, @RequestBody BusinessDTO businessDTO) {
        return ResponseEntity.ok(businessService.updateBusiness(id, businessDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusiness(@PathVariable Long id) {
        businessService.deleteBusiness(id);
        return ResponseEntity.ok().build();
    }
} 