package com.catalis.core.distributor.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.distributor.core.mappers.DistributorAuditLogMapper;
import com.catalis.core.distributor.interfaces.dtos.DistributorAuditLogDTO;
import com.catalis.core.distributor.models.entities.DistributorAuditLog;
import com.catalis.core.distributor.models.repositories.DistributorAuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class DistributorAuditLogServiceImpl implements DistributorAuditLogService {

    @Autowired
    private DistributorAuditLogRepository repository;

    @Autowired
    private DistributorAuditLogMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorAuditLogDTO>> filterDistributorAuditLogs(FilterRequest<DistributorAuditLogDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        DistributorAuditLog.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<DistributorAuditLogDTO> createDistributorAuditLog(DistributorAuditLogDTO distributorAuditLogDTO) {
        return Mono.just(distributorAuditLogDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorAuditLogDTO> updateDistributorAuditLog(Long distributorAuditLogId, DistributorAuditLogDTO distributorAuditLogDTO) {
        return repository.findById(distributorAuditLogId)
                .switchIfEmpty(Mono.error(new RuntimeException("Distributor audit log not found with ID: " + distributorAuditLogId)))
                .flatMap(existingDistributorAuditLog -> {
                    DistributorAuditLog updatedDistributorAuditLog = mapper.toEntity(distributorAuditLogDTO);
                    updatedDistributorAuditLog.setId(distributorAuditLogId);
                    return repository.save(updatedDistributorAuditLog);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteDistributorAuditLog(Long distributorAuditLogId) {
        return repository.findById(distributorAuditLogId)
                .switchIfEmpty(Mono.error(new RuntimeException("Distributor audit log not found with ID: " + distributorAuditLogId)))
                .flatMap(distributorAuditLog -> repository.deleteById(distributorAuditLogId));
    }

    @Override
    public Mono<DistributorAuditLogDTO> getDistributorAuditLogById(Long distributorAuditLogId) {
        return repository.findById(distributorAuditLogId)
                .switchIfEmpty(Mono.error(new RuntimeException("Distributor audit log not found with ID: " + distributorAuditLogId)))
                .map(mapper::toDTO);
    }
}