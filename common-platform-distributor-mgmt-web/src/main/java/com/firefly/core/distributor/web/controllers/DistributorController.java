package com.firefly.core.distributor.web.controllers;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.distributor.core.services.DistributorService;
import com.firefly.core.distributor.interfaces.dtos.DistributorDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/distributors")
@Tag(name = "Distributor", description = "API for managing distributors")
@RequiredArgsConstructor
public class DistributorController {

    private final DistributorService distributorService;

    @Operation(summary = "Filter distributors", description = "Returns a paginated list of distributors based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved distributors",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = PaginationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<PaginationResponse<DistributorDTO>>> filterDistributors(
            @Valid @RequestBody FilterRequest<DistributorDTO> filterRequest) {
        return ResponseEntity.ok(distributorService.filterDistributors(filterRequest));
    }

    @Operation(summary = "Create a new distributor", description = "Creates a new distributor with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Distributor successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid distributor data provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<DistributorDTO>> createDistributor(
            @Valid @RequestBody DistributorDTO distributorDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(distributorService.createDistributor(distributorDTO));
    }

    @Operation(summary = "Get distributor by ID", description = "Returns a distributor based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved distributor",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorDTO.class))),
        @ApiResponse(responseCode = "404", description = "Distributor not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{distributorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<DistributorDTO>> getDistributorById(
            @Parameter(description = "ID of the distributor to retrieve", required = true)
            @PathVariable UUID distributorId) {
        return ResponseEntity.ok(distributorService.getDistributorById(distributorId));
    }

    @Operation(summary = "Update distributor", description = "Updates an existing distributor with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Distributor successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid distributor data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Distributor not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{distributorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<DistributorDTO>> updateDistributor(
            @Parameter(description = "ID of the distributor to update", required = true)
            @PathVariable UUID distributorId,
            @Valid @RequestBody DistributorDTO distributorDTO) {
        return ResponseEntity.ok(distributorService.updateDistributor(distributorId, distributorDTO));
    }

    @Operation(summary = "Delete distributor", description = "Deletes a distributor based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Distributor successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Distributor not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{distributorId}")
    public Mono<ResponseEntity<Void>> deleteDistributor(
            @Parameter(description = "ID of the distributor to delete", required = true)
            @PathVariable UUID distributorId) {
        return distributorService.deleteDistributor(distributorId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}