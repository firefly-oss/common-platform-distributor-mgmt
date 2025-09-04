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


package com.firefly.core.distributor.models.repositories;

import com.firefly.core.distributor.models.entities.LeasingContract;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing LeasingContract entities.
 */
@Repository
public interface LeasingContractRepository extends BaseRepository<LeasingContract, UUID> {

    /**
     * Find all leasing contracts by distributor ID.
     *
     * @param distributorId the distributor ID
     * @return a Flux of leasing contracts
     */
    Flux<LeasingContract> findByDistributorId(UUID distributorId);

    /**
     * Find all leasing contracts by product ID.
     *
     * @param productId the product ID
     * @return a Flux of leasing contracts
     */
    Flux<LeasingContract> findByProductId(UUID productId);

    /**
     * Find all leasing contracts by party ID.
     *
     * @param partyId the party ID
     * @return a Flux of leasing contracts
     */
    Flux<LeasingContract> findByPartyId(UUID partyId);

    /**
     * Find a leasing contract by contract ID.
     *
     * @param contractId the contract ID
     * @return a Mono of leasing contract
     */
    Mono<LeasingContract> findByContractId(UUID contractId);

    /**
     * Find all active leasing contracts by distributor ID.
     *
     * @param distributorId the distributor ID
     * @return a Flux of active leasing contracts
     */
    Flux<LeasingContract> findByDistributorIdAndIsActiveTrue(UUID distributorId);

    /**
     * Find all leasing contracts by status.
     *
     * @param status the status
     * @return a Flux of leasing contracts
     */
    Flux<LeasingContract> findByStatus(String status);

    /**
     * Find all leasing contracts by distributor ID and status.
     *
     * @param distributorId the distributor ID
     * @param status the status
     * @return a Flux of leasing contracts
     */
    Flux<LeasingContract> findByDistributorIdAndStatus(UUID distributorId, String status);

    /**
     * Find all leasing contracts by product ID and status.
     *
     * @param productId the product ID
     * @param status the status
     * @return a Flux of leasing contracts
     */
    Flux<LeasingContract> findByProductIdAndStatus(UUID productId, String status);

    /**
     * Find all leasing contracts by party ID and status.
     *
     * @param partyId the party ID
     * @param status the status
     * @return a Flux of leasing contracts
     */
    Flux<LeasingContract> findByPartyIdAndStatus(UUID partyId, String status);

    /**
     * Find all leasing contracts approved after a specific date.
     *
     * @param approvalDate the approval date
     * @return a Flux of leasing contracts
     */
    @Query("SELECT * FROM leasing_contract WHERE approval_date >= :approvalDate")
    Flux<LeasingContract> findByApprovalDateAfter(String approvalDate);
}