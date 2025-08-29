package com.firefly.core.distributor.web.controllers;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.distributor.core.services.DistributorAuditLogService;
import com.firefly.core.distributor.interfaces.dtos.DistributorAuditLogDTO;
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

@RestController
@RequestMapping("/api/v1/distributors/{distributorId}/audit-logs")
@Tag(name = "Distributor Audit Log", description = "API for managing distributor audit logs")
@RequiredArgsConstructor
public class DistributorAuditLogController {

    private final DistributorAuditLogService distributorAuditLogService;

    @Operation(summary = "Filter distributor audit logs", description = "Returns a paginated list of distributor audit logs based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved distributor audit logs",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = PaginationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<PaginationResponse<DistributorAuditLogDTO>>> filterDistributorAuditLogs(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Valid @RequestBody FilterRequest<DistributorAuditLogDTO> filterRequest) {
        return ResponseEntity.ok(distributorAuditLogService.filterDistributorAuditLogs(filterRequest));
    }

    @Operation(summary = "Create a new distributor audit log", description = "Creates a new distributor audit log with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Distributor audit log successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorAuditLogDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid distributor audit log data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Distributor not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<DistributorAuditLogDTO>> createDistributorAuditLog(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Valid @RequestBody DistributorAuditLogDTO distributorAuditLogDTO) {
        // Ensure the distributorId in the path is used
        distributorAuditLogDTO.setDistributorId(distributorId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(distributorAuditLogService.createDistributorAuditLog(distributorAuditLogDTO));
    }

    @Operation(summary = "Get distributor audit log by ID", description = "Returns a distributor audit log based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved distributor audit log",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorAuditLogDTO.class))),
        @ApiResponse(responseCode = "404", description = "Distributor audit log not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{auditLogId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<DistributorAuditLogDTO>> getDistributorAuditLogById(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the distributor audit log to retrieve", required = true)
            @PathVariable Long auditLogId) {
        return ResponseEntity.ok(distributorAuditLogService.getDistributorAuditLogById(auditLogId)
                .filter(auditLog -> auditLog.getDistributorId().equals(distributorId)));
    }

    @Operation(summary = "Update distributor audit log", description = "Updates an existing distributor audit log with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Distributor audit log successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorAuditLogDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid distributor audit log data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Distributor audit log not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{auditLogId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<DistributorAuditLogDTO>> updateDistributorAuditLog(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the distributor audit log to update", required = true)
            @PathVariable Long auditLogId,
            @Valid @RequestBody DistributorAuditLogDTO distributorAuditLogDTO) {
        // Ensure the distributorId in the path is used
        distributorAuditLogDTO.setDistributorId(distributorId);
        return ResponseEntity.ok(distributorAuditLogService.updateDistributorAuditLog(auditLogId, distributorAuditLogDTO));
    }

    @Operation(summary = "Delete distributor audit log", description = "Deletes a distributor audit log based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Distributor audit log successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Distributor audit log not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{auditLogId}")
    public Mono<ResponseEntity<Void>> deleteDistributorAuditLog(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable Long distributorId,
            @Parameter(description = "ID of the distributor audit log to delete", required = true)
            @PathVariable Long auditLogId) {
        return distributorAuditLogService.getDistributorAuditLogById(auditLogId)
                .filter(auditLog -> auditLog.getDistributorId().equals(distributorId))
                .flatMap(auditLog -> distributorAuditLogService.deleteDistributorAuditLog(auditLogId))
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}