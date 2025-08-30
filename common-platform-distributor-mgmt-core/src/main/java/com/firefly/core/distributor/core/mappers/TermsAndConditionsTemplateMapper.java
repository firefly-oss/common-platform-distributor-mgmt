package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.TermsAndConditionsTemplateDTO;
import com.firefly.core.distributor.models.entities.TermsAndConditionsTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between TermsAndConditionsTemplate entity and TermsAndConditionsTemplateDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TermsAndConditionsTemplateMapper {

    /**
     * Converts a TermsAndConditionsTemplate entity to a TermsAndConditionsTemplateDTO.
     *
     * @param entity the TermsAndConditionsTemplate entity to convert
     * @return the corresponding TermsAndConditionsTemplateDTO
     */
    TermsAndConditionsTemplateDTO toDTO(TermsAndConditionsTemplate entity);

    /**
     * Converts a TermsAndConditionsTemplateDTO to a TermsAndConditionsTemplate entity.
     *
     * @param dto the TermsAndConditionsTemplateDTO to convert
     * @return the corresponding TermsAndConditionsTemplate entity
     */
    TermsAndConditionsTemplate toEntity(TermsAndConditionsTemplateDTO dto);
}
