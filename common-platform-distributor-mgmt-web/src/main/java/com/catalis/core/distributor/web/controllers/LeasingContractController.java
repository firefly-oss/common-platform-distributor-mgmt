package com.firefly.core.distributor.web.controllers;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.distributor.core.services.LeasingContractService;
import com.firefly.core.distributor.interfaces.dtos.LeasingContractDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing leasing contracts.
 */
@RestController
@RequestMapping("/api/v1/leasing-contracts")
@Tag(name = "Leasing Contract", description = "API for managing leasing contracts")
public class LeasingContractController {

    @Autowired
    private LeasingContractService leasingContractService;

    /**
     * POST /api/v1/leasing-contracts/filter : Filter leasing contracts
     *
     * @param filterRequest the filter request containing criteria and pagination
     * @return the ResponseEntity with status 200 (OK) and the list of leasing contracts in body
     */
    @Operation(summary = "Filter leasing contracts", description = "Returns a paginated list of leasing contracts based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved leasing contracts",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = PaginationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<LeasingContractDTO>>> filterLeasingContracts(
            @Valid @RequestBody FilterRequest<LeasingContractDTO> filterRequest) {
        
        return leasingContractService.filterLeasingContracts(filterRequest)
                .map(ResponseEntity::ok);
    }

    /**
     * POST /api/v1/leasing-contracts : Create a new leasing contract
     *
     * @param leasingContractDTO the leasing contract to create
     * @return the ResponseEntity with status 201 (Created) and with body the new leasing contract
     */
    @Operation(summary = "Create a new leasing contract", description = "Creates a new leasing contract with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Leasing contract successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LeasingContractDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid leasing contract data provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LeasingContractDTO>> createLeasingContract(
            @Valid @RequestBody LeasingContractDTO leasingContractDTO) {
        
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
    @Operation(summary = "Update an existing leasing contract", description = "Updates an existing leasing contract with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Leasing contract successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LeasingContractDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid leasing contract data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Leasing contract not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LeasingContractDTO>> updateLeasingContract(
            @Parameter(description = "ID of the leasing contract to update", required = true)
            @PathVariable Long id,
            @Valid @RequestBody LeasingContractDTO leasingContractDTO) {
        
        return leasingContractService.updateLeasingContract(id, leasingContractDTO)
                .map(ResponseEntity::ok);
    }

    /**
     * DELETE /api/v1/leasing-contracts/{id} : Delete a leasing contract
     *
     * @param id the ID of the leasing contract to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @Operation(summary = "Delete a leasing contract", description = "Deletes a leasing contract based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Leasing contract successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Leasing contract not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteLeasingContract(
            @Parameter(description = "ID of the leasing contract to delete", required = true)
            @PathVariable Long id) {
        
        return leasingContractService.deleteLeasingContract(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    /**
     * GET /api/v1/leasing-contracts/{id} : Get a leasing contract by ID
     *
     * @param id the ID of the leasing contract to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leasing contract
     */
    @Operation(summary = "Get leasing contract by ID", description = "Returns a leasing contract based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved leasing contract",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LeasingContractDTO.class))),
        @ApiResponse(responseCode = "404", description = "Leasing contract not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LeasingContractDTO>> getLeasingContractById(
            @Parameter(description = "ID of the leasing contract to retrieve", required = true)
            @PathVariable Long id) {
        
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
    @Operation(summary = "Get leasing contract by contract ID", description = "Returns a leasing contract based on its contract ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved leasing contract",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LeasingContractDTO.class))),
        @ApiResponse(responseCode = "404", description = "Leasing contract not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/contract/{contractId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LeasingContractDTO>> getLeasingContractByContractId(
            @Parameter(description = "Contract ID of the leasing contract to retrieve", required = true)
            @PathVariable Long contractId) {
        
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
    @Operation(summary = "Get leasing contracts by distributor", description = "Returns all leasing contracts associated with a specific distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved leasing contracts",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LeasingContractDTO.class))),
        @ApiResponse(responseCode = "404", description = "Distributor not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/distributor/{distributorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<LeasingContractDTO>>> getLeasingContractsByDistributorId(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId) {
        
        Flux<LeasingContractDTO> contracts = leasingContractService.getLeasingContractsByDistributorId(distributorId);
        return Mono.just(ResponseEntity.ok(contracts));
    }

    /**
     * GET /api/v1/leasing-contracts/product/{productId} : Get all leasing contracts for a product
     *
     * @param productId the ID of the product
     * @return the ResponseEntity with status 200 (OK) and with body the list of leasing contracts
     */
    @Operation(summary = "Get leasing contracts by product", description = "Returns all leasing contracts associated with a specific product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved leasing contracts",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LeasingContractDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<LeasingContractDTO>>> getLeasingContractsByProductId(
            @Parameter(description = "ID of the product", required = true)
            @PathVariable Long productId) {
        
        Flux<LeasingContractDTO> contracts = leasingContractService.getLeasingContractsByProductId(productId);
        return Mono.just(ResponseEntity.ok(contracts));
    }

    /**
     * GET /api/v1/leasing-contracts/party/{partyId} : Get all leasing contracts for a party
     *
     * @param partyId the ID of the party
     * @return the ResponseEntity with status 200 (OK) and with body the list of leasing contracts
     */
    @Operation(summary = "Get leasing contracts by party", description = "Returns all leasing contracts associated with a specific party")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved leasing contracts",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LeasingContractDTO.class))),
        @ApiResponse(responseCode = "404", description = "Party not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/party/{partyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<LeasingContractDTO>>> getLeasingContractsByPartyId(
            @Parameter(description = "ID of the party", required = true)
            @PathVariable Long partyId) {
        
        Flux<LeasingContractDTO> contracts = leasingContractService.getLeasingContractsByPartyId(partyId);
        return Mono.just(ResponseEntity.ok(contracts));
    }

    /**
     * GET /api/v1/leasing-contracts/status/{status} : Get all leasing contracts with a specific status
     *
     * @param status the status
     * @return the ResponseEntity with status 200 (OK) and with body the list of leasing contracts
     */
    @Operation(summary = "Get leasing contracts by status", description = "Returns all leasing contracts with a specific status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved leasing contracts",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LeasingContractDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid status value", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<LeasingContractDTO>>> getLeasingContractsByStatus(
            @Parameter(description = "Status value to filter leasing contracts", required = true)
            @PathVariable String status) {
        
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
    @Operation(summary = "Approve a leasing contract", description = "Approves a leasing contract and updates its status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Leasing contract successfully approved",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LeasingContractDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid approval request", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Leasing contract not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/{id}/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LeasingContractDTO>> approveLeasingContract(
            @Parameter(description = "ID of the leasing contract to approve", required = true)
            @PathVariable Long id,
            @Parameter(description = "ID of the user approving the contract", required = true)
            @RequestParam Long approvedBy) {
        
        return leasingContractService.approveLeasingContract(id, approvedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}