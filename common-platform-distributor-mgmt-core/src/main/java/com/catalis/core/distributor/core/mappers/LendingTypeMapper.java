package com.catalis.core.distributor.core.mappers;

import com.catalis.core.distributor.interfaces.dtos.LendingTypeDTO;
import com.catalis.core.distributor.models.entities.LendingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper for converting between LendingType entity and LendingTypeDTO.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LendingTypeMapper {

    /**
     * Convert a LendingType entity to a LendingTypeDTO.
     *
     * @param lendingType The LendingType entity to convert
     * @return The corresponding LendingTypeDTO
     */
    LendingTypeDTO toDto(LendingType lendingType);

    /**
     * Convert a LendingTypeDTO to a LendingType entity.
     *
     * @param lendingTypeDTO The LendingTypeDTO to convert
     * @return The corresponding LendingType entity
     */
    LendingType toEntity(LendingTypeDTO lendingTypeDTO);
}