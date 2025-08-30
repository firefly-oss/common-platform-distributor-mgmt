package com.firefly.core.distributor.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.distributor.core.mappers.DistributorSimulationMapper;
import com.firefly.core.distributor.core.services.DistributorSimulationService;
import com.firefly.core.distributor.interfaces.dtos.DistributorSimulationDTO;
import com.firefly.core.distributor.models.entities.DistributorSimulation;
import com.firefly.core.distributor.models.repositories.DistributorSimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Implementation of the DistributorSimulationService interface.
 */
@Service
@Transactional
public class DistributorSimulationServiceImpl implements DistributorSimulationService {

    @Autowired
    private DistributorSimulationRepository repository;

    @Autowired
    private DistributorSimulationMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorSimulationDTO>> filterDistributorSimulations(FilterRequest<DistributorSimulationDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        DistributorSimulation.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<DistributorSimulationDTO> createDistributorSimulation(DistributorSimulationDTO distributorSimulationDTO) {
        return Mono.just(distributorSimulationDTO)
                .map(mapper::toEntity)
                .doOnNext(simulation -> {
                    simulation.setCreatedAt(LocalDateTime.now());
                    if (simulation.getIsActive() == null) {
                        simulation.setIsActive(true);
                    }
                    if (simulation.getSimulationStatus() == null) {
                        simulation.setSimulationStatus("PENDING");
                    }
                })
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorSimulationDTO> updateDistributorSimulation(Long id, DistributorSimulationDTO distributorSimulationDTO) {
        return repository.findById(id)
                .flatMap(existingSimulation -> {
                    DistributorSimulation updatedSimulation = mapper.toEntity(distributorSimulationDTO);
                    updatedSimulation.setId(id);
                    updatedSimulation.setCreatedAt(existingSimulation.getCreatedAt());
                    updatedSimulation.setCreatedBy(existingSimulation.getCreatedBy());
                    updatedSimulation.setUpdatedAt(LocalDateTime.now());
                    return repository.save(updatedSimulation);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteDistributorSimulation(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<DistributorSimulationDTO> getDistributorSimulationById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorSimulationDTO> getSimulationsByDistributorId(Long distributorId) {
        return repository.findByDistributorId(distributorId)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorSimulationDTO> getActiveSimulationsByDistributorId(Long distributorId) {
        return repository.findByDistributorIdAndIsActiveTrue(distributorId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorSimulationDTO> getSimulationByApplicationId(Long applicationId) {
        return repository.findByApplicationId(applicationId)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorSimulationDTO> getSimulationsByStatus(String simulationStatus) {
        return repository.findBySimulationStatus(simulationStatus)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorSimulationDTO> getSimulationsByDistributorIdAndStatus(Long distributorId, String simulationStatus) {
        return repository.findByDistributorIdAndSimulationStatus(distributorId, simulationStatus)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorSimulationDTO> updateSimulationStatus(Long id, String simulationStatus, Long updatedBy) {
        return repository.findById(id)
                .flatMap(simulation -> {
                    simulation.setSimulationStatus(simulationStatus);
                    simulation.setUpdatedAt(LocalDateTime.now());
                    simulation.setUpdatedBy(updatedBy);
                    return repository.save(simulation);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorSimulationDTO> activateDistributorSimulation(Long id, Long updatedBy) {
        return repository.findById(id)
                .flatMap(simulation -> {
                    simulation.setIsActive(true);
                    simulation.setUpdatedAt(LocalDateTime.now());
                    simulation.setUpdatedBy(updatedBy);
                    return repository.save(simulation);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorSimulationDTO> deactivateDistributorSimulation(Long id, Long updatedBy) {
        return repository.findById(id)
                .flatMap(simulation -> {
                    simulation.setIsActive(false);
                    simulation.setUpdatedAt(LocalDateTime.now());
                    simulation.setUpdatedBy(updatedBy);
                    return repository.save(simulation);
                })
                .map(mapper::toDTO);
    }
}
