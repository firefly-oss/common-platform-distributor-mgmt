package com.firefly.core.distributor.models.repositories;

import com.firefly.core.distributor.models.entities.DistributorOperation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository interface for managing {@link DistributorOperation} entities.
 * Extends {@link BaseRepository} to inherit common CRUD operations.
 */
@Repository
public interface DistributorOperationRepository extends BaseRepository<DistributorOperation, Long> {
    
    /**
     * Find all operations for a specific distributor.
     *
     * @param distributorId the distributor ID to search for
     * @return a Flux of distributor operations
     */
    Flux<DistributorOperation> findByDistributorId(Long distributorId);
    
    /**
     * Find all active operations for a specific distributor.
     *
     * @param distributorId the distributor ID to search for
     * @return a Flux of active distributor operations
     */
    Flux<DistributorOperation> findByDistributorIdAndIsActiveTrue(Long distributorId);
    
    /**
     * Find operations by country ID.
     *
     * @param countryId the country ID to search for
     * @return a Flux of distributor operations
     */
    Flux<DistributorOperation> findByCountryId(Long countryId);
    
    /**
     * Find operations by administrative division ID.
     *
     * @param administrativeDivisionId the administrative division ID to search for
     * @return a Flux of distributor operations
     */
    Flux<DistributorOperation> findByAdministrativeDivisionId(Long administrativeDivisionId);
    
    /**
     * Find a specific operation by distributor, country, and administrative division.
     *
     * @param distributorId the distributor ID
     * @param countryId the country ID
     * @param administrativeDivisionId the administrative division ID
     * @return a Mono containing the distributor operation if found
     */
    Mono<DistributorOperation> findByDistributorIdAndCountryIdAndAdministrativeDivisionIdAndIsActiveTrue(
            Long distributorId, Long countryId, Long administrativeDivisionId);
    
    /**
     * Check if a distributor can operate in a specific country and administrative division.
     *
     * @param distributorId the distributor ID
     * @param countryId the country ID
     * @param administrativeDivisionId the administrative division ID
     * @return a Mono containing true if the operation exists and is active
     */
    Mono<Boolean> existsByDistributorIdAndCountryIdAndAdministrativeDivisionIdAndIsActiveTrue(
            Long distributorId, Long countryId, Long administrativeDivisionId);
}
