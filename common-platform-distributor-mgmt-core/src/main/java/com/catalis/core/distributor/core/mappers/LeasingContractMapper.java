package com.catalis.core.distributor.core.mappers;

import com.catalis.core.distributor.interfaces.dtos.LeasingContractDTO;
import com.catalis.core.distributor.models.entities.LeasingContract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for converting between LeasingContract entity and LeasingContractDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public abstract class LeasingContractMapper {

    /**
     * Convert a LeasingContract entity to a LeasingContractDTO.
     *
     * @param leasingContract the LeasingContract entity
     * @return the LeasingContractDTO
     */
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "lendingConfiguration", ignore = true)
    public abstract LeasingContractDTO toDto(LeasingContract leasingContract);

    /**
     * Convert a LeasingContractDTO to a LeasingContract entity.
     *
     * @param leasingContractDTO the LeasingContractDTO
     * @return the LeasingContract entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract LeasingContract toEntity(LeasingContractDTO leasingContractDTO);
}