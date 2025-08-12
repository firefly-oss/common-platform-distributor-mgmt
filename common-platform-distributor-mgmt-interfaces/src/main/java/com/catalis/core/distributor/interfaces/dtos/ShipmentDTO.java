package com.catalis.core.distributor.interfaces.dtos;

import com.catalis.annotations.ValidDateTime;
import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Shipment entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @FilterableId
    private Long leasingContractId;
    
    private LeasingContractDTO leasingContract;

    @FilterableId
    private Long productId;
    
    private ProductDTO product;

    private String trackingNumber;
    
    private String carrier;
    
    private String shippingAddress;
    
    private LocalDateTime shippingDate;
    
    private LocalDateTime estimatedDeliveryDate;
    
    private LocalDateTime actualDeliveryDate;
    
    private String status;
    
    private String notes;
    
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