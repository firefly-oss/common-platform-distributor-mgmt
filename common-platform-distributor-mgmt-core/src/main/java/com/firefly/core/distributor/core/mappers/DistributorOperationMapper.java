package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.DistributorOperationDTO;
import com.firefly.core.distributor.models.entities.DistributorOperation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between DistributorOperation entity and DistributorOperationDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DistributorOperationMapper {

    /**
     * Converts a DistributorOperation entity to a DistributorOperationDTO.
     *
     * @param entity the DistributorOperation entity to convert
     * @return the corresponding DistributorOperationDTO
     */
    DistributorOperationDTO toDTO(DistributorOperation entity);

    /**
     * Converts a DistributorOperationDTO to a DistributorOperation entity.
     *
     * @param dto the DistributorOperationDTO to convert
     * @return the corresponding DistributorOperation entity
     */
    DistributorOperation toEntity(DistributorOperationDTO dto);
}
