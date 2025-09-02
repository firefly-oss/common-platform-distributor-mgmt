package com.firefly.core.distributor.interfaces.dtos;
import com.firefly.annotations.ValidDateTime;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Product entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @FilterableId
    @NotNull(message = "Distributor ID is required")
    private UUID distributorId;

    @NotBlank(message = "Product name is required")
    @Size(max = 255, message = "Product name cannot exceed 255 characters")
    private String name;

    @Size(max = 1000, message = "Product description cannot exceed 1000 characters")
    private String description;

    @Size(max = 100, message = "SKU cannot exceed 100 characters")
    private String sku;

    @Size(max = 100, message = "Model number cannot exceed 100 characters")
    private String modelNumber;

    @Size(max = 255, message = "Manufacturer cannot exceed 255 characters")
    private String manufacturer;
    private ProductCategoryDTO category;

    @Size(max = 255, message = "Image URL cannot exceed 255 characters")
    private String imageUrl;

    private JsonNode specifications; // Using JsonNode for flexible JSON structure
    private Boolean isActive;

    @ValidDateTime
    private LocalDateTime createdAt;
    private UUID createdBy;

    @ValidDateTime
    private LocalDateTime updatedAt;
    private UUID updatedBy;
}
