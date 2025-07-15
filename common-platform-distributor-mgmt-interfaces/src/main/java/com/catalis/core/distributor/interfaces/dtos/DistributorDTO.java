package com.catalis.core.distributor.interfaces.dtos;

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

    private Long id;
    private String externalCode;
    private String name;
    private String displayName;
    private String taxId;
    private String registrationNumber;
    private String websiteUrl;
    private String phoneNumber;
    private String email;
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
    private LocalDateTime onboardedAt;
    private LocalDateTime terminatedAt;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}