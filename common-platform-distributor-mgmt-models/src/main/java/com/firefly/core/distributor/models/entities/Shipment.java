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
 * Entity representing a shipment of a product after a leasing contract is approved.
 * Maps to the 'shipment' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("shipment")
public class Shipment {

    @Id
    private Long id;

    @Column("leasing_contract_id")
    private Long leasingContractId;

    @Column("product_id")
    private Long productId;

    @Column("tracking_number")
    private String trackingNumber;

    @Column("carrier")
    private String carrier;

    @Column("shipping_address")
    private String shippingAddress;

    @Column("shipping_date")
    private LocalDateTime shippingDate;

    @Column("estimated_delivery_date")
    private LocalDateTime estimatedDeliveryDate;

    @Column("actual_delivery_date")
    private LocalDateTime actualDeliveryDate;

    @Column("status")
    private String status;

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