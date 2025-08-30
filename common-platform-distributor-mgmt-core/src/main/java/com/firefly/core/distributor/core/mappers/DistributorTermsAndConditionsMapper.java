package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.DistributorTermsAndConditionsDTO;
import com.firefly.core.distributor.models.entities.DistributorTermsAndConditions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between DistributorTermsAndConditions entity and DistributorTermsAndConditionsDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DistributorTermsAndConditionsMapper {

    /**
     * Converts a DistributorTermsAndConditions entity to a DistributorTermsAndConditionsDTO.
     *
     * @param entity the DistributorTermsAndConditions entity to convert
     * @return the corresponding DistributorTermsAndConditionsDTO
     */
    @Mapping(target = "distributor", ignore = true)
    @Mapping(target = "template", ignore = true)
    DistributorTermsAndConditionsDTO toDTO(DistributorTermsAndConditions entity);

    /**
     * Converts a DistributorTermsAndConditionsDTO to a DistributorTermsAndConditions entity.
     *
     * @param dto the DistributorTermsAndConditionsDTO to convert
     * @return the corresponding DistributorTermsAndConditions entity
     */
    DistributorTermsAndConditions toEntity(DistributorTermsAndConditionsDTO dto);
}
