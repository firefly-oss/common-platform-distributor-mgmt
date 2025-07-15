package com.catalis.core.distributor.models.repositories;

import com.catalis.core.distributor.interfaces.enums.ThemeEnum;
import com.catalis.core.distributor.models.entities.DistributorBranding;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository interface for managing {@link DistributorBranding} entities.
 * Extends {@link BaseRepository} to inherit common CRUD operations.
 */
@Repository
public interface DistributorBrandingRepository extends BaseRepository<DistributorBranding, Long> {
    
    /**
     * Find all branding configurations for a specific distributor.
     *
     * @param distributorId the ID of the distributor
     * @return a Flux of branding configurations for the distributor
     */
    Flux<DistributorBranding> findByDistributorId(Long distributorId);
    
    /**
     * Find the default branding configuration for a specific distributor.
     *
     * @param distributorId the ID of the distributor
     * @return a Mono containing the default branding if found, or an empty Mono if not found
     */
    Mono<DistributorBranding> findByDistributorIdAndIsDefaultTrue(Long distributorId);
    
    /**
     * Find all branding configurations with a specific theme.
     *
     * @param theme the theme to search for
     * @return a Flux of branding configurations with the specified theme
     */
    Flux<DistributorBranding> findByTheme(ThemeEnum theme);
    
    /**
     * Delete all branding configurations for a specific distributor.
     *
     * @param distributorId the ID of the distributor
     * @return a Mono containing the number of deleted branding configurations
     */
    Mono<Long> deleteByDistributorId(Long distributorId);
}