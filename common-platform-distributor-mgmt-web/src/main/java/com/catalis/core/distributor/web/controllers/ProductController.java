package com.catalis.core.distributor.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.distributor.core.services.ProductService;
import com.catalis.core.distributor.core.services.ProductCategoryService;
import com.catalis.core.distributor.interfaces.dtos.ProductCategoryDTO;
import com.catalis.core.distributor.interfaces.dtos.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing products.
 */
@RestController
@RequestMapping("/api/v1/distributors/{distributorId}/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * POST /api/v1/distributors/{distributorId}/products/filter : Filter products
     *
     * @param distributorId the ID of the distributor
     * @param filterRequest the filter request containing criteria and pagination
     * @return the ResponseEntity with status 200 (OK) and the list of products in body
     */
    @PostMapping("/filter")
    public Mono<ResponseEntity<PaginationResponse<ProductDTO>>> filterProducts(
            @PathVariable Long distributorId,
            @RequestBody FilterRequest<ProductDTO> filterRequest) {
        
        // Ensure we're only filtering products for the specified distributor
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new ProductDTO());
        }
        filterRequest.getFilters().setDistributorId(distributorId);
        
        return productService.filterProducts(filterRequest)
                .map(ResponseEntity::ok);
    }

    /**
     * POST /api/v1/distributors/{distributorId}/products : Create a new product
     *
     * @param distributorId the ID of the distributor
     * @param productDTO the product to create
     * @return the ResponseEntity with status 201 (Created) and with body the new product
     */
    @PostMapping
    public Mono<ResponseEntity<ProductDTO>> createProduct(
            @PathVariable Long distributorId,
            @RequestBody ProductDTO productDTO) {
        
        productDTO.setDistributorId(distributorId);
        
        return productService.createProduct(productDTO)
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    /**
     * PUT /api/v1/distributors/{distributorId}/products/{productId} : Update an existing product
     *
     * @param distributorId the ID of the distributor
     * @param productId the ID of the product to update
     * @param productDTO the product to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated product
     */
    @PutMapping("/{productId}")
    public Mono<ResponseEntity<ProductDTO>> updateProduct(
            @PathVariable Long distributorId,
            @PathVariable Long productId,
            @RequestBody ProductDTO productDTO) {
        
        productDTO.setDistributorId(distributorId);
        
        return productService.updateProduct(productId, productDTO)
                .map(ResponseEntity::ok);
    }

    /**
     * DELETE /api/v1/distributors/{distributorId}/products/{productId} : Delete a product
     *
     * @param distributorId the ID of the distributor
     * @param productId the ID of the product to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{productId}")
    public Mono<ResponseEntity<Void>> deleteProduct(
            @PathVariable Long distributorId,
            @PathVariable Long productId) {
        
        return productService.deleteProduct(productId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    /**
     * GET /api/v1/distributors/{distributorId}/products/{productId} : Get a product by ID
     *
     * @param distributorId the ID of the distributor
     * @param productId the ID of the product to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the product
     */
    @GetMapping("/{productId}")
    public Mono<ResponseEntity<ProductDTO>> getProductById(
            @PathVariable Long distributorId,
            @PathVariable Long productId) {
        
        return productService.getProductById(productId)
                .map(ResponseEntity::ok);
    }

    /**
     * GET /api/v1/distributors/{distributorId}/products : Get all products for a distributor
     *
     * @param distributorId the ID of the distributor
     * @return the ResponseEntity with status 200 (OK) and with body the list of products
     */
    @GetMapping
    public Mono<ResponseEntity<Flux<ProductDTO>>> getProductsByDistributorId(
            @PathVariable Long distributorId) {
        
        return Mono.just(ResponseEntity.ok(productService.getProductsByDistributorId(distributorId)));
    }

    /**
     * GET /api/v1/distributors/{distributorId}/products/active : Get all active products for a distributor
     *
     * @param distributorId the ID of the distributor
     * @return the ResponseEntity with status 200 (OK) and with body the list of active products
     */
    @GetMapping("/active")
    public Mono<ResponseEntity<Flux<ProductDTO>>> getActiveProductsByDistributorId(
            @PathVariable Long distributorId) {
        
        return Mono.just(ResponseEntity.ok(productService.getActiveProductsByDistributorId(distributorId)));
    }

    /**
     * GET /api/v1/distributors/{distributorId}/products/category/{categoryId} : Get all products for a distributor by category
     *
     * @param distributorId the ID of the distributor
     * @param categoryId the ID of the product category
     * @return the ResponseEntity with status 200 (OK) and with body the list of products
     */
    @GetMapping("/category/{categoryId}")
    public Mono<ResponseEntity<Flux<ProductDTO>>> getProductsByDistributorIdAndCategory(
            @PathVariable Long distributorId,
            @PathVariable Long categoryId) {
        
        return productCategoryService.getProductCategoryById(categoryId)
                .map(category -> ResponseEntity.ok(productService.getProductsByDistributorIdAndCategory(distributorId, category)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}