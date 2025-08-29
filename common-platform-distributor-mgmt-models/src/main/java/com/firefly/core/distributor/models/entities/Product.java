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
 * Entity representing a product that a distributor manages.
 * Maps to the 'product' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("product")
public class Product {

    @Id
    private Long id;

    @Column("distributor_id")
    private Long distributorId;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("sku")
    private String sku;

    @Column("model_number")
    private String modelNumber;

    @Column("manufacturer")
    private String manufacturer;

    @Column("category_id")
    private ProductCategory category;

    @Column("image_url")
    private String imageUrl;

    @Column("specifications")
    private String specifications; // Stored as JSON string

    @Column("is_active")
    private Boolean isActive;

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