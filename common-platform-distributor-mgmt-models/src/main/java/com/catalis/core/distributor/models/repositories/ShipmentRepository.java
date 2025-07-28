package com.catalis.core.distributor.models.repositories;

import com.catalis.core.distributor.models.entities.Shipment;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository for managing Shipment entities.
 */
@Repository
public interface ShipmentRepository extends BaseRepository<Shipment, Long> {

    /**
     * Find all shipments by leasing contract ID.
     *
     * @param leasingContractId the leasing contract ID
     * @return a Flux of shipments
     */
    Flux<Shipment> findByLeasingContractId(Long leasingContractId);

    /**
     * Find all shipments by product ID.
     *
     * @param productId the product ID
     * @return a Flux of shipments
     */
    Flux<Shipment> findByProductId(Long productId);

    /**
     * Find a shipment by tracking number.
     *
     * @param trackingNumber the tracking number
     * @return a Mono of shipment
     */
    Mono<Shipment> findByTrackingNumber(String trackingNumber);

    /**
     * Find all shipments by status.
     *
     * @param status the status
     * @return a Flux of shipments
     */
    Flux<Shipment> findByStatus(String status);

    /**
     * Find all shipments by leasing contract ID and status.
     *
     * @param leasingContractId the leasing contract ID
     * @param status the status
     * @return a Flux of shipments
     */
    Flux<Shipment> findByLeasingContractIdAndStatus(Long leasingContractId, String status);

    /**
     * Find all shipments by product ID and status.
     *
     * @param productId the product ID
     * @param status the status
     * @return a Flux of shipments
     */
    Flux<Shipment> findByProductIdAndStatus(Long productId, String status);

    /**
     * Find all shipments by carrier.
     *
     * @param carrier the carrier
     * @return a Flux of shipments
     */
    Flux<Shipment> findByCarrier(String carrier);

    /**
     * Find all shipments shipped after a specific date.
     *
     * @param shippingDate the shipping date
     * @return a Flux of shipments
     */
    @Query("SELECT * FROM shipment WHERE shipping_date >= :shippingDate")
    Flux<Shipment> findByShippingDateAfter(String shippingDate);

    /**
     * Find all shipments with estimated delivery date after a specific date.
     *
     * @param estimatedDeliveryDate the estimated delivery date
     * @return a Flux of shipments
     */
    @Query("SELECT * FROM shipment WHERE estimated_delivery_date >= :estimatedDeliveryDate")
    Flux<Shipment> findByEstimatedDeliveryDateAfter(String estimatedDeliveryDate);

    /**
     * Find all shipments with actual delivery date after a specific date.
     *
     * @param actualDeliveryDate the actual delivery date
     * @return a Flux of shipments
     */
    @Query("SELECT * FROM shipment WHERE actual_delivery_date >= :actualDeliveryDate")
    Flux<Shipment> findByActualDeliveryDateAfter(String actualDeliveryDate);
}