package com.catalis.core.distributor.interfaces.dtos;
import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for LendingConfiguration entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LendingConfigurationDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @FilterableId
    private Long productId;

    private String name;
    private String description;
    private LendingTypeDTO lendingType;
    private Integer minTermMonths;
    private Integer maxTermMonths;
    private Integer defaultTermMonths;
    private BigDecimal minDownPaymentPercentage;
    private BigDecimal defaultDownPaymentPercentage;
    private BigDecimal interestRate;
    private BigDecimal processingFeePercentage;
    private BigDecimal earlyTerminationFeePercentage;
    private BigDecimal latePaymentFeeAmount;
    private Integer gracePeriodDays;
    private Boolean isDefault;
    private Boolean isActive;
    private String termsConditions;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}