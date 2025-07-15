package com.catalis.core.distributor.interfaces.dtos;

import com.catalis.core.distributor.interfaces.enums.ThemeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO representing branding information for a distributor.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorBrandingDTO {

    private Long id;
    private Long distributorId;
    private String logoUrl;
    private String faviconUrl;
    private String primaryColor;
    private String secondaryColor;
    private String backgroundColor;
    private String fontFamily;
    private ThemeEnum theme;
    private Boolean isDefault;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}