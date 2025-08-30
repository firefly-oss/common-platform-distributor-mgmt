package com.firefly.core.distributor.models.entities;

import com.fasterxml.jackson.databind.JsonNode;
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
 * Entity representing a template for dynamically creating terms and conditions.
 * Maps to the 'terms_and_conditions_template' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("terms_and_conditions_template")
public class TermsAndConditionsTemplate {

    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("category")
    private String category; // GENERAL, LENDING, OPERATIONAL, COMPLIANCE, etc.

    @Column("template_content")
    private String templateContent; // Template with placeholders like {{distributorName}}, {{effectiveDate}}

    @Column("variables")
    private JsonNode variables; // JSON defining available variables and their types

    @Column("version")
    private String version;

    @Column("is_default")
    private Boolean isDefault;

    @Column("is_active")
    private Boolean isActive;

    @Column("approval_required")
    private Boolean approvalRequired;

    @Column("auto_renewal")
    private Boolean autoRenewal;

    @Column("renewal_period_months")
    private Integer renewalPeriodMonths;

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
