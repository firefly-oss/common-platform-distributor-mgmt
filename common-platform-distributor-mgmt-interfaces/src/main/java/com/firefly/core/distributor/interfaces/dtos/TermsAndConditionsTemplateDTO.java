package com.firefly.core.distributor.interfaces.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.firefly.annotations.ValidDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for TermsAndConditionsTemplate entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TermsAndConditionsTemplateDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Template name is required")
    @Size(max = 255, message = "Template name cannot exceed 255 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotBlank(message = "Category is required")
    @Pattern(regexp = "GENERAL|LENDING|OPERATIONAL|COMPLIANCE|MARKETING|TECHNICAL", 
             message = "Category must be one of: GENERAL, LENDING, OPERATIONAL, COMPLIANCE, MARKETING, TECHNICAL")
    private String category;

    @NotBlank(message = "Template content is required")
    private String templateContent;

    private JsonNode variables; // JSON defining available variables and their types

    @NotBlank(message = "Version is required")
    @Size(max = 50, message = "Version cannot exceed 50 characters")
    private String version;

    private Boolean isDefault;

    private Boolean isActive;

    private Boolean approvalRequired;

    private Boolean autoRenewal;

    @Positive(message = "Renewal period must be positive")
    private Integer renewalPeriodMonths;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ValidDateTime
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long createdBy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ValidDateTime
    private LocalDateTime updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long updatedBy;
}
