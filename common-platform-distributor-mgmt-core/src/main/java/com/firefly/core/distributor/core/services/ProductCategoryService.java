package com.firefly.core.distributor.core.services;

import com.firefly.core.distributor.interfaces.dtos.ProductCategoryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing product categories.
 */
public interface ProductCategoryService {

    /**
     * Get all product categories.
     *
     * @return A Flux of all product categories
     */
    Flux<ProductCategoryDTO> getAllProductCategories();

    /**
     * Get all active product categories.
     *
     * @return A Flux of active product categories
     */
    Flux<ProductCategoryDTO> getActiveProductCategories();

    /**
     * Get a product category by its ID.
     *
     * @param id The ID of the product category
     * @return A Mono containing the product category if found
     */
    Mono<ProductCategoryDTO> getProductCategoryById(Long id);

    /**
     * Get a product category by its code.
     *
     * @param code The code of the product category
     * @return A Mono containing the product category if found
     */
    Mono<ProductCategoryDTO> getProductCategoryByCode(String code);

    /**
     * Create a new product category.
     *
     * @param productCategoryDTO The product category to create
     * @return A Mono containing the created product category
     */
    Mono<ProductCategoryDTO> createProductCategory(ProductCategoryDTO productCategoryDTO);

    /**
     * Update an existing product category.
     *
     * @param id The ID of the product category to update
     * @param productCategoryDTO The updated product category data
     * @return A Mono containing the updated product category
     */
    Mono<ProductCategoryDTO> updateProductCategory(Long id, ProductCategoryDTO productCategoryDTO);

    /**
     * Delete a product category by its ID.
     *
     * @param id The ID of the product category to delete
     * @return A Mono that completes when the product category is deleted
     */
    Mono<Void> deleteProductCategory(Long id);
}