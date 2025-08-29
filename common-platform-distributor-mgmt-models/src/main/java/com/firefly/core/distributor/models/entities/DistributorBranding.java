package com.firefly.core.distributor.models.entities;

import com.firefly.core.distributor.interfaces.enums.ThemeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entity representing branding information for a distributor.
 * Maps to the 'distributor_branding' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("distributor_branding")
public class DistributorBranding {

    @Id
    private Long id;

    @Column("distributor_id")
    private Long distributorId;

    @Column("logo_url")
    private String logoUrl;

    @Column("favicon_url")
    private String faviconUrl;

    @Column("primary_color")
    private String primaryColor;

    @Column("secondary_color")
    private String secondaryColor;

    @Column("background_color")
    private String backgroundColor;

    @Column("font_family")
    private String fontFamily;

    @Column("theme")
    private ThemeEnum theme;

    @Column("is_default")
    private Boolean isDefault;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("created_by")
    private Long createdBy;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("updated_by")
    private Long updatedBy;
}