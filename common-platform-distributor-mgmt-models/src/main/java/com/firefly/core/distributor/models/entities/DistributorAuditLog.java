package com.firefly.core.distributor.models.entities;

import com.firefly.core.distributor.interfaces.enums.DistributorActionEnum;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing an audit log entry for distributor-related actions.
 * Maps to the 'distributor_audit_log' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("distributor_audit_log")
public class DistributorAuditLog {

    @Id
    private UUID id;

    @Column("distributor_id")
    private UUID distributorId;

    @Column("action")
    private DistributorActionEnum action;

    @Column("entity")
    private String entity;

    @Column("entity_id")
    private String entityId;

    @Column("metadata")
    private JsonNode metadata;

    @Column("ip_address")
    private String ipAddress;

    @Column("user_id")
    private UUID userId;

    @Column("timestamp")
    private LocalDateTime timestamp;
}
