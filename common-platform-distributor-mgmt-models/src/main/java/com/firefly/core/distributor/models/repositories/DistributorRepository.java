package com.firefly.core.distributor.models.repositories;

import com.firefly.core.distributor.models.entities.Distributor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository interface for managing {@link Distributor} entities.
 * Extends {@link BaseRepository} to inherit common CRUD operations.
 */
@Repository
public interface DistributorRepository extends BaseRepository<Distributor, Long> {
    
    /**
     * Find a distributor by its external code.
     *
     * @param externalCode the external code to search for
     * @return a Mono containing the distributor if found, or an empty Mono if not found
     */
    Mono<Distributor> findByExternalCode(String externalCode);
    
    /**
     * Find all active distributors.
     *
     * @return a Flux of active distributors
     */
    Flux<Distributor> findByIsActiveTrue();
    
    /**
     * Find all test distributors.
     *
     * @return a Flux of test distributors
     */
    Flux<Distributor> findByIsTestDistributorTrue();
    
    /**
     * Find distributors by country code.
     *
     * @param countryCode the ISO 3166-1 alpha-2 country code
     * @return a Flux of distributors in the specified country
     */
    Flux<Distributor> findByCountryCode(String countryCode);
}