package com.firefly.core.distributor.web.controllers;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.distributor.core.services.DistributorTermsAndConditionsService;
import com.firefly.core.distributor.core.services.TermsAndConditionsGenerationService;
import com.firefly.core.distributor.interfaces.dtos.DistributorTermsAndConditionsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * REST controller for managing distributor terms and conditions.
 */
@RestController
@RequestMapping("/api/v1/distributors/{distributorId}/terms-and-conditions")
@Tag(name = "Distributor Terms and Conditions", description = "API for managing distributor terms and conditions")
@RequiredArgsConstructor
public class DistributorTermsAndConditionsController {

    private final DistributorTermsAndConditionsService distributorTermsAndConditionsService;
    private final TermsAndConditionsGenerationService generationService;

    @Operation(summary = "Filter distributor terms and conditions", description = "Returns a paginated list of distributor terms and conditions based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved distributor terms and conditions",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = PaginationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<PaginationResponse<DistributorTermsAndConditionsDTO>>> filterDistributorTermsAndConditions(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Valid @RequestBody FilterRequest<DistributorTermsAndConditionsDTO> filterRequest) {
        return ResponseEntity.ok(distributorTermsAndConditionsService.filterDistributorTermsAndConditions(filterRequest));
    }

    @Operation(summary = "Create new distributor terms and conditions", description = "Creates new terms and conditions for a distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Terms and conditions successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorTermsAndConditionsDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid terms and conditions data provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorTermsAndConditionsDTO>> createDistributorTermsAndConditions(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Valid @RequestBody DistributorTermsAndConditionsDTO distributorTermsAndConditionsDTO) {
        
        distributorTermsAndConditionsDTO.setDistributorId(distributorId);
        
        return distributorTermsAndConditionsService.createDistributorTermsAndConditions(distributorTermsAndConditionsDTO)
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    @Operation(summary = "Generate terms and conditions from template", description = "Generates new terms and conditions from a template with provided variables")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Terms and conditions successfully generated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorTermsAndConditionsDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid template or variables provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/generate/{templateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorTermsAndConditionsDTO>> generateFromTemplate(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the template", required = true)
            @PathVariable Long templateId,
            @RequestBody Map<String, Object> variables) {
        
        return generationService.generateFromTemplate(templateId, distributorId, variables)
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    @Operation(summary = "Get distributor terms and conditions by ID", description = "Returns distributor terms and conditions based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved terms and conditions",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorTermsAndConditionsDTO.class))),
        @ApiResponse(responseCode = "404", description = "Terms and conditions not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{termsId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorTermsAndConditionsDTO>> getDistributorTermsAndConditionsById(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the terms and conditions", required = true)
            @PathVariable Long termsId) {
        return distributorTermsAndConditionsService.getDistributorTermsAndConditionsById(termsId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update distributor terms and conditions", description = "Updates existing distributor terms and conditions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Terms and conditions successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorTermsAndConditionsDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid terms and conditions data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Terms and conditions not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{termsId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorTermsAndConditionsDTO>> updateDistributorTermsAndConditions(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the terms and conditions to update", required = true)
            @PathVariable Long termsId,
            @Valid @RequestBody DistributorTermsAndConditionsDTO distributorTermsAndConditionsDTO) {
        
        distributorTermsAndConditionsDTO.setDistributorId(distributorId);
        
        return distributorTermsAndConditionsService.updateDistributorTermsAndConditions(termsId, distributorTermsAndConditionsDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete distributor terms and conditions", description = "Deletes distributor terms and conditions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Terms and conditions successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Terms and conditions not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{termsId}")
    public Mono<ResponseEntity<Void>> deleteDistributorTermsAndConditions(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the terms and conditions to delete", required = true)
            @PathVariable Long termsId) {
        return distributorTermsAndConditionsService.deleteDistributorTermsAndConditions(termsId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    @Operation(summary = "Get all terms and conditions for distributor", description = "Returns all terms and conditions for a distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved terms and conditions",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorTermsAndConditionsDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<DistributorTermsAndConditionsDTO>> getTermsAndConditionsByDistributorId(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId) {
        return ResponseEntity.ok(distributorTermsAndConditionsService.getTermsAndConditionsByDistributorId(distributorId));
    }

    @Operation(summary = "Get active terms and conditions for distributor", description = "Returns all active terms and conditions for a distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved active terms and conditions",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorTermsAndConditionsDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<DistributorTermsAndConditionsDTO>> getActiveTermsAndConditionsByDistributorId(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId) {
        return ResponseEntity.ok(distributorTermsAndConditionsService.getActiveTermsAndConditionsByDistributorId(distributorId));
    }

    @Operation(summary = "Get terms and conditions by status", description = "Returns terms and conditions with a specific status for a distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved terms and conditions",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorTermsAndConditionsDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<DistributorTermsAndConditionsDTO>> getTermsAndConditionsByDistributorIdAndStatus(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "Status of the terms and conditions", required = true)
            @PathVariable String status) {
        return ResponseEntity.ok(distributorTermsAndConditionsService.getTermsAndConditionsByDistributorIdAndStatus(distributorId, status));
    }

    @Operation(summary = "Update terms and conditions status", description = "Updates the status of distributor terms and conditions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorTermsAndConditionsDTO.class))),
        @ApiResponse(responseCode = "404", description = "Terms and conditions not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PatchMapping(value = "/{termsId}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorTermsAndConditionsDTO>> updateStatus(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the terms and conditions", required = true)
            @PathVariable Long termsId,
            @Parameter(description = "New status", required = true)
            @RequestParam String status,
            @Parameter(description = "ID of the user performing the update")
            @RequestParam(required = false) Long updatedBy) {
        return distributorTermsAndConditionsService.updateStatus(termsId, status, updatedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Sign terms and conditions", description = "Signs the distributor terms and conditions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Terms and conditions successfully signed",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorTermsAndConditionsDTO.class))),
        @ApiResponse(responseCode = "404", description = "Terms and conditions not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PatchMapping(value = "/{termsId}/sign", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorTermsAndConditionsDTO>> signTermsAndConditions(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the terms and conditions to sign", required = true)
            @PathVariable Long termsId,
            @Parameter(description = "ID of the user signing", required = true)
            @RequestParam Long signedBy) {
        return distributorTermsAndConditionsService.signTermsAndConditions(termsId, signedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Check if distributor has active signed terms", description = "Checks if a distributor has active signed terms and conditions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully checked active signed terms",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = Boolean.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/has-active-signed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Boolean>> hasActiveSignedTerms(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId) {
        return ResponseEntity.ok(distributorTermsAndConditionsService.hasActiveSignedTerms(distributorId));
    }

    @Operation(summary = "Get latest terms and conditions", description = "Returns the latest version of terms and conditions for a distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved latest terms and conditions",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorTermsAndConditionsDTO.class))),
        @ApiResponse(responseCode = "404", description = "No terms and conditions found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorTermsAndConditionsDTO>> getLatestTermsAndConditions(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId) {
        return distributorTermsAndConditionsService.getLatestTermsAndConditions(distributorId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get expiring terms and conditions", description = "Returns terms and conditions expiring before a certain date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved expiring terms and conditions",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorTermsAndConditionsDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/expiring", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<DistributorTermsAndConditionsDTO>> getExpiringTermsAndConditions(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "Expiration date threshold", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime expirationDate) {
        return ResponseEntity.ok(distributorTermsAndConditionsService.getExpiringTermsAndConditions(expirationDate)
                .filter(terms -> terms.getDistributorId().equals(distributorId)));
    }

    @Operation(summary = "Activate terms and conditions", description = "Activates distributor terms and conditions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Terms and conditions successfully activated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorTermsAndConditionsDTO.class))),
        @ApiResponse(responseCode = "404", description = "Terms and conditions not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PatchMapping(value = "/{termsId}/activate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorTermsAndConditionsDTO>> activateTermsAndConditions(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the terms and conditions to activate", required = true)
            @PathVariable Long termsId,
            @Parameter(description = "ID of the user performing the update")
            @RequestParam(required = false) Long updatedBy) {
        return distributorTermsAndConditionsService.activateTermsAndConditions(termsId, updatedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deactivate terms and conditions", description = "Deactivates distributor terms and conditions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Terms and conditions successfully deactivated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorTermsAndConditionsDTO.class))),
        @ApiResponse(responseCode = "404", description = "Terms and conditions not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PatchMapping(value = "/{termsId}/deactivate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorTermsAndConditionsDTO>> deactivateTermsAndConditions(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the terms and conditions to deactivate", required = true)
            @PathVariable Long termsId,
            @Parameter(description = "ID of the user performing the update")
            @RequestParam(required = false) Long updatedBy) {
        return distributorTermsAndConditionsService.deactivateTermsAndConditions(termsId, updatedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
