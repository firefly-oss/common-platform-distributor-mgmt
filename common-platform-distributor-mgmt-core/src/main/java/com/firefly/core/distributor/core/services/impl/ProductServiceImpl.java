/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.distributor.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.distributor.core.mappers.ProductMapper;
import com.firefly.core.distributor.core.services.ProductService;
import com.firefly.core.distributor.interfaces.dtos.ProductCategoryDTO;
import com.firefly.core.distributor.interfaces.dtos.ProductDTO;
import com.firefly.core.distributor.models.entities.Product;
import com.firefly.core.distributor.models.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the ProductService interface.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    @Override
    public Mono<PaginationResponse<ProductDTO>> filterProducts(FilterRequest<ProductDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        Product.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<ProductDTO> createProduct(ProductDTO productDTO) {
        return Mono.just(productDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProductDTO> updateProduct(UUID productId, ProductDTO productDTO) {
        return repository.findById(productId)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found with ID: " + productId)))
                .flatMap(existingProduct -> {
                    mapper.updateEntityFromDto(productDTO, existingProduct);
                    return repository.save(existingProduct);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteProduct(UUID productId) {
        return repository.findById(productId)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found with ID: " + productId)))
                .flatMap(product -> repository.deleteById(productId));
    }

    @Override
    public Mono<ProductDTO> getProductById(UUID productId) {
        return repository.findById(productId)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found with ID: " + productId)))
                .map(mapper::toDTO);
    }

    @Override
    public Flux<ProductDTO> getProductsByDistributorId(UUID distributorId) {
        return repository.findByDistributorId(distributorId)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<ProductDTO> getActiveProductsByDistributorId(UUID distributorId) {
        return repository.findByDistributorIdAndIsActive(distributorId, true)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<ProductDTO> getProductsByDistributorIdAndCategory(UUID distributorId, ProductCategoryDTO category) {
        return repository.findByDistributorIdAndCategoryId(distributorId, category.getId())
                .map(mapper::toDTO);
    }
}