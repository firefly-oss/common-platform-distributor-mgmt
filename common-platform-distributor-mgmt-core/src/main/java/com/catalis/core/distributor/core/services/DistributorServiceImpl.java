package com.catalis.core.distributor.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.distributor.core.mappers.DistributorMapper;
import com.catalis.core.distributor.interfaces.dtos.DistributorDTO;
import com.catalis.core.distributor.models.entities.Distributor;
import com.catalis.core.distributor.models.repositories.DistributorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class DistributorServiceImpl implements DistributorService {

    @Autowired
    private DistributorRepository repository;

    @Autowired
    private DistributorMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorDTO>> filterDistributors(FilterRequest<DistributorDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        Distributor.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<DistributorDTO> createDistributor(DistributorDTO distributorDTO) {
        return Mono.just(distributorDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorDTO> updateDistributor(Long distributorId, DistributorDTO distributorDTO) {
        return repository.findById(distributorId)
                .switchIfEmpty(Mono.error(new RuntimeException("Distributor not found with ID: " + distributorId)))
                .flatMap(existingDistributor -> {
                    Distributor updatedDistributor = mapper.toEntity(distributorDTO);
                    updatedDistributor.setId(distributorId);
                    return repository.save(updatedDistributor);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteDistributor(Long distributorId) {
        return repository.findById(distributorId)
                .switchIfEmpty(Mono.error(new RuntimeException("Distributor not found with ID: " + distributorId)))
                .flatMap(distributor -> repository.deleteById(distributorId));
    }

    @Override
    public Mono<DistributorDTO> getDistributorById(Long distributorId) {
        return repository.findById(distributorId)
                .switchIfEmpty(Mono.error(new RuntimeException("Distributor not found with ID: " + distributorId)))
                .map(mapper::toDTO);
    }
}