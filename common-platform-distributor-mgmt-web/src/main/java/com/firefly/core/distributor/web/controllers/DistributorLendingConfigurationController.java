package com.firefly.core.distributor.web.controllers;

import com.firefly.core.distributor.core.services.LendingConfigurationService;
import com.firefly.core.distributor.interfaces.dtos.LendingConfigurationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing distributor-level lending configurations.
 */
@RestController
@RequestMapping("/api/v1/distributors/{distributorId}/lending-configurations")
@Tag(name = "Distributor Lending Configuration", description = "API for managing distributor-level lending configurations")
public class DistributorLendingConfigurationController {

    @Autowired
    private LendingConfigurationService lendingConfigurationService;

    /**
     * GET /api/v1/distributors/{distributorId}/lending-configurations : Get all lending configurations for a distributor
     *
     * @param distributorId the ID of the distributor
     * @return the ResponseEntity with status 200 (OK) and with body the list of lending configurations
     */
    @Operation(summary = "Get all lending configurations for a distributor", description = "Returns all lending configurations associated with a specific distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lending configurations",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingConfigurationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Distributor not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<LendingConfigurationDTO>>> getLendingConfigurationsByDistributorId(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId) {
        
        return Mono.just(ResponseEntity.ok(lendingConfigurationService.getLendingConfigurationsByDistributorId(distributorId)));
    }
}