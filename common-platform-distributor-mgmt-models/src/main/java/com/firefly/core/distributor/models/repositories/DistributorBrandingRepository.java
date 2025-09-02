package com.firefly.core.distributor.models.repositories;

import com.firefly.core.distributor.interfaces.enums.ThemeEnum;
import com.firefly.core.distributor.models.entities.DistributorBranding;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository interface for managing {@link DistributorBranding} entities.
 * Extends {@link BaseRepository} to inherit common CRUD operations.
 */
@Repository
public interface DistributorBrandingRepository extends BaseRepository<DistributorBranding, UUID> {
    
    /**
     * Find all branding configurations for a specific distributor.
     *
     * @param distributorId the ID of the distributor
     * @return a Flux of branding configurations for the distributor
     */
    Flux<DistributorBranding> findByDistributorId(UUID distributorId);
    
    /**
     * Find the default branding configuration for a specific distributor.
     *
     * @param distributorId the ID of the distributor
     * @return a Mono containing the default branding if found, or an empty Mono if not found
     */
    Mono<DistributorBranding> findByDistributorIdAndIsDefaultTrue(UUID distributorId);
    
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
    Mono<UUID> deleteByDistributorId(UUID distributorId);
}