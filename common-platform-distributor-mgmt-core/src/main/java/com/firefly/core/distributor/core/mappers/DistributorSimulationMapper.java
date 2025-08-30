package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.DistributorSimulationDTO;
import com.firefly.core.distributor.models.entities.DistributorSimulation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between DistributorSimulation entity and DistributorSimulationDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DistributorSimulationMapper {

    /**
     * Converts a DistributorSimulation entity to a DistributorSimulationDTO.
     *
     * @param entity the DistributorSimulation entity to convert
     * @return the corresponding DistributorSimulationDTO
     */
    DistributorSimulationDTO toDTO(DistributorSimulation entity);

    /**
     * Converts a DistributorSimulationDTO to a DistributorSimulation entity.
     *
     * @param dto the DistributorSimulationDTO to convert
     * @return the corresponding DistributorSimulation entity
     */
    DistributorSimulation toEntity(DistributorSimulationDTO dto);
}
