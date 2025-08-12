package com.catalis.core.distributor.interfaces.dtos;
import com.catalis.annotations.ValidDateTime;
import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Product entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @FilterableId
    private Long distributorId;

    private String name;
    private String description;
    private String sku;
    private String modelNumber;
    private String manufacturer;
    private ProductCategoryDTO category;
    private String imageUrl;
    private JsonNode specifications; // Using JsonNode for flexible JSON structure
    private Boolean isActive;
    @ValidDateTime
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}
