package com.firefly.core.distributor.core.services;

import com.firefly.core.distributor.core.mappers.DistributorOperationMapper;
import com.firefly.core.distributor.core.services.impl.DistributorOperationServiceImpl;
import com.firefly.core.distributor.interfaces.dtos.DistributorOperationDTO;
import com.firefly.core.distributor.models.entities.DistributorOperation;
import com.firefly.core.distributor.models.repositories.DistributorOperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class DistributorOperationServiceImplTest {

    private DistributorOperationRepository repository;
    private DistributorOperationMapper mapper;
    private DistributorOperationServiceImpl service;

    private DistributorOperation distributorOperation;
    private DistributorOperationDTO distributorOperationDTO;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        repository = mock(DistributorOperationRepository.class);
        mapper = mock(DistributorOperationMapper.class);
        service = new DistributorOperationServiceImpl();

        // Use reflection to set the mocked dependencies
        try {
            java.lang.reflect.Field repoField = DistributorOperationServiceImpl.class.getDeclaredField("repository");
            repoField.setAccessible(true);
            repoField.set(service, repository);

            java.lang.reflect.Field mapperField = DistributorOperationServiceImpl.class.getDeclaredField("mapper");
            mapperField.setAccessible(true);
            mapperField.set(service, mapper);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up test", e);
        }

        // Initialize test data
        distributorOperation = DistributorOperation.builder()
                .id(1L)
                .distributorId(1L)
                .countryId(1L)
                .administrativeDivisionId(1L)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        distributorOperationDTO = DistributorOperationDTO.builder()
                .id(1L)
                .distributorId(1L)
                .countryId(1L)
                .administrativeDivisionId(1L)
                .isActive(true)
                .build();
    }

    @Test
    void createDistributorOperation_ShouldCreateAndReturnOperation() {
        // Arrange
        when(mapper.toEntity(any(DistributorOperationDTO.class))).thenReturn(distributorOperation);
        when(repository.save(any(DistributorOperation.class))).thenReturn(Mono.just(distributorOperation));
        when(mapper.toDTO(any(DistributorOperation.class))).thenReturn(distributorOperationDTO);

        // Act & Assert
        StepVerifier.create(service.createDistributorOperation(distributorOperationDTO))
                .expectNext(distributorOperationDTO)
                .verifyComplete();

        // Verify
        verify(mapper).toEntity(distributorOperationDTO);
        verify(repository).save(any(DistributorOperation.class));
        verify(mapper).toDTO(distributorOperation);
    }

    @Test
    void updateDistributorOperation_WhenOperationExists_ShouldUpdateAndReturnOperation() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.just(distributorOperation));
        when(mapper.toEntity(any(DistributorOperationDTO.class))).thenReturn(distributorOperation);
        when(repository.save(any(DistributorOperation.class))).thenReturn(Mono.just(distributorOperation));
        when(mapper.toDTO(any(DistributorOperation.class))).thenReturn(distributorOperationDTO);

        // Act & Assert
        StepVerifier.create(service.updateDistributorOperation(1L, distributorOperationDTO))
                .expectNext(distributorOperationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(mapper).toEntity(distributorOperationDTO);
        verify(repository).save(any(DistributorOperation.class));
        verify(mapper).toDTO(distributorOperation);
    }

    @Test
    void getDistributorOperationById_WhenOperationExists_ShouldReturnOperation() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.just(distributorOperation));
        when(mapper.toDTO(any(DistributorOperation.class))).thenReturn(distributorOperationDTO);

        // Act & Assert
        StepVerifier.create(service.getDistributorOperationById(1L))
                .expectNext(distributorOperationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(mapper).toDTO(distributorOperation);
    }

    @Test
    void getDistributorOperationById_WhenOperationDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getDistributorOperationById(1L))
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void getOperationsByDistributorId_ShouldReturnOperations() {
        // Arrange
        when(repository.findByDistributorId(anyLong())).thenReturn(Flux.just(distributorOperation));
        when(mapper.toDTO(any(DistributorOperation.class))).thenReturn(distributorOperationDTO);

        // Act & Assert
        StepVerifier.create(service.getOperationsByDistributorId(1L))
                .expectNext(distributorOperationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByDistributorId(1L);
        verify(mapper).toDTO(distributorOperation);
    }

    @Test
    void getActiveOperationsByDistributorId_ShouldReturnActiveOperations() {
        // Arrange
        when(repository.findByDistributorIdAndIsActiveTrue(anyLong())).thenReturn(Flux.just(distributorOperation));
        when(mapper.toDTO(any(DistributorOperation.class))).thenReturn(distributorOperationDTO);

        // Act & Assert
        StepVerifier.create(service.getActiveOperationsByDistributorId(1L))
                .expectNext(distributorOperationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByDistributorIdAndIsActiveTrue(1L);
        verify(mapper).toDTO(distributorOperation);
    }

    @Test
    void canDistributorOperateInLocation_WhenOperationExists_ShouldReturnTrue() {
        // Arrange
        when(repository.existsByDistributorIdAndCountryIdAndAdministrativeDivisionIdAndIsActiveTrue(
                anyLong(), anyLong(), anyLong())).thenReturn(Mono.just(true));

        // Act & Assert
        StepVerifier.create(service.canDistributorOperateInLocation(1L, 1L, 1L))
                .expectNext(true)
                .verifyComplete();

        // Verify
        verify(repository).existsByDistributorIdAndCountryIdAndAdministrativeDivisionIdAndIsActiveTrue(1L, 1L, 1L);
    }

    @Test
    void canDistributorOperateInLocation_WhenOperationDoesNotExist_ShouldReturnFalse() {
        // Arrange
        when(repository.existsByDistributorIdAndCountryIdAndAdministrativeDivisionIdAndIsActiveTrue(
                anyLong(), anyLong(), anyLong())).thenReturn(Mono.just(false));

        // Act & Assert
        StepVerifier.create(service.canDistributorOperateInLocation(1L, 1L, 1L))
                .expectNext(false)
                .verifyComplete();

        // Verify
        verify(repository).existsByDistributorIdAndCountryIdAndAdministrativeDivisionIdAndIsActiveTrue(1L, 1L, 1L);
    }

    @Test
    void activateDistributorOperation_WhenOperationExists_ShouldActivateAndReturnOperation() {
        // Arrange
        DistributorOperation inactiveOperation = DistributorOperation.builder()
                .id(1L)
                .distributorId(1L)
                .countryId(1L)
                .administrativeDivisionId(1L)
                .isActive(false)
                .build();

        DistributorOperation activeOperation = DistributorOperation.builder()
                .id(1L)
                .distributorId(1L)
                .countryId(1L)
                .administrativeDivisionId(1L)
                .isActive(true)
                .build();

        when(repository.findById(anyLong())).thenReturn(Mono.just(inactiveOperation));
        when(repository.save(any(DistributorOperation.class))).thenReturn(Mono.just(activeOperation));
        when(mapper.toDTO(any(DistributorOperation.class))).thenReturn(distributorOperationDTO);

        // Act & Assert
        StepVerifier.create(service.activateDistributorOperation(1L, 1L))
                .expectNext(distributorOperationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(repository).save(any(DistributorOperation.class));
        verify(mapper).toDTO(activeOperation);
    }

    @Test
    void deactivateDistributorOperation_WhenOperationExists_ShouldDeactivateAndReturnOperation() {
        // Arrange
        DistributorOperation inactiveOperation = DistributorOperation.builder()
                .id(1L)
                .distributorId(1L)
                .countryId(1L)
                .administrativeDivisionId(1L)
                .isActive(false)
                .build();

        when(repository.findById(anyLong())).thenReturn(Mono.just(distributorOperation));
        when(repository.save(any(DistributorOperation.class))).thenReturn(Mono.just(inactiveOperation));
        when(mapper.toDTO(any(DistributorOperation.class))).thenReturn(distributorOperationDTO);

        // Act & Assert
        StepVerifier.create(service.deactivateDistributorOperation(1L, 1L))
                .expectNext(distributorOperationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(repository).save(any(DistributorOperation.class));
        verify(mapper).toDTO(inactiveOperation);
    }

    @Test
    void deleteDistributorOperation_ShouldDeleteOperation() {
        // Arrange
        when(repository.deleteById(anyLong())).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDistributorOperation(1L))
                .verifyComplete();

        // Verify
        verify(repository).deleteById(1L);
    }
}
