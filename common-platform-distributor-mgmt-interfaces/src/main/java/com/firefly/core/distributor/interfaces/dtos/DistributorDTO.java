package com.firefly.core.distributor.interfaces.dtos;

import com.firefly.annotations.ValidDateTime;
import com.firefly.annotations.ValidPhoneNumber;
import com.firefly.annotations.ValidTaxId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO representing a distributor in the system.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Size(max = 255, message = "External code cannot exceed 255 characters")
    private String externalCode;

    @NotBlank(message = "Distributor name is required")
    @Size(max = 255, message = "Distributor name cannot exceed 255 characters")
    private String name;

    @Size(max = 255, message = "Display name cannot exceed 255 characters")
    private String displayName;

    @ValidTaxId
    private String taxId;

    @Size(max = 100, message = "Registration number cannot exceed 100 characters")
    private String registrationNumber;

    @Size(max = 255, message = "Website URL cannot exceed 255 characters")
    private String websiteUrl;
    @ValidPhoneNumber
    private String phoneNumber;
    @Email
    private String email;
    @Email(message = "Support email must be a valid email address")
    private String supportEmail;

    @Size(max = 255, message = "Address line cannot exceed 255 characters")
    private String addressLine;

    @Size(max = 20, message = "Postal code cannot exceed 20 characters")
    private String postalCode;

    @Size(max = 100, message = "City cannot exceed 100 characters")
    private String city;

    @Size(max = 100, message = "State cannot exceed 100 characters")
    private String state;

    @Pattern(regexp = "^[A-Z]{2}$", message = "Country code must be a valid 2-letter ISO code")
    private String countryCode;
    private Boolean isActive;
    private Boolean isTestDistributor;

    @Size(max = 50, message = "Time zone cannot exceed 50 characters")
    private String timeZone;

    @Size(max = 10, message = "Default locale cannot exceed 10 characters")
    private String defaultLocale;
    @ValidDateTime
    private LocalDateTime onboardedAt;
    @ValidDateTime
    private LocalDateTime terminatedAt;
    @ValidDateTime
    private LocalDateTime createdAt;
    private Long createdBy;
    @ValidDateTime
    private LocalDateTime updatedAt;
    private Long updatedBy;
}
