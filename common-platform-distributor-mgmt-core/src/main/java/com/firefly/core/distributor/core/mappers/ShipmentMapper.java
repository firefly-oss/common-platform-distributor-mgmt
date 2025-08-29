package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.ShipmentDTO;
import com.firefly.core.distributor.models.entities.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for converting between Shipment entity and ShipmentDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, LeasingContractMapper.class})
public abstract class ShipmentMapper {

    /**
     * Convert a Shipment entity to a ShipmentDTO.
     *
     * @param shipment the Shipment entity
     * @return the ShipmentDTO
     */
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "leasingContract", ignore = true)
    public abstract ShipmentDTO toDto(Shipment shipment);

    /**
     * Convert a ShipmentDTO to a Shipment entity.
     *
     * @param shipmentDTO the ShipmentDTO
     * @return the Shipment entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Shipment toEntity(ShipmentDTO shipmentDTO);
}