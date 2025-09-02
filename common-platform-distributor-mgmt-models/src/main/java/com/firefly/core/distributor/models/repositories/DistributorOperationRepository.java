package com.firefly.core.distributor.models.repositories;

import com.firefly.core.distributor.models.entities.DistributorOperation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;
import java.util.UUID;

/**
 * Repository interface for managing {@link DistributorOperation} entities.
 * Extends {@link BaseRepository} to inherit common CRUD operations.
 */
@Repository
public interface DistributorOperationRepository extends BaseRepository<DistributorOperation, UUID> {
    
    /**
     * Find all operations for a specific distributor.
     *
     * @param distributorId the distributor ID to search for
     * @return a Flux of distributor operations
     */
    Flux<DistributorOperation> findByDistributorId(UUID distributorId);
    
    /**
     * Find all active operations for a specific distributor.
     *
     * @param distributorId the distributor ID to search for
     * @return a Flux of active distributor operations
     */
    Flux<DistributorOperation> findByDistributorIdAndIsActiveTrue(UUID distributorId);
    
    /**
     * Find operations by country ID.
     *
erro     * @param countryId the country ID to search for
     * @return a Flux of distributor operations
     */
    Flux<DistributorOperation> findByCountryId(UUID countryId);
    
    /**
     * Find operations by administrative division ID.
     *
     * @param administrativeDivisionId the administrative division ID to search for
     * @return a Flux of distributor operations
     */
    Flux<DistributorOperation> findByAdministrativeDivisionId(UUID administrativeDivisionId);
    
    /**
     * Find a specific operation by distributor, country, and administrative division.
     *
     * @param distributorId the distributor ID
     * @param countryId the country ID
     * @param administrativeDivisionId the administrative division ID
     * @return a Mono containing the distributor operation if found
     */
    Mono<DistributorOperation> findByDistributorIdAndCountryIdAndAdministrativeDivisionIdAndIsActiveTrue(
            UUID distributorId, UUID countryId, UUID administrativeDivisionId);

    /**
     * Check if a distributor can operate in a specific country and administrative division.
     *
     * @param distributorId the distributor ID
     * @param countryId the country ID
     * @param administrativeDivisionId the administrative division ID
     * @return a Mono containing true if the operation exists and is active
     */
    Mono<Boolean> existsByDistributorIdAndCountryIdAndAdministrativeDivisionIdAndIsActiveTrue(
            UUID distributorId, UUID countryId, UUID administrativeDivisionId);
}
