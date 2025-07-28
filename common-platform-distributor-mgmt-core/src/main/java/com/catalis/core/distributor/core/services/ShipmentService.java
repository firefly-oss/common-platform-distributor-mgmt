package com.catalis.core.distributor.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.distributor.interfaces.dtos.LeasingContractDTO;
import com.catalis.core.distributor.interfaces.dtos.ShipmentDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service for managing shipments.
 */
public interface ShipmentService {

    /**
     * Create a new shipment.
     *
     * @param shipmentDTO the shipment to create
     * @return the created shipment
     */
    Mono<ShipmentDTO> createShipment(ShipmentDTO shipmentDTO);

    /**
     * Create a shipment for an approved leasing contract.
     *
     * @param leasingContractDTO the approved leasing contract
     * @return a Mono completing when the operation is done
     */
    Mono<ShipmentDTO> createShipmentForApprovedContract(LeasingContractDTO leasingContractDTO);

    /**
     * Update an existing shipment.
     *
     * @param id the ID of the shipment to update
     * @param shipmentDTO the shipment data to update
     * @return the updated shipment
     */
    Mono<ShipmentDTO> updateShipment(Long id, ShipmentDTO shipmentDTO);

    /**
     * Delete a shipment.
     *
     * @param id the ID of the shipment to delete
     * @return a Mono completing when the operation is done
     */
    Mono<Void> deleteShipment(Long id);

    /**
     * Get a shipment by ID.
     *
     * @param id the ID of the shipment to retrieve
     * @return the shipment
     */
    Mono<ShipmentDTO> getShipmentById(Long id);

    /**
     * Get a shipment by tracking number.
     *
     * @param trackingNumber the tracking number of the shipment to retrieve
     * @return the shipment
     */
    Mono<ShipmentDTO> getShipmentByTrackingNumber(String trackingNumber);

    /**
     * Get all shipments for a leasing contract.
     *
     * @param leasingContractId the ID of the leasing contract
     * @return a Flux of shipments
     */
    Flux<ShipmentDTO> getShipmentsByLeasingContractId(Long leasingContractId);

    /**
     * Get all shipments for a product.
     *
     * @param productId the ID of the product
     * @return a Flux of shipments
     */
    Flux<ShipmentDTO> getShipmentsByProductId(Long productId);

    /**
     * Get all shipments with a specific status.
     *
     * @param status the status
     * @return a Flux of shipments
     */
    Flux<ShipmentDTO> getShipmentsByStatus(String status);

    /**
     * Update the status of a shipment.
     *
     * @param id the ID of the shipment to update
     * @param status the new status
     * @param updatedBy the ID of the user updating the status
     * @return the updated shipment
     */
    Mono<ShipmentDTO> updateShipmentStatus(Long id, String status, Long updatedBy);

    /**
     * Filter shipments based on criteria.
     *
     * @param filterRequest the filter request containing criteria and pagination
     * @return a PaginationResponse with the filtered shipments
     */
    Mono<PaginationResponse<ShipmentDTO>> filterShipments(FilterRequest<ShipmentDTO> filterRequest);
}