package com.catalis.core.distributor.core.mappers;

import com.catalis.core.distributor.interfaces.dtos.ProductCategoryDTO;
import com.catalis.core.distributor.models.entities.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper for converting between ProductCategory entity and ProductCategoryDTO.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductCategoryMapper {

    /**
     * Convert a ProductCategory entity to a ProductCategoryDTO.
     *
     * @param productCategory The ProductCategory entity to convert
     * @return The corresponding ProductCategoryDTO
     */
    ProductCategoryDTO toDto(ProductCategory productCategory);

    /**
     * Convert a ProductCategoryDTO to a ProductCategory entity.
     *
     * @param productCategoryDTO The ProductCategoryDTO to convert
     * @return The corresponding ProductCategory entity
     */
    ProductCategory toEntity(ProductCategoryDTO productCategoryDTO);
}