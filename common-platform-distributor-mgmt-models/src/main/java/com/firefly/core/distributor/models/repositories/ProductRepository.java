package com.firefly.core.distributor.models.repositories;

import com.firefly.core.distributor.models.entities.Product;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing Product entities.
 */
@Repository
public interface ProductRepository extends BaseRepository<Product, UUID> {

    /**
     * Find all products for a specific distributor.
     *
     * @param distributorId the ID of the distributor
     * @return a Flux of products
     */
    Flux<Product> findByDistributorId(UUID distributorId);

    /**
     * Find all active products for a specific distributor.
     *
     * @param distributorId the ID of the distributor
     * @param isActive the active status
     * @return a Flux of products
     */
    Flux<Product> findByDistributorIdAndIsActive(UUID distributorId, Boolean isActive);

    /**
     * Find all products for a specific distributor and category.
     *
     * @param distributorId the ID of the distributor
     * @param categoryId the ID of the product category
     * @return a Flux of products
     */
    Flux<Product> findByDistributorIdAndCategoryId(UUID distributorId, UUID categoryId);

    /**
     * Find a product by its SKU.
     *
     * @param distributorId the ID of the distributor
     * @param sku the product SKU
     * @return a Mono of the product
     */
    Mono<Product> findByDistributorIdAndSku(UUID distributorId, String sku);
}