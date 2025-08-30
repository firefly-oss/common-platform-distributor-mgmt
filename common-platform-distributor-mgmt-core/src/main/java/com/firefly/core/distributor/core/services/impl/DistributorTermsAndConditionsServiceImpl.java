package com.firefly.core.distributor.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.distributor.core.mappers.DistributorTermsAndConditionsMapper;
import com.firefly.core.distributor.core.services.DistributorTermsAndConditionsService;
import com.firefly.core.distributor.interfaces.dtos.DistributorTermsAndConditionsDTO;
import com.firefly.core.distributor.models.entities.DistributorTermsAndConditions;
import com.firefly.core.distributor.models.repositories.DistributorTermsAndConditionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Implementation of the DistributorTermsAndConditionsService interface.
 */
@Service
@Transactional
public class DistributorTermsAndConditionsServiceImpl implements DistributorTermsAndConditionsService {

    @Autowired
    private DistributorTermsAndConditionsRepository repository;

    @Autowired
    private DistributorTermsAndConditionsMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorTermsAndConditionsDTO>> filterDistributorTermsAndConditions(FilterRequest<DistributorTermsAndConditionsDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        DistributorTermsAndConditions.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> createDistributorTermsAndConditions(DistributorTermsAndConditionsDTO distributorTermsAndConditionsDTO) {
        return Mono.just(distributorTermsAndConditionsDTO)
                .map(mapper::toEntity)
                .doOnNext(termsAndConditions -> {
                    termsAndConditions.setCreatedAt(LocalDateTime.now());
                    if (termsAndConditions.getIsActive() == null) {
                        termsAndConditions.setIsActive(true);
                    }
                    if (termsAndConditions.getStatus() == null) {
                        termsAndConditions.setStatus("DRAFT");
                    }
                })
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> updateDistributorTermsAndConditions(Long id, DistributorTermsAndConditionsDTO distributorTermsAndConditionsDTO) {
        return repository.findById(id)
                .flatMap(existingTermsAndConditions -> {
                    DistributorTermsAndConditions updatedTermsAndConditions = mapper.toEntity(distributorTermsAndConditionsDTO);
                    updatedTermsAndConditions.setId(id);
                    updatedTermsAndConditions.setCreatedAt(existingTermsAndConditions.getCreatedAt());
                    updatedTermsAndConditions.setCreatedBy(existingTermsAndConditions.getCreatedBy());
                    updatedTermsAndConditions.setUpdatedAt(LocalDateTime.now());
                    return repository.save(updatedTermsAndConditions);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteDistributorTermsAndConditions(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> getDistributorTermsAndConditionsById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorTermsAndConditionsDTO> getTermsAndConditionsByDistributorId(Long distributorId) {
        return repository.findByDistributorId(distributorId)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorTermsAndConditionsDTO> getActiveTermsAndConditionsByDistributorId(Long distributorId) {
        return repository.findByDistributorIdAndIsActiveTrue(distributorId)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorTermsAndConditionsDTO> getTermsAndConditionsByStatus(String status) {
        return repository.findByStatus(status)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorTermsAndConditionsDTO> getTermsAndConditionsByDistributorIdAndStatus(Long distributorId, String status) {
        return repository.findByDistributorIdAndStatus(distributorId, status)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorTermsAndConditionsDTO> getTermsAndConditionsByTemplateId(Long templateId) {
        return repository.findByTemplateId(templateId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> updateStatus(Long id, String status, Long updatedBy) {
        return repository.findById(id)
                .flatMap(termsAndConditions -> {
                    termsAndConditions.setStatus(status);
                    termsAndConditions.setUpdatedAt(LocalDateTime.now());
                    termsAndConditions.setUpdatedBy(updatedBy);
                    return repository.save(termsAndConditions);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> signTermsAndConditions(Long id, Long signedBy) {
        return repository.findById(id)
                .flatMap(termsAndConditions -> {
                    termsAndConditions.setStatus("SIGNED");
                    termsAndConditions.setSignedDate(LocalDateTime.now());
                    termsAndConditions.setSignedBy(signedBy);
                    termsAndConditions.setUpdatedAt(LocalDateTime.now());
                    termsAndConditions.setUpdatedBy(signedBy);
                    return repository.save(termsAndConditions);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorTermsAndConditionsDTO> getExpiringTermsAndConditions(LocalDateTime expirationDate) {
        return repository.findByExpirationDateBeforeAndIsActiveTrue(expirationDate)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Boolean> hasActiveSignedTerms(Long distributorId) {
        return repository.existsByDistributorIdAndStatusAndIsActiveTrue(distributorId, "SIGNED");
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> getLatestTermsAndConditions(Long distributorId) {
        return repository.findTopByDistributorIdAndIsActiveTrueOrderByCreatedAtDesc(distributorId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> activateTermsAndConditions(Long id, Long updatedBy) {
        return repository.findById(id)
                .flatMap(termsAndConditions -> {
                    termsAndConditions.setIsActive(true);
                    termsAndConditions.setUpdatedAt(LocalDateTime.now());
                    termsAndConditions.setUpdatedBy(updatedBy);
                    return repository.save(termsAndConditions);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> deactivateTermsAndConditions(Long id, Long updatedBy) {
        return repository.findById(id)
                .flatMap(termsAndConditions -> {
                    termsAndConditions.setIsActive(false);
                    termsAndConditions.setUpdatedAt(LocalDateTime.now());
                    termsAndConditions.setUpdatedBy(updatedBy);
                    return repository.save(termsAndConditions);
                })
                .map(mapper::toDTO);
    }
}
