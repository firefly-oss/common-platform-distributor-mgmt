package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.LendingConfigurationDTO;
import com.firefly.core.distributor.models.entities.LendingConfiguration;
import org.mapstruct.Mapper;

/**
 * Mapper for converting between LendingConfiguration entity and LendingConfigurationDTO.
 */
@Mapper(componentModel = "spring")
public interface LendingConfigurationMapper {

    /**
     * Convert a LendingConfiguration entity to a LendingConfigurationDTO.
     *
     * @param lendingConfiguration the LendingConfiguration entity
     * @return the LendingConfigurationDTO
     */
    LendingConfigurationDTO toDTO(LendingConfiguration lendingConfiguration);

    /**
     * Convert a LendingConfigurationDTO to a LendingConfiguration entity.
     *
     * @param lendingConfigurationDTO the LendingConfigurationDTO
     * @return the LendingConfiguration entity
     */
    LendingConfiguration toEntity(LendingConfigurationDTO lendingConfigurationDTO);
}