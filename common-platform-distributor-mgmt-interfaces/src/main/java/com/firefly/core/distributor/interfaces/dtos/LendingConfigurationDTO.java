package com.firefly.core.distributor.interfaces.dtos;
import com.firefly.annotations.ValidAmount;
import com.firefly.annotations.ValidDateTime;
import com.firefly.annotations.ValidInterestRate;
import com.firefly.core.utils.annotations.FilterableId;
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
    @ValidInterestRate
    private BigDecimal minDownPaymentPercentage;
    @ValidInterestRate
    private BigDecimal defaultDownPaymentPercentage;
    @ValidInterestRate
    private BigDecimal interestRate;
    @ValidInterestRate
    private BigDecimal processingFeePercentage;
    @ValidInterestRate
    private BigDecimal earlyTerminationFeePercentage;
    @ValidInterestRate
    private BigDecimal latePaymentFeeAmount;
    private Integer gracePeriodDays;
    private Boolean isDefault;
    private Boolean isActive;
    private String termsConditions;
    @ValidDateTime
    private LocalDateTime createdAt;
    private Long createdBy;
    @ValidDateTime
    private LocalDateTime updatedAt;
    private Long updatedBy;
}
