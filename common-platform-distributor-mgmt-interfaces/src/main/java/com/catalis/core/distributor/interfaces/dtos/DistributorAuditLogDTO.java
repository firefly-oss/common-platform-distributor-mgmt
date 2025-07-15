package com.catalis.core.distributor.interfaces.dtos;

import com.catalis.core.distributor.interfaces.enums.DistributorActionEnum;
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

    private Long id;
    private Long distributorId;
    private DistributorActionEnum action;
    private String entity;
    private String entityId;
    private JsonNode metadata;
    private String ipAddress;
    private Long userId;
    private LocalDateTime timestamp;
}