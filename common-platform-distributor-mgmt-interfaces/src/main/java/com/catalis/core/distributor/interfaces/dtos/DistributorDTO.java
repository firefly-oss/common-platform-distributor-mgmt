package com.catalis.core.distributor.interfaces.dtos;

import com.catalis.annotations.ValidDateTime;
import com.catalis.annotations.ValidPhoneNumber;
import com.catalis.annotations.ValidTaxId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
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

    private String externalCode;
    private String name;
    private String displayName;
    @ValidTaxId
    private String taxId;
    private String registrationNumber;
    private String websiteUrl;
    @ValidPhoneNumber
    private String phoneNumber;
    @Email
    private String email;
    @Email
    private String supportEmail;
    private String addressLine;
    private String postalCode;
    private String city;
    private String state;
    private String countryCode;
    private Boolean isActive;
    private Boolean isTestDistributor;
    private String timeZone;
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
