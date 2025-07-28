package com.catalis.core.distributor.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.distributor.interfaces.dtos.LeasingContractDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service for managing leasing contracts.
 */
public interface LeasingContractService {

    /**
     * Create a new leasing contract.
     *
     * @param leasingContractDTO the leasing contract to create
     * @return the created leasing contract
     */
    Mono<LeasingContractDTO> createLeasingContract(LeasingContractDTO leasingContractDTO);

    /**
     * Update an existing leasing contract.
     *
     * @param id the ID of the leasing contract to update
     * @param leasingContractDTO the leasing contract data to update
     * @return the updated leasing contract
     */
    Mono<LeasingContractDTO> updateLeasingContract(Long id, LeasingContractDTO leasingContractDTO);

    /**
     * Delete a leasing contract.
     *
     * @param id the ID of the leasing contract to delete
     * @return a Mono completing when the operation is done
     */
    Mono<Void> deleteLeasingContract(Long id);

    /**
     * Get a leasing contract by ID.
     *
     * @param id the ID of the leasing contract to retrieve
     * @return the leasing contract
     */
    Mono<LeasingContractDTO> getLeasingContractById(Long id);

    /**
     * Get a leasing contract by contract ID.
     *
     * @param contractId the contract ID of the leasing contract to retrieve
     * @return the leasing contract
     */
    Mono<LeasingContractDTO> getLeasingContractByContractId(Long contractId);

    /**
     * Get all leasing contracts for a distributor.
     *
     * @param distributorId the ID of the distributor
     * @return a Flux of leasing contracts
     */
    Flux<LeasingContractDTO> getLeasingContractsByDistributorId(Long distributorId);

    /**
     * Get all leasing contracts for a product.
     *
     * @param productId the ID of the product
     * @return a Flux of leasing contracts
     */
    Flux<LeasingContractDTO> getLeasingContractsByProductId(Long productId);

    /**
     * Get all leasing contracts for a party.
     *
     * @param partyId the ID of the party
     * @return a Flux of leasing contracts
     */
    Flux<LeasingContractDTO> getLeasingContractsByPartyId(Long partyId);

    /**
     * Get all leasing contracts with a specific status.
     *
     * @param status the status
     * @return a Flux of leasing contracts
     */
    Flux<LeasingContractDTO> getLeasingContractsByStatus(String status);

    /**
     * Approve a leasing contract.
     *
     * @param id the ID of the leasing contract to approve
     * @param approvedBy the ID of the user approving the contract
     * @return the approved leasing contract
     */
    Mono<LeasingContractDTO> approveLeasingContract(Long id, Long approvedBy);

    /**
     * Filter leasing contracts based on criteria.
     *
     * @param filterRequest the filter request containing criteria and pagination
     * @return a PaginationResponse with the filtered leasing contracts
     */
    Mono<PaginationResponse<LeasingContractDTO>> filterLeasingContracts(FilterRequest<LeasingContractDTO> filterRequest);
}