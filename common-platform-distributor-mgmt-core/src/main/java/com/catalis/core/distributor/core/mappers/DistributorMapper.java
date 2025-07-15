package com.catalis.core.distributor.core.mappers;

import com.catalis.core.distributor.interfaces.dtos.DistributorDTO;
import com.catalis.core.distributor.models.entities.Distributor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between Distributor entity and DistributorDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DistributorMapper {

    /**
     * Converts a Distributor entity to a DistributorDTO.
     *
     * @param entity the Distributor entity to convert
     * @return the corresponding DistributorDTO
     */
    DistributorDTO toDTO(Distributor entity);

    /**
     * Converts a DistributorDTO to a Distributor entity.
     *
     * @param dto the DistributorDTO to convert
     * @return the corresponding Distributor entity
     */
    Distributor toEntity(DistributorDTO dto);
}