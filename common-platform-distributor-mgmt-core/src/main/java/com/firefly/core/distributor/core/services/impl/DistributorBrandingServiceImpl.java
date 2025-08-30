package com.firefly.core.distributor.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.distributor.core.mappers.DistributorBrandingMapper;
import com.firefly.core.distributor.core.services.DistributorBrandingService;
import com.firefly.core.distributor.interfaces.dtos.DistributorBrandingDTO;
import com.firefly.core.distributor.models.entities.DistributorBranding;
import com.firefly.core.distributor.models.repositories.DistributorBrandingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class DistributorBrandingServiceImpl implements DistributorBrandingService {

    @Autowired
    private DistributorBrandingRepository repository;

    @Autowired
    private DistributorBrandingMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorBrandingDTO>> filterDistributorBranding(FilterRequest<DistributorBrandingDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        DistributorBranding.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<DistributorBrandingDTO> createDistributorBranding(DistributorBrandingDTO distributorBrandingDTO) {
        return Mono.just(distributorBrandingDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorBrandingDTO> updateDistributorBranding(Long distributorBrandingId, DistributorBrandingDTO distributorBrandingDTO) {
        return repository.findById(distributorBrandingId)
                .switchIfEmpty(Mono.error(new RuntimeException("Distributor branding not found with ID: " + distributorBrandingId)))
                .flatMap(existingDistributorBranding -> {
                    DistributorBranding updatedDistributorBranding = mapper.toEntity(distributorBrandingDTO);
                    updatedDistributorBranding.setId(distributorBrandingId);
                    return repository.save(updatedDistributorBranding);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteDistributorBranding(Long distributorBrandingId) {
        return repository.findById(distributorBrandingId)
                .switchIfEmpty(Mono.error(new RuntimeException("Distributor branding not found with ID: " + distributorBrandingId)))
                .flatMap(distributorBranding -> repository.deleteById(distributorBrandingId));
    }

    @Override
    public Mono<DistributorBrandingDTO> getDistributorBrandingById(Long distributorBrandingId) {
        return repository.findById(distributorBrandingId)
                .switchIfEmpty(Mono.error(new RuntimeException("Distributor branding not found with ID: " + distributorBrandingId)))
                .map(mapper::toDTO);
    }
}