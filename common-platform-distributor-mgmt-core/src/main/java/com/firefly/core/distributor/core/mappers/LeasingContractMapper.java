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

import com.firefly.core.distributor.interfaces.dtos.LeasingContractDTO;
import com.firefly.core.distributor.models.entities.LeasingContract;
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