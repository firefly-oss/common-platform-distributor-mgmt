package com.firefly.core.distributor.models.repositories;

import com.firefly.core.distributor.models.entities.DistributorSimulation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository interface for managing {@link DistributorSimulation} entities.
 * Extends {@link BaseRepository} to inherit common CRUD operations.
 */
@Repository
public interface DistributorSimulationRepository extends BaseRepository<DistributorSimulation, Long> {
    
    /**
     * Find all simulations for a specific distributor.
     *
     * @param distributorId the distributor ID to search for
     * @return a Flux of distributor simulations
     */
    Flux<DistributorSimulation> findByDistributorId(Long distributorId);
    
    /**
     * Find all active simulations for a specific distributor.
     *
     * @param distributorId the distributor ID to search for
     * @return a Flux of active distributor simulations
     */
    Flux<DistributorSimulation> findByDistributorIdAndIsActiveTrue(Long distributorId);
    
    /**
     * Find simulation by application ID.
     *
     * @param applicationId the application ID to search for
     * @return a Mono containing the distributor simulation if found
     */
    Mono<DistributorSimulation> findByApplicationId(Long applicationId);
    
    /**
     * Find active simulation by application ID.
     *
     * @param applicationId the application ID to search for
     * @return a Mono containing the active distributor simulation if found
     */
    Mono<DistributorSimulation> findByApplicationIdAndIsActiveTrue(Long applicationId);
    
    /**
     * Find simulations by status.
     *
     * @param simulationStatus the simulation status to search for
     * @return a Flux of distributor simulations
     */
    Flux<DistributorSimulation> findBySimulationStatus(String simulationStatus);
    
    /**
     * Find simulations by distributor and status.
     *
     * @param distributorId the distributor ID
     * @param simulationStatus the simulation status
     * @return a Flux of distributor simulations
     */
    Flux<DistributorSimulation> findByDistributorIdAndSimulationStatus(Long distributorId, String simulationStatus);
    
    /**
     * Find a specific simulation by distributor and application.
     *
     * @param distributorId the distributor ID
     * @param applicationId the application ID
     * @return a Mono containing the distributor simulation if found
     */
    Mono<DistributorSimulation> findByDistributorIdAndApplicationIdAndIsActiveTrue(Long distributorId, Long applicationId);
    
    /**
     * Check if a simulation exists for a specific distributor and application.
     *
     * @param distributorId the distributor ID
     * @param applicationId the application ID
     * @return a Mono containing true if the simulation exists and is active
     */
    Mono<Boolean> existsByDistributorIdAndApplicationIdAndIsActiveTrue(Long distributorId, Long applicationId);
}
