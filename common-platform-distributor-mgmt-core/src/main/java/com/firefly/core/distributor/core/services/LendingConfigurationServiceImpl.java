package com.firefly.core.distributor.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.distributor.core.mappers.LendingConfigurationMapper;
import com.firefly.core.distributor.interfaces.dtos.LendingConfigurationDTO;
import com.firefly.core.distributor.interfaces.dtos.LendingTypeDTO;
import com.firefly.core.distributor.models.entities.LendingConfiguration;
import com.firefly.core.distributor.models.repositories.LendingConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the LendingConfigurationService interface.
 */
@Service
@Transactional
public class LendingConfigurationServiceImpl implements LendingConfigurationService {

    @Autowired
    private LendingConfigurationRepository repository;

    @Autowired
    private LendingConfigurationMapper mapper;

    @Override
    public Mono<PaginationResponse<LendingConfigurationDTO>> filterLendingConfigurations(FilterRequest<LendingConfigurationDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        LendingConfiguration.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<LendingConfigurationDTO> createLendingConfiguration(LendingConfigurationDTO lendingConfigurationDTO) {
        return Mono.just(lendingConfigurationDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LendingConfigurationDTO> updateLendingConfiguration(Long lendingConfigurationId, LendingConfigurationDTO lendingConfigurationDTO) {
        return repository.findById(lendingConfigurationId)
                .switchIfEmpty(Mono.error(new RuntimeException("Lending configuration not found with ID: " + lendingConfigurationId)))
                .flatMap(existingConfig -> {
                    LendingConfiguration updatedConfig = mapper.toEntity(lendingConfigurationDTO);
                    updatedConfig.setId(lendingConfigurationId);
                    return repository.save(updatedConfig);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteLendingConfiguration(Long lendingConfigurationId) {
        return repository.findById(lendingConfigurationId)
                .switchIfEmpty(Mono.error(new RuntimeException("Lending configuration not found with ID: " + lendingConfigurationId)))
                .flatMap(config -> repository.deleteById(lendingConfigurationId));
    }

    @Override
    public Mono<LendingConfigurationDTO> getLendingConfigurationById(Long lendingConfigurationId) {
        return repository.findById(lendingConfigurationId)
                .switchIfEmpty(Mono.error(new RuntimeException("Lending configuration not found with ID: " + lendingConfigurationId)))
                .map(mapper::toDTO);
    }

    @Override
    public Flux<LendingConfigurationDTO> getLendingConfigurationsByProductId(Long productId) {
        return repository.findByProductId(productId)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<LendingConfigurationDTO> getActiveLendingConfigurationsByProductId(Long productId) {
        return repository.findByProductIdAndIsActive(productId, true)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<LendingConfigurationDTO> getLendingConfigurationsByProductIdAndLendingType(Long productId, LendingTypeDTO lendingType) {
        return repository.findByProductIdAndLendingTypeId(productId, lendingType.getId())
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LendingConfigurationDTO> getDefaultLendingConfigurationByProductId(Long productId) {
        return repository.findByProductIdAndIsDefault(productId, true)
                .switchIfEmpty(Mono.error(new RuntimeException("Default lending configuration not found for product ID: " + productId)))
                .map(mapper::toDTO);
    }

    @Override
    public Flux<LendingConfigurationDTO> getLendingConfigurationsByDistributorId(Long distributorId) {
        return repository.findByProductDistributorId(distributorId)
                .map(mapper::toDTO);
    }
}