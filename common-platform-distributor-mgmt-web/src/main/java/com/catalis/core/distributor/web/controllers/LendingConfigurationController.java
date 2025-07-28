package com.catalis.core.distributor.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.distributor.core.services.LeasingContractService;
import com.catalis.core.distributor.core.services.LendingConfigurationService;
import com.catalis.core.distributor.core.services.LendingTypeService;
import com.catalis.core.distributor.interfaces.dtos.LeasingContractDTO;
import com.catalis.core.distributor.interfaces.dtos.LendingConfigurationDTO;
import com.catalis.core.distributor.interfaces.dtos.LendingTypeDTO;
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
 * REST controller for managing lending configurations.
 */
@RestController
@RequestMapping("/api/v1/distributors/{distributorId}/products/{productId}/lending-configurations")
@Tag(name = "Lending Configuration", description = "API for managing product lending configurations")
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
    @Operation(summary = "Filter lending configurations", description = "Returns a paginated list of lending configurations based on filter criteria for a specific product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lending configurations",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = PaginationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<LendingConfigurationDTO>>> filterLendingConfigurations(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the product", required = true)
            @PathVariable Long productId,
            @Valid @RequestBody FilterRequest<LendingConfigurationDTO> filterRequest) {
        
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
    @Operation(summary = "Create a new lending configuration", description = "Creates a new lending configuration for a specific product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Lending configuration successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingConfigurationDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid lending configuration data provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LendingConfigurationDTO>> createLendingConfiguration(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the product", required = true)
            @PathVariable Long productId,
            @Valid @RequestBody LendingConfigurationDTO lendingConfigurationDTO) {
        
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
    @Operation(summary = "Update an existing lending configuration", description = "Updates an existing lending configuration for a specific product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lending configuration successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingConfigurationDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid lending configuration data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Lending configuration not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{configId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LendingConfigurationDTO>> updateLendingConfiguration(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the product", required = true)
            @PathVariable Long productId,
            @Parameter(description = "ID of the lending configuration to update", required = true)
            @PathVariable Long configId,
            @Valid @RequestBody LendingConfigurationDTO lendingConfigurationDTO) {
        
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
    @Operation(summary = "Delete a lending configuration", description = "Deletes a lending configuration based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Lending configuration successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Lending configuration not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{configId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteLendingConfiguration(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the product", required = true)
            @PathVariable Long productId,
            @Parameter(description = "ID of the lending configuration to delete", required = true)
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
    @Operation(summary = "Get lending configuration by ID", description = "Returns a lending configuration based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lending configuration",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingConfigurationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Lending configuration not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{configId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LendingConfigurationDTO>> getLendingConfigurationById(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the product", required = true)
            @PathVariable Long productId,
            @Parameter(description = "ID of the lending configuration to retrieve", required = true)
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
    @Operation(summary = "Get all lending configurations for a product", description = "Returns all lending configurations associated with a specific product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lending configurations",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingConfigurationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<LendingConfigurationDTO>>> getLendingConfigurationsByProductId(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the product", required = true)
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
    @Operation(summary = "Get all active lending configurations for a product", description = "Returns all active lending configurations associated with a specific product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved active lending configurations",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingConfigurationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<LendingConfigurationDTO>>> getActiveLendingConfigurationsByProductId(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the product", required = true)
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
    @Operation(summary = "Get lending configurations by product and lending type", description = "Returns all lending configurations for a specific product filtered by lending type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lending configurations",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingConfigurationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product or lending type not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/type/{lendingTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<LendingConfigurationDTO>>> getLendingConfigurationsByProductIdAndLendingType(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the product", required = true)
            @PathVariable Long productId,
            @Parameter(description = "ID of the lending type", required = true)
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
    @Operation(summary = "Get default lending configuration for a product", description = "Returns the default lending configuration for a specific product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved default lending configuration",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingConfigurationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product or default configuration not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/default", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LendingConfigurationDTO>> getDefaultLendingConfigurationByProductId(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the product", required = true)
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
    @Operation(summary = "Create a leasing contract from a lending configuration", description = "Creates a new leasing contract based on a specific lending configuration")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Leasing contract successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LeasingContractDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid leasing contract data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Lending configuration not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/{configId}/create-contract", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LeasingContractDTO>> createLeasingContractFromConfiguration(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the product", required = true)
            @PathVariable Long productId,
            @Parameter(description = "ID of the lending configuration", required = true)
            @PathVariable Long configId,
            @Valid @RequestBody LeasingContractDTO leasingContractDTO) {
        
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