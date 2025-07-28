package com.catalis.core.distributor.interfaces.dtos;

import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for LeasingContract entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeasingContractDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private Long contractId;

    private Long partyId;

    @FilterableId
    private Long distributorId;

    @FilterableId
    private Long productId;

    private Long lendingConfigurationId;
    
    private ProductDTO product;
    
    private LendingConfigurationDTO lendingConfiguration;

    private LocalDate startDate;
    
    private LocalDate endDate;
    
    private BigDecimal monthlyPayment;
    
    private BigDecimal downPayment;
    
    private BigDecimal totalAmount;
    
    private String status;
    
    private LocalDateTime approvalDate;
    
    private Long approvedBy;
    
    private String termsConditions;
    
    private String notes;
    
    private Boolean isActive;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long createdBy;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long updatedBy;
}