/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.DistributorAuthorizedTerritoryDTO;
import com.firefly.core.distributor.models.entities.DistributorAuthorizedTerritory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between DistributorAuthorizedTerritory entity and DistributorAuthorizedTerritoryDTO.
 */
@Mapper(componentModel = "spring")
public interface DistributorAuthorizedTerritoryMapper {
    DistributorAuthorizedTerritoryMapper INSTANCE = Mappers.getMapper(DistributorAuthorizedTerritoryMapper.class);
    
    /**
     * Converts DistributorAuthorizedTerritory entity to DistributorAuthorizedTerritoryDTO.
     *
     * @param entity the DistributorAuthorizedTerritory entity to convert
     * @return the converted DistributorAuthorizedTerritoryDTO
     */
    DistributorAuthorizedTerritoryDTO toDTO(DistributorAuthorizedTerritory entity);
    
    /**
     * Converts DistributorAuthorizedTerritoryDTO to DistributorAuthorizedTerritory entity.
     *
     * @param dto the DistributorAuthorizedTerritoryDTO to convert
     * @return the converted DistributorAuthorizedTerritory entity
     */
    DistributorAuthorizedTerritory toEntity(DistributorAuthorizedTerritoryDTO dto);
}

