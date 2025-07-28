package com.catalis.core.distributor.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.distributor.core.services.ShipmentService;
import com.catalis.core.distributor.interfaces.dtos.ShipmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing shipments.
 */
@RestController
@RequestMapping("/api/v1/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    /**
     * POST /api/v1/shipments/filter : Filter shipments
     *
     * @param filterRequest the filter request containing criteria and pagination
     * @return the ResponseEntity with status 200 (OK) and the list of shipments in body
     */
    @PostMapping("/filter")
    public Mono<ResponseEntity<PaginationResponse<ShipmentDTO>>> filterShipments(
            @RequestBody FilterRequest<ShipmentDTO> filterRequest) {
        
        return shipmentService.filterShipments(filterRequest)
                .map(ResponseEntity::ok);
    }

    /**
     * POST /api/v1/shipments : Create a new shipment
     *
     * @param shipmentDTO the shipment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipment
     */
    @PostMapping
    public Mono<ResponseEntity<ShipmentDTO>> createShipment(
            @RequestBody ShipmentDTO shipmentDTO) {
        
        return shipmentService.createShipment(shipmentDTO)
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    /**
     * PUT /api/v1/shipments/{id} : Update an existing shipment
     *
     * @param id the ID of the shipment to update
     * @param shipmentDTO the shipment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipment
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ShipmentDTO>> updateShipment(
            @PathVariable Long id,
            @RequestBody ShipmentDTO shipmentDTO) {
        
        return shipmentService.updateShipment(id, shipmentDTO)
                .map(ResponseEntity::ok);
    }

    /**
     * DELETE /api/v1/shipments/{id} : Delete a shipment
     *
     * @param id the ID of the shipment to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteShipment(@PathVariable Long id) {
        
        return shipmentService.deleteShipment(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    /**
     * GET /api/v1/shipments/{id} : Get a shipment by ID
     *
     * @param id the ID of the shipment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipment
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ShipmentDTO>> getShipmentById(@PathVariable Long id) {
        
        return shipmentService.getShipmentById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/v1/shipments/tracking/{trackingNumber} : Get a shipment by tracking number
     *
     * @param trackingNumber the tracking number of the shipment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipment
     */
    @GetMapping("/tracking/{trackingNumber}")
    public Mono<ResponseEntity<ShipmentDTO>> getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
        
        return shipmentService.getShipmentByTrackingNumber(trackingNumber)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/v1/shipments/leasing-contract/{leasingContractId} : Get all shipments for a leasing contract
     *
     * @param leasingContractId the ID of the leasing contract
     * @return the ResponseEntity with status 200 (OK) and with body the list of shipments
     */
    @GetMapping("/leasing-contract/{leasingContractId}")
    public Mono<ResponseEntity<Flux<ShipmentDTO>>> getShipmentsByLeasingContractId(@PathVariable Long leasingContractId) {
        
        Flux<ShipmentDTO> shipments = shipmentService.getShipmentsByLeasingContractId(leasingContractId);
        return Mono.just(ResponseEntity.ok(shipments));
    }

    /**
     * GET /api/v1/shipments/product/{productId} : Get all shipments for a product
     *
     * @param productId the ID of the product
     * @return the ResponseEntity with status 200 (OK) and with body the list of shipments
     */
    @GetMapping("/product/{productId}")
    public Mono<ResponseEntity<Flux<ShipmentDTO>>> getShipmentsByProductId(@PathVariable Long productId) {
        
        Flux<ShipmentDTO> shipments = shipmentService.getShipmentsByProductId(productId);
        return Mono.just(ResponseEntity.ok(shipments));
    }

    /**
     * GET /api/v1/shipments/status/{status} : Get all shipments with a specific status
     *
     * @param status the status
     * @return the ResponseEntity with status 200 (OK) and with body the list of shipments
     */
    @GetMapping("/status/{status}")
    public Mono<ResponseEntity<Flux<ShipmentDTO>>> getShipmentsByStatus(@PathVariable String status) {
        
        Flux<ShipmentDTO> shipments = shipmentService.getShipmentsByStatus(status);
        return Mono.just(ResponseEntity.ok(shipments));
    }

    /**
     * PUT /api/v1/shipments/{id}/status : Update the status of a shipment
     *
     * @param id the ID of the shipment to update
     * @param status the new status
     * @param updatedBy the ID of the user updating the status
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipment
     */
    @PutMapping("/{id}/status")
    public Mono<ResponseEntity<ShipmentDTO>> updateShipmentStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam Long updatedBy) {
        
        return shipmentService.updateShipmentStatus(id, status, updatedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}