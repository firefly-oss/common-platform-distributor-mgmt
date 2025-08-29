package com.firefly.core.distributor.interfaces.dtos;

import com.firefly.annotations.ValidDateTime;
import com.firefly.core.distributor.interfaces.enums.DistributorActionEnum;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO representing an audit log entry for distributor-related actions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorAuditLogDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @FilterableId
    private Long distributorId;

    private DistributorActionEnum action;
    private String entity;

    @FilterableId
    private String entityId;
    private JsonNode metadata;
    private String ipAddress;
    private Long userId;
    @ValidDateTime
    private LocalDateTime timestamp;
}
