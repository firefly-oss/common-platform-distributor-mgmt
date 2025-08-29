package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.DistributorAuditLogDTO;
import com.firefly.core.distributor.models.entities.DistributorAuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between DistributorAuditLog entity and DistributorAuditLogDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DistributorAuditLogMapper {

    /**
     * Converts a DistributorAuditLog entity to a DistributorAuditLogDTO.
     *
     * @param entity the DistributorAuditLog entity to convert
     * @return the corresponding DistributorAuditLogDTO
     */
    DistributorAuditLogDTO toDTO(DistributorAuditLog entity);

    /**
     * Converts a DistributorAuditLogDTO to a DistributorAuditLog entity.
     *
     * @param dto the DistributorAuditLogDTO to convert
     * @return the corresponding DistributorAuditLog entity
     */
    DistributorAuditLog toEntity(DistributorAuditLogDTO dto);
}