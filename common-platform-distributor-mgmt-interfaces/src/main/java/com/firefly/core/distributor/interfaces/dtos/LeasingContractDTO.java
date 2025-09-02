package com.firefly.core.distributor.interfaces.dtos;

import com.firefly.annotations.ValidAmount;
import com.firefly.annotations.ValidDate;
import com.firefly.annotations.ValidDateTime;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for LeasingContract entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeasingContractDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull(message = "Contract ID is required")
    private UUID contractId;

    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @FilterableId
    @NotNull(message = "Distributor ID is required")
    private UUID distributorId;

    @FilterableId
    @NotNull(message = "Product ID is required")
    private UUID productId;

    @NotNull(message = "Lending configuration ID is required")
    private UUID lendingConfigurationId;

    private ProductDTO product;

    private LendingConfigurationDTO lendingConfiguration;

    @ValidDate
    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @ValidDate
    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @ValidAmount
    @NotNull(message = "Monthly payment is required")
    private BigDecimal monthlyPayment;

    @ValidAmount
    @NotNull(message = "Down payment is required")
    private BigDecimal downPayment;

    @ValidAmount
    @NotNull(message = "Total amount is required")
    private BigDecimal totalAmount;

    @Pattern(regexp = "DRAFT|PENDING|APPROVED|ACTIVE|COMPLETED|CANCELLED|TERMINATED",
             message = "Status must be one of: DRAFT, PENDING, APPROVED, ACTIVE, COMPLETED, CANCELLED, TERMINATED")
    private String status;

    @ValidDateTime
    private LocalDateTime approvalDate;

    private UUID approvedBy;

    @Size(max = 5000, message = "Terms and conditions cannot exceed 5000 characters")
    private String termsConditions;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    private Boolean isActive;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ValidDateTime
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID createdBy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ValidDateTime
    private LocalDateTime updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID updatedBy;
}
