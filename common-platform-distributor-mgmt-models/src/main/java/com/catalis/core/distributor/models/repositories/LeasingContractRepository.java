package com.catalis.core.distributor.models.repositories;

import com.catalis.core.distributor.models.entities.LeasingContract;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository for managing LeasingContract entities.
 */
@Repository
public interface LeasingContractRepository extends BaseRepository<LeasingContract, Long> {

    /**
     * Find all leasing contracts by distributor ID.
     *
     * @param distributorId the distributor ID
     * @return a Flux of leasing contracts
     */
    Flux<LeasingContract> findByDistributorId(Long distributorId);

    /**
     * Find all leasing contracts by product ID.
     *
     * @param productId the product ID
     * @return a Flux of leasing contracts
     */
    Flux<LeasingContract> findByProductId(Long productId);

    /**
     * Find all leasing contracts by party ID.
     *
     * @param partyId the party ID
     * @return a Flux of leasing contracts
     */
    Flux<LeasingContract> findByPartyId(Long partyId);

    /**
     * Find a leasing contract by contract ID.
     *
     * @param contractId the contract ID
     * @return a Mono of leasing contract
     */
    Mono<LeasingContract> findByContractId(Long contractId);

    /**
     * Find all active leasing contracts by distributor ID.
     *
     * @param distributorId the distributor ID
     * @return a Flux of active leasing contracts
     */
    Flux<LeasingContract> findByDistributorIdAndIsActiveTrue(Long distributorId);

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
    Flux<LeasingContract> findByDistributorIdAndStatus(Long distributorId, String status);

    /**
     * Find all leasing contracts by product ID and status.
     *
     * @param productId the product ID
     * @param status the status
     * @return a Flux of leasing contracts
     */
    Flux<LeasingContract> findByProductIdAndStatus(Long productId, String status);

    /**
     * Find all leasing contracts by party ID and status.
     *
     * @param partyId the party ID
     * @param status the status
     * @return a Flux of leasing contracts
     */
    Flux<LeasingContract> findByPartyIdAndStatus(Long partyId, String status);

    /**
     * Find all leasing contracts approved after a specific date.
     *
     * @param approvalDate the approval date
     * @return a Flux of leasing contracts
     */
    @Query("SELECT * FROM leasing_contract WHERE approval_date >= :approvalDate")
    Flux<LeasingContract> findByApprovalDateAfter(String approvalDate);
}