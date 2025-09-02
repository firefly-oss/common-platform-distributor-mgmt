package com.firefly.core.distributor.interfaces.dtos;

import com.firefly.annotations.ValidDateTime;
import com.firefly.core.distributor.interfaces.enums.DistributorActionEnum;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
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
 * DTO representing an audit log entry for distributor-related actions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorAuditLogDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @FilterableId
    @NotNull(message = "Distributor ID is required")
    private UUID distributorId;

    @NotNull(message = "Action is required")
    private DistributorActionEnum action;

    @NotBlank(message = "Entity is required")
    @Size(max = 50, message = "Entity cannot exceed 50 characters")
    private String entity;

    @FilterableId
    @NotBlank(message = "Entity ID is required")
    @Size(max = 50, message = "Entity ID cannot exceed 50 characters")
    private String entityId;

    private JsonNode metadata;

    @Pattern(regexp = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$|^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$",
             message = "IP address must be a valid IPv4 or IPv6 address")
    private String ipAddress;

    private UUID userId;

    @ValidDateTime
    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;
}
