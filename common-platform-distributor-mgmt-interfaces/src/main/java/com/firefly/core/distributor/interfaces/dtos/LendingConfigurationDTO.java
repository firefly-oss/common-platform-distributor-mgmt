package com.firefly.core.distributor.interfaces.dtos;
import com.firefly.annotations.ValidAmount;
import com.firefly.annotations.ValidDateTime;
import com.firefly.annotations.ValidInterestRate;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotBlank(message = "Lending configuration name is required")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Lending type is required")
    private LendingTypeDTO lendingType;

    @NotNull(message = "Minimum term months is required")
    @Positive(message = "Minimum term months must be positive")
    private Integer minTermMonths;

    @NotNull(message = "Maximum term months is required")
    @Positive(message = "Maximum term months must be positive")
    private Integer maxTermMonths;

    @NotNull(message = "Default term months is required")
    @Positive(message = "Default term months must be positive")
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
    @Min(value = 0, message = "Grace period days cannot be negative")
    private Integer gracePeriodDays;

    private Boolean isDefault;
    private Boolean isActive;

    @Size(max = 5000, message = "Terms and conditions cannot exceed 5000 characters")
    private String termsConditions;
    @ValidDateTime
    private LocalDateTime createdAt;
    private Long createdBy;
    @ValidDateTime
    private LocalDateTime updatedAt;
    private Long updatedBy;
}
