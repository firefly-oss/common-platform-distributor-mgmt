package com.catalis.core.distributor.models.repositories;

import com.catalis.core.distributor.models.entities.ProductCategory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository for managing ProductCategory entities.
 */
@Repository
public interface ProductCategoryRepository extends BaseRepository<ProductCategory, Long> {

    /**
     * Find a product category by its code.
     *
     * @param code The unique code of the product category
     * @return A Mono containing the product category if found
     */
    Mono<ProductCategory> findByCode(String code);

    /**
     * Find all active product categories.
     *
     * @return A Flux of active product categories
     */
    Flux<ProductCategory> findByIsActiveTrue();

    /**
     * Find a product category by its name.
     *
     * @param name The name of the product category
     * @return A Mono containing the product category if found
     */
    Mono<ProductCategory> findByName(String name);
}