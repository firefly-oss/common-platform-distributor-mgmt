package com.firefly.core.distributor.interfaces.dtos;

import com.firefly.annotations.ValidDateTime;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Shipment entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @FilterableId
    @NotNull(message = "Leasing contract ID is required")
    private UUID leasingContractId;

    private LeasingContractDTO leasingContract;

    @FilterableId
    @NotNull(message = "Product ID is required")
    private UUID productId;

    private ProductDTO product;

    @Size(max = 100, message = "Tracking number cannot exceed 100 characters")
    private String trackingNumber;

    @Size(max = 100, message = "Carrier cannot exceed 100 characters")
    private String carrier;

    @NotBlank(message = "Shipping address is required")
    @Size(max = 500, message = "Shipping address cannot exceed 500 characters")
    private String shippingAddress;
    
    @ValidDateTime
    private LocalDateTime shippingDate;

    @ValidDateTime
    private LocalDateTime estimatedDeliveryDate;

    @ValidDateTime
    private LocalDateTime actualDeliveryDate;

    @Pattern(regexp = "PENDING|SHIPPED|IN_TRANSIT|DELIVERED|CANCELLED|RETURNED",
             message = "Status must be one of: PENDING, SHIPPED, IN_TRANSIT, DELIVERED, CANCELLED, RETURNED")
    private String status;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ValidDateTime
    private LocalDateTime createdAt;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID createdBy;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ValidDateTime
    private LocalDateTime updatedAt;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID updatedBy;
}