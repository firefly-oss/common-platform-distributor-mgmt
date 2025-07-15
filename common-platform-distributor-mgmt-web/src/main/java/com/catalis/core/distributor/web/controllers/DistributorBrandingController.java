package com.catalis.core.distributor.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.distributor.core.services.DistributorBrandingService;
import com.catalis.core.distributor.interfaces.dtos.DistributorBrandingDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/distributors/{distributorId}/brandings")
@Tag(name = "Distributor Branding", description = "API for managing distributor branding")
@RequiredArgsConstructor
public class DistributorBrandingController {

    private final DistributorBrandingService distributorBrandingService;

    @Operation(summary = "Filter distributor brandings", description = "Returns a paginated list of distributor brandings based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved distributor brandings",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = PaginationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PaginationResponse<DistributorBrandingDTO>> filterDistributorBrandings(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Valid @RequestBody FilterRequest<DistributorBrandingDTO> filterRequest) {
        return distributorBrandingService.filterDistributorBranding(filterRequest);
    }

    @Operation(summary = "Create a new distributor branding", description = "Creates a new distributor branding with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Distributor branding successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorBrandingDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid distributor branding data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Distributor not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DistributorBrandingDTO> createDistributorBranding(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Valid @RequestBody DistributorBrandingDTO distributorBrandingDTO) {
        // Ensure the distributorId in the path is used
        distributorBrandingDTO.setDistributorId(distributorId);
        return distributorBrandingService.createDistributorBranding(distributorBrandingDTO);
    }

    @Operation(summary = "Get distributor branding by ID", description = "Returns a distributor branding based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved distributor branding",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorBrandingDTO.class))),
        @ApiResponse(responseCode = "404", description = "Distributor branding not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{brandingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DistributorBrandingDTO> getDistributorBrandingById(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the distributor branding to retrieve", required = true)
            @PathVariable Long brandingId) {
        return distributorBrandingService.getDistributorBrandingById(brandingId)
                .filter(branding -> branding.getDistributorId().equals(distributorId));
    }

    @Operation(summary = "Update distributor branding", description = "Updates an existing distributor branding with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Distributor branding successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorBrandingDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid distributor branding data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Distributor branding not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{brandingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DistributorBrandingDTO> updateDistributorBranding(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the distributor branding to update", required = true)
            @PathVariable Long brandingId,
            @Valid @RequestBody DistributorBrandingDTO distributorBrandingDTO) {
        // Ensure the distributorId and brandingId in the path are used
        distributorBrandingDTO.setDistributorId(distributorId);
        return distributorBrandingService.updateDistributorBranding(brandingId, distributorBrandingDTO);
    }

    @Operation(summary = "Delete distributor branding", description = "Deletes a distributor branding based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Distributor branding successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Distributor branding not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{brandingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteDistributorBranding(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the distributor branding to delete", required = true)
            @PathVariable Long brandingId) {
        return distributorBrandingService.getDistributorBrandingById(brandingId)
                .filter(branding -> branding.getDistributorId().equals(distributorId))
                .flatMap(branding -> distributorBrandingService.deleteDistributorBranding(brandingId));
    }
}
