package com.firefly.core.distributor.interfaces.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firefly.annotations.ValidDateTime;
import com.firefly.core.utils.annotations.FilterableId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for DistributorTermsAndConditions entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorTermsAndConditionsDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @FilterableId
    @NotNull(message = "Distributor ID is required")
    private UUID distributorId;

    private UUID templateId;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "Version is required")
    @Size(max = 50, message = "Version cannot exceed 50 characters")
    private String version;

    @NotNull(message = "Effective date is required")
    @ValidDateTime
    private LocalDateTime effectiveDate;

    @ValidDateTime
    private LocalDateTime expirationDate;

    @ValidDateTime
    private LocalDateTime signedDate;

    private UUID signedBy;

    @Pattern(regexp = "DRAFT|PENDING_SIGNATURE|SIGNED|EXPIRED|TERMINATED", 
             message = "Status must be one of: DRAFT, PENDING_SIGNATURE, SIGNED, EXPIRED, TERMINATED")
    private String status;

    private Boolean isActive;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

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

    // Related entities
    private DistributorDTO distributor;
    private TermsAndConditionsTemplateDTO template;
}
