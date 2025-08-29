package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.DistributorBrandingDTO;
import com.firefly.core.distributor.models.entities.DistributorBranding;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between DistributorBranding entity and DistributorBrandingDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DistributorBrandingMapper {

    /**
     * Converts a DistributorBranding entity to a DistributorBrandingDTO.
     *
     * @param entity the DistributorBranding entity to convert
     * @return the corresponding DistributorBrandingDTO
     */
    DistributorBrandingDTO toDTO(DistributorBranding entity);

    /**
     * Converts a DistributorBrandingDTO to a DistributorBranding entity.
     *
     * @param dto the DistributorBrandingDTO to convert
     * @return the corresponding DistributorBranding entity
     */
    DistributorBranding toEntity(DistributorBrandingDTO dto);
}