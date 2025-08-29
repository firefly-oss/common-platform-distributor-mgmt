package com.firefly.core.distributor.models.repositories;

import com.firefly.core.distributor.models.entities.LendingConfiguration;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository for managing LendingConfiguration entities.
 */
@Repository
public interface LendingConfigurationRepository extends BaseRepository<LendingConfiguration, Long> {

    /**
     * Find all lending configurations for a specific product.
     *
     * @param productId the ID of the product
     * @return a Flux of lending configurations
     */
    Flux<LendingConfiguration> findByProductId(Long productId);

    /**
     * Find all active lending configurations for a specific product.
     *
     * @param productId the ID of the product
     * @param isActive the active status
     * @return a Flux of lending configurations
     */
    Flux<LendingConfiguration> findByProductIdAndIsActive(Long productId, Boolean isActive);

    /**
     * Find all lending configurations for a specific product and lending type.
     *
     * @param productId the ID of the product
     * @param lendingTypeId the ID of the lending type
     * @return a Flux of lending configurations
     */
    Flux<LendingConfiguration> findByProductIdAndLendingTypeId(Long productId, Long lendingTypeId);

    /**
     * Find the default lending configuration for a specific product.
     *
     * @param productId the ID of the product
     * @param isDefault the default status
     * @return a Mono of the default lending configuration
     */
    Mono<LendingConfiguration> findByProductIdAndIsDefault(Long productId, Boolean isDefault);

    /**
     * Find all lending configurations for products belonging to a specific distributor.
     *
     * @param distributorId the ID of the distributor
     * @return a Flux of lending configurations
     */
    @Query("SELECT lc.* FROM lending_configuration lc JOIN product p ON lc.product_id = p.id WHERE p.distributor_id = :distributorId")
    Flux<LendingConfiguration> findByProductDistributorId(Long distributorId);
}