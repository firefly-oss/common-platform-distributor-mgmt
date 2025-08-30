package com.firefly.core.distributor.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.core.distributor.core.mappers.DistributorAuditLogMapper;
import com.firefly.core.distributor.core.services.impl.DistributorAuditLogServiceImpl;
import com.firefly.core.distributor.interfaces.dtos.DistributorAuditLogDTO;
import com.firefly.core.distributor.models.entities.DistributorAuditLog;
import com.firefly.core.distributor.models.repositories.DistributorAuditLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class DistributorAuditLogServiceImplTest {

    private DistributorAuditLogRepository repository;
    private DistributorAuditLogMapper mapper;
    private DistributorAuditLogServiceImpl service;

    private DistributorAuditLog distributorAuditLog;
    private DistributorAuditLogDTO distributorAuditLogDTO;
    private FilterRequest<DistributorAuditLogDTO> filterRequest;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        repository = mock(DistributorAuditLogRepository.class);
        mapper = mock(DistributorAuditLogMapper.class);
        service = new DistributorAuditLogServiceImpl();

        // Use reflection to set the mocked dependencies
        try {
            java.lang.reflect.Field repoField = DistributorAuditLogServiceImpl.class.getDeclaredField("repository");
            repoField.setAccessible(true);
            repoField.set(service, repository);

            java.lang.reflect.Field mapperField = DistributorAuditLogServiceImpl.class.getDeclaredField("mapper");
            mapperField.setAccessible(true);
            mapperField.set(service, mapper);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up test", e);
        }

        // Initialize test data
        distributorAuditLog = new DistributorAuditLog();
        distributorAuditLog.setId(1L);

        distributorAuditLogDTO = new DistributorAuditLogDTO();
        distributorAuditLogDTO.setId(1L);

        filterRequest = new FilterRequest<>();
    }

    @Test
    void createDistributorAuditLog_ShouldCreateAndReturnDistributorAuditLog() {
        // Arrange
        when(mapper.toEntity(any(DistributorAuditLogDTO.class))).thenReturn(distributorAuditLog);
        when(repository.save(any(DistributorAuditLog.class))).thenReturn(Mono.just(distributorAuditLog));
        when(mapper.toDTO(any(DistributorAuditLog.class))).thenReturn(distributorAuditLogDTO);

        // Act & Assert
        StepVerifier.create(service.createDistributorAuditLog(distributorAuditLogDTO))
                .expectNext(distributorAuditLogDTO)
                .verifyComplete();

        // Verify
        verify(mapper).toEntity(distributorAuditLogDTO);
        verify(repository).save(distributorAuditLog);
        verify(mapper).toDTO(distributorAuditLog);
    }

    @Test
    void updateDistributorAuditLog_WhenDistributorAuditLogExists_ShouldUpdateAndReturnDistributorAuditLog() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.just(distributorAuditLog));
        when(mapper.toEntity(any(DistributorAuditLogDTO.class))).thenReturn(distributorAuditLog);
        when(repository.save(any(DistributorAuditLog.class))).thenReturn(Mono.just(distributorAuditLog));
        when(mapper.toDTO(any(DistributorAuditLog.class))).thenReturn(distributorAuditLogDTO);

        // Act & Assert
        StepVerifier.create(service.updateDistributorAuditLog(1L, distributorAuditLogDTO))
                .expectNext(distributorAuditLogDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(mapper).toEntity(distributorAuditLogDTO);
        verify(repository).save(distributorAuditLog);
        verify(mapper).toDTO(distributorAuditLog);
    }

    @Test
    void updateDistributorAuditLog_WhenDistributorAuditLogDoesNotExist_ShouldReturnError() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.updateDistributorAuditLog(1L, distributorAuditLogDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Distributor audit log not found with ID: 1"))
                .verify();

        // Verify
        verify(repository).findById(1L);
        verify(mapper, never()).toEntity(any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void deleteDistributorAuditLog_WhenDistributorAuditLogExists_ShouldDeleteDistributorAuditLog() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.just(distributorAuditLog));
        when(repository.deleteById(anyLong())).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDistributorAuditLog(1L))
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void deleteDistributorAuditLog_WhenDistributorAuditLogDoesNotExist_ShouldReturnError() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDistributorAuditLog(1L))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Distributor audit log not found with ID: 1"))
                .verify();

        // Verify
        verify(repository).findById(1L);
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    void getDistributorAuditLogById_WhenDistributorAuditLogExists_ShouldReturnDistributorAuditLog() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.just(distributorAuditLog));
        when(mapper.toDTO(any(DistributorAuditLog.class))).thenReturn(distributorAuditLogDTO);

        // Act & Assert
        StepVerifier.create(service.getDistributorAuditLogById(1L))
                .expectNext(distributorAuditLogDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(mapper).toDTO(distributorAuditLog);
    }

    @Test
    void getDistributorAuditLogById_WhenDistributorAuditLogDoesNotExist_ShouldReturnError() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getDistributorAuditLogById(1L))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Distributor audit log not found with ID: 1"))
                .verify();

        // Verify
        verify(repository).findById(1L);
        verify(mapper, never()).toDTO(any());
    }
}
