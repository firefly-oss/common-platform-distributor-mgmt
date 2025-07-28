package com.catalis.core.distributor.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.distributor.core.services.LeasingContractService;
import com.catalis.core.distributor.core.services.LendingConfigurationService;
import com.catalis.core.distributor.core.services.LendingTypeService;
import com.catalis.core.distributor.interfaces.dtos.LeasingContractDTO;
import com.catalis.core.distributor.interfaces.dtos.LendingConfigurationDTO;
import com.catalis.core.distributor.interfaces.dtos.LendingTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing lending configurations.
 */
@RestController
@RequestMapping("/api/v1/distributors/{distributorId}/products/{productId}/lending-configurations")
public class LendingConfigurationController {

    @Autowired
    private LendingConfigurationService lendingConfigurationService;
    
    @Autowired
    private LendingTypeService lendingTypeService;
    
    @Autowired
    private LeasingContractService leasingContractService;

    /**
     * POST /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations/filter : Filter lending configurations
     *
     * @param distributorId the ID of the distributor
     * @param productId the ID of the product
     * @param filterRequest the filter request containing criteria and pagination
     * @return the ResponseEntity with status 200 (OK) and the list of lending configurations in body
     */
    @PostMapping("/filter")
    public Mono<ResponseEntity<PaginationResponse<LendingConfigurationDTO>>> filterLendingConfigurations(
            @PathVariable Long distributorId,
            @PathVariable Long productId,
            @RequestBody FilterRequest<LendingConfigurationDTO> filterRequest) {
        
        // Ensure we're only filtering lending configurations for the specified product
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new LendingConfigurationDTO());
        }
        filterRequest.getFilters().setProductId(productId);
        
        return lendingConfigurationService.filterLendingConfigurations(filterRequest)
                .map(ResponseEntity::ok);
    }

    /**
     * POST /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations : Create a new lending configuration
     *
     * @param distributorId the ID of the distributor
     * @param productId the ID of the product
     * @param lendingConfigurationDTO the lending configuration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lending configuration
     */
    @PostMapping
    public Mono<ResponseEntity<LendingConfigurationDTO>> createLendingConfiguration(
            @PathVariable Long distributorId,
            @PathVariable Long productId,
            @RequestBody LendingConfigurationDTO lendingConfigurationDTO) {
        
        lendingConfigurationDTO.setProductId(productId);
        
        return lendingConfigurationService.createLendingConfiguration(lendingConfigurationDTO)
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    /**
     * PUT /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations/{configId} : Update an existing lending configuration
     *
     * @param distributorId the ID of the distributor
     * @param productId the ID of the product
     * @param configId the ID of the lending configuration to update
     * @param lendingConfigurationDTO the lending configuration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lending configuration
     */
    @PutMapping("/{configId}")
    public Mono<ResponseEntity<LendingConfigurationDTO>> updateLendingConfiguration(
            @PathVariable Long distributorId,
            @PathVariable Long productId,
            @PathVariable Long configId,
            @RequestBody LendingConfigurationDTO lendingConfigurationDTO) {
        
        lendingConfigurationDTO.setProductId(productId);
        
        return lendingConfigurationService.updateLendingConfiguration(configId, lendingConfigurationDTO)
                .map(ResponseEntity::ok);
    }

    /**
     * DELETE /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations/{configId} : Delete a lending configuration
     *
     * @param distributorId the ID of the distributor
     * @param productId the ID of the product
     * @param configId the ID of the lending configuration to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{configId}")
    public Mono<ResponseEntity<Void>> deleteLendingConfiguration(
            @PathVariable Long distributorId,
            @PathVariable Long productId,
            @PathVariable Long configId) {
        
        return lendingConfigurationService.deleteLendingConfiguration(configId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    /**
     * GET /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations/{configId} : Get a lending configuration by ID
     *
     * @param distributorId the ID of the distributor
     * @param productId the ID of the product
     * @param configId the ID of the lending configuration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lending configuration
     */
    @GetMapping("/{configId}")
    public Mono<ResponseEntity<LendingConfigurationDTO>> getLendingConfigurationById(
            @PathVariable Long distributorId,
            @PathVariable Long productId,
            @PathVariable Long configId) {
        
        return lendingConfigurationService.getLendingConfigurationById(configId)
                .map(ResponseEntity::ok);
    }

    /**
     * GET /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations : Get all lending configurations for a product
     *
     * @param distributorId the ID of the distributor
     * @param productId the ID of the product
     * @return the ResponseEntity with status 200 (OK) and with body the list of lending configurations
     */
    @GetMapping
    public Mono<ResponseEntity<Flux<LendingConfigurationDTO>>> getLendingConfigurationsByProductId(
            @PathVariable Long distributorId,
            @PathVariable Long productId) {
        
        return Mono.just(ResponseEntity.ok(lendingConfigurationService.getLendingConfigurationsByProductId(productId)));
    }

    /**
     * GET /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations/active : Get all active lending configurations for a product
     *
     * @param distributorId the ID of the distributor
     * @param productId the ID of the product
     * @return the ResponseEntity with status 200 (OK) and with body the list of active lending configurations
     */
    @GetMapping("/active")
    public Mono<ResponseEntity<Flux<LendingConfigurationDTO>>> getActiveLendingConfigurationsByProductId(
            @PathVariable Long distributorId,
            @PathVariable Long productId) {
        
        return Mono.just(ResponseEntity.ok(lendingConfigurationService.getActiveLendingConfigurationsByProductId(productId)));
    }

    /**
     * GET /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations/type/{lendingTypeId} : Get all lending configurations for a product by lending type
     *
     * @param distributorId the ID of the distributor
     * @param productId the ID of the product
     * @param lendingTypeId the ID of the lending type
     * @return the ResponseEntity with status 200 (OK) and with body the list of lending configurations
     */
    @GetMapping("/type/{lendingTypeId}")
    public Mono<ResponseEntity<Flux<LendingConfigurationDTO>>> getLendingConfigurationsByProductIdAndLendingType(
            @PathVariable Long distributorId,
            @PathVariable Long productId,
            @PathVariable Long lendingTypeId) {
        
        return lendingTypeService.getLendingTypeById(lendingTypeId)
                .map(lendingType -> ResponseEntity.ok(lendingConfigurationService.getLendingConfigurationsByProductIdAndLendingType(productId, lendingType)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations/default : Get the default lending configuration for a product
     *
     * @param distributorId the ID of the distributor
     * @param productId the ID of the product
     * @return the ResponseEntity with status 200 (OK) and with body the default lending configuration
     */
    @GetMapping("/default")
    public Mono<ResponseEntity<LendingConfigurationDTO>> getDefaultLendingConfigurationByProductId(
            @PathVariable Long distributorId,
            @PathVariable Long productId) {
        
        return lendingConfigurationService.getDefaultLendingConfigurationByProductId(productId)
                .map(ResponseEntity::ok);
    }
    
    /**
     * POST /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations/{configId}/create-contract : Create a leasing contract from a lending configuration
     *
     * @param distributorId the ID of the distributor
     * @param productId the ID of the product
     * @param configId the ID of the lending configuration
     * @param leasingContractDTO the leasing contract details
     * @return the ResponseEntity with status 201 (Created) and with body the new leasing contract
     */
    @PostMapping("/{configId}/create-contract")
    public Mono<ResponseEntity<LeasingContractDTO>> createLeasingContractFromConfiguration(
            @PathVariable Long distributorId,
            @PathVariable Long productId,
            @PathVariable Long configId,
            @RequestBody LeasingContractDTO leasingContractDTO) {
        
        // Set the required fields from the path variables
        leasingContractDTO.setDistributorId(distributorId);
        leasingContractDTO.setProductId(productId);
        leasingContractDTO.setLendingConfigurationId(configId);
        
        // Set initial status
        leasingContractDTO.setStatus("PENDING");
        
        return leasingContractService.createLeasingContract(leasingContractDTO)
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

}