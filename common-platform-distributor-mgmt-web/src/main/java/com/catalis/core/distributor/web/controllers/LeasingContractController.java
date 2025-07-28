package com.catalis.core.distributor.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.distributor.core.services.LeasingContractService;
import com.catalis.core.distributor.interfaces.dtos.LeasingContractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing leasing contracts.
 */
@RestController
@RequestMapping("/api/v1/leasing-contracts")
public class LeasingContractController {

    @Autowired
    private LeasingContractService leasingContractService;

    /**
     * POST /api/v1/leasing-contracts/filter : Filter leasing contracts
     *
     * @param filterRequest the filter request containing criteria and pagination
     * @return the ResponseEntity with status 200 (OK) and the list of leasing contracts in body
     */
    @PostMapping("/filter")
    public Mono<ResponseEntity<PaginationResponse<LeasingContractDTO>>> filterLeasingContracts(
            @RequestBody FilterRequest<LeasingContractDTO> filterRequest) {
        
        return leasingContractService.filterLeasingContracts(filterRequest)
                .map(ResponseEntity::ok);
    }

    /**
     * POST /api/v1/leasing-contracts : Create a new leasing contract
     *
     * @param leasingContractDTO the leasing contract to create
     * @return the ResponseEntity with status 201 (Created) and with body the new leasing contract
     */
    @PostMapping
    public Mono<ResponseEntity<LeasingContractDTO>> createLeasingContract(
            @RequestBody LeasingContractDTO leasingContractDTO) {
        
        return leasingContractService.createLeasingContract(leasingContractDTO)
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    /**
     * PUT /api/v1/leasing-contracts/{id} : Update an existing leasing contract
     *
     * @param id the ID of the leasing contract to update
     * @param leasingContractDTO the leasing contract to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated leasing contract
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<LeasingContractDTO>> updateLeasingContract(
            @PathVariable Long id,
            @RequestBody LeasingContractDTO leasingContractDTO) {
        
        return leasingContractService.updateLeasingContract(id, leasingContractDTO)
                .map(ResponseEntity::ok);
    }

    /**
     * DELETE /api/v1/leasing-contracts/{id} : Delete a leasing contract
     *
     * @param id the ID of the leasing contract to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteLeasingContract(@PathVariable Long id) {
        
        return leasingContractService.deleteLeasingContract(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    /**
     * GET /api/v1/leasing-contracts/{id} : Get a leasing contract by ID
     *
     * @param id the ID of the leasing contract to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leasing contract
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<LeasingContractDTO>> getLeasingContractById(@PathVariable Long id) {
        
        return leasingContractService.getLeasingContractById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/v1/leasing-contracts/contract/{contractId} : Get a leasing contract by contract ID
     *
     * @param contractId the contract ID of the leasing contract to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leasing contract
     */
    @GetMapping("/contract/{contractId}")
    public Mono<ResponseEntity<LeasingContractDTO>> getLeasingContractByContractId(@PathVariable Long contractId) {
        
        return leasingContractService.getLeasingContractByContractId(contractId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/v1/leasing-contracts/distributor/{distributorId} : Get all leasing contracts for a distributor
     *
     * @param distributorId the ID of the distributor
     * @return the ResponseEntity with status 200 (OK) and with body the list of leasing contracts
     */
    @GetMapping("/distributor/{distributorId}")
    public Mono<ResponseEntity<Flux<LeasingContractDTO>>> getLeasingContractsByDistributorId(@PathVariable Long distributorId) {
        
        Flux<LeasingContractDTO> contracts = leasingContractService.getLeasingContractsByDistributorId(distributorId);
        return Mono.just(ResponseEntity.ok(contracts));
    }

    /**
     * GET /api/v1/leasing-contracts/product/{productId} : Get all leasing contracts for a product
     *
     * @param productId the ID of the product
     * @return the ResponseEntity with status 200 (OK) and with body the list of leasing contracts
     */
    @GetMapping("/product/{productId}")
    public Mono<ResponseEntity<Flux<LeasingContractDTO>>> getLeasingContractsByProductId(@PathVariable Long productId) {
        
        Flux<LeasingContractDTO> contracts = leasingContractService.getLeasingContractsByProductId(productId);
        return Mono.just(ResponseEntity.ok(contracts));
    }

    /**
     * GET /api/v1/leasing-contracts/party/{partyId} : Get all leasing contracts for a party
     *
     * @param partyId the ID of the party
     * @return the ResponseEntity with status 200 (OK) and with body the list of leasing contracts
     */
    @GetMapping("/party/{partyId}")
    public Mono<ResponseEntity<Flux<LeasingContractDTO>>> getLeasingContractsByPartyId(@PathVariable Long partyId) {
        
        Flux<LeasingContractDTO> contracts = leasingContractService.getLeasingContractsByPartyId(partyId);
        return Mono.just(ResponseEntity.ok(contracts));
    }

    /**
     * GET /api/v1/leasing-contracts/status/{status} : Get all leasing contracts with a specific status
     *
     * @param status the status
     * @return the ResponseEntity with status 200 (OK) and with body the list of leasing contracts
     */
    @GetMapping("/status/{status}")
    public Mono<ResponseEntity<Flux<LeasingContractDTO>>> getLeasingContractsByStatus(@PathVariable String status) {
        
        Flux<LeasingContractDTO> contracts = leasingContractService.getLeasingContractsByStatus(status);
        return Mono.just(ResponseEntity.ok(contracts));
    }

    /**
     * POST /api/v1/leasing-contracts/{id}/approve : Approve a leasing contract
     *
     * @param id the ID of the leasing contract to approve
     * @param approvedBy the ID of the user approving the contract
     * @return the ResponseEntity with status 200 (OK) and with body the approved leasing contract
     */
    @PostMapping("/{id}/approve")
    public Mono<ResponseEntity<LeasingContractDTO>> approveLeasingContract(
            @PathVariable Long id,
            @RequestParam Long approvedBy) {
        
        return leasingContractService.approveLeasingContract(id, approvedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}