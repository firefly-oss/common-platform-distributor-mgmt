package com.firefly.core.distributor.models.entities;

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
 * Entity representing specific terms and conditions agreed between a distributor and the company.
 * Maps to the 'distributor_terms_and_conditions' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("distributor_terms_and_conditions")
public class DistributorTermsAndConditions {

    @Id
    private Long id;

    @Column("distributor_id")
    private Long distributorId;

    @Column("template_id")
    private Long templateId;

    @Column("title")
    private String title;

    @Column("content")
    private String content;

    @Column("version")
    private String version;

    @Column("effective_date")
    private LocalDateTime effectiveDate;

    @Column("expiration_date")
    private LocalDateTime expirationDate;

    @Column("signed_date")
    private LocalDateTime signedDate;

    @Column("signed_by")
    private Long signedBy;

    @Column("status")
    private String status; // DRAFT, PENDING_SIGNATURE, SIGNED, EXPIRED, TERMINATED

    @Column("is_active")
    private Boolean isActive;

    @Column("notes")
    private String notes;

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
