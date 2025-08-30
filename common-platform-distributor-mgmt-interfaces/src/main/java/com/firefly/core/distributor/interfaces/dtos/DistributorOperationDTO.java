package com.firefly.core.distributor.interfaces.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firefly.annotations.ValidDateTime;
import com.firefly.core.utils.annotations.FilterableId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for DistributorOperation entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorOperationDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @FilterableId
    @NotNull(message = "Distributor ID is required")
    private Long distributorId;

    @NotNull(message = "Country ID is required")
    @Positive(message = "Country ID must be positive")
    private Long countryId;

    @NotNull(message = "Administrative Division ID is required")
    @Positive(message = "Administrative Division ID must be positive")
    private Long administrativeDivisionId;

    private Boolean isActive;

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
