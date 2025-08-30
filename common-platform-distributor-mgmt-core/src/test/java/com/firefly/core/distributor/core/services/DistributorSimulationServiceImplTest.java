package com.firefly.core.distributor.core.services;

import com.firefly.core.distributor.core.mappers.DistributorSimulationMapper;
import com.firefly.core.distributor.core.services.impl.DistributorSimulationServiceImpl;
import com.firefly.core.distributor.interfaces.dtos.DistributorSimulationDTO;
import com.firefly.core.distributor.models.entities.DistributorSimulation;
import com.firefly.core.distributor.models.repositories.DistributorSimulationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DistributorSimulationServiceImplTest {

    private DistributorSimulationRepository repository;
    private DistributorSimulationMapper mapper;
    private DistributorSimulationServiceImpl service;

    private DistributorSimulation distributorSimulation;
    private DistributorSimulationDTO distributorSimulationDTO;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        repository = mock(DistributorSimulationRepository.class);
        mapper = mock(DistributorSimulationMapper.class);
        service = new DistributorSimulationServiceImpl();

        // Use reflection to set the mocked dependencies
        try {
            java.lang.reflect.Field repoField = DistributorSimulationServiceImpl.class.getDeclaredField("repository");
            repoField.setAccessible(true);
            repoField.set(service, repository);

            java.lang.reflect.Field mapperField = DistributorSimulationServiceImpl.class.getDeclaredField("mapper");
            mapperField.setAccessible(true);
            mapperField.set(service, mapper);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up test", e);
        }

        // Initialize test data
        distributorSimulation = DistributorSimulation.builder()
                .id(1L)
                .distributorId(1L)
                .applicationId(1L)
                .simulationStatus("PENDING")
                .notes("Test simulation")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        distributorSimulationDTO = DistributorSimulationDTO.builder()
                .id(1L)
                .distributorId(1L)
                .applicationId(1L)
                .simulationStatus("PENDING")
                .notes("Test simulation")
                .isActive(true)
                .build();
    }

    @Test
    void createDistributorSimulation_ShouldCreateAndReturnSimulation() {
        // Arrange
        when(mapper.toEntity(any(DistributorSimulationDTO.class))).thenReturn(distributorSimulation);
        when(repository.save(any(DistributorSimulation.class))).thenReturn(Mono.just(distributorSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.createDistributorSimulation(distributorSimulationDTO))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(mapper).toEntity(distributorSimulationDTO);
        verify(repository).save(any(DistributorSimulation.class));
        verify(mapper).toDTO(distributorSimulation);
    }

    @Test
    void updateDistributorSimulation_WhenSimulationExists_ShouldUpdateAndReturnSimulation() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.just(distributorSimulation));
        when(mapper.toEntity(any(DistributorSimulationDTO.class))).thenReturn(distributorSimulation);
        when(repository.save(any(DistributorSimulation.class))).thenReturn(Mono.just(distributorSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.updateDistributorSimulation(1L, distributorSimulationDTO))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(mapper).toEntity(distributorSimulationDTO);
        verify(repository).save(any(DistributorSimulation.class));
        verify(mapper).toDTO(distributorSimulation);
    }

    @Test
    void getDistributorSimulationById_WhenSimulationExists_ShouldReturnSimulation() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.just(distributorSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.getDistributorSimulationById(1L))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(mapper).toDTO(distributorSimulation);
    }

    @Test
    void getDistributorSimulationById_WhenSimulationDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getDistributorSimulationById(1L))
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void getSimulationsByDistributorId_ShouldReturnSimulations() {
        // Arrange
        when(repository.findByDistributorId(anyLong())).thenReturn(Flux.just(distributorSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.getSimulationsByDistributorId(1L))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByDistributorId(1L);
        verify(mapper).toDTO(distributorSimulation);
    }

    @Test
    void getActiveSimulationsByDistributorId_ShouldReturnActiveSimulations() {
        // Arrange
        when(repository.findByDistributorIdAndIsActiveTrue(anyLong())).thenReturn(Flux.just(distributorSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.getActiveSimulationsByDistributorId(1L))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByDistributorIdAndIsActiveTrue(1L);
        verify(mapper).toDTO(distributorSimulation);
    }

    @Test
    void getSimulationByApplicationId_WhenSimulationExists_ShouldReturnSimulation() {
        // Arrange
        when(repository.findByApplicationId(anyLong())).thenReturn(Mono.just(distributorSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.getSimulationByApplicationId(1L))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByApplicationId(1L);
        verify(mapper).toDTO(distributorSimulation);
    }

    @Test
    void getSimulationsByStatus_ShouldReturnSimulationsWithStatus() {
        // Arrange
        when(repository.findBySimulationStatus(anyString())).thenReturn(Flux.just(distributorSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.getSimulationsByStatus("PENDING"))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findBySimulationStatus("PENDING");
        verify(mapper).toDTO(distributorSimulation);
    }

    @Test
    void getSimulationsByDistributorIdAndStatus_ShouldReturnSimulations() {
        // Arrange
        when(repository.findByDistributorIdAndSimulationStatus(anyLong(), anyString()))
                .thenReturn(Flux.just(distributorSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.getSimulationsByDistributorIdAndStatus(1L, "PENDING"))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByDistributorIdAndSimulationStatus(1L, "PENDING");
        verify(mapper).toDTO(distributorSimulation);
    }

    @Test
    void updateSimulationStatus_WhenSimulationExists_ShouldUpdateStatusAndReturnSimulation() {
        // Arrange
        DistributorSimulation updatedSimulation = DistributorSimulation.builder()
                .id(1L)
                .distributorId(1L)
                .applicationId(1L)
                .simulationStatus("COMPLETED")
                .notes("Test simulation")
                .isActive(true)
                .build();

        when(repository.findById(anyLong())).thenReturn(Mono.just(distributorSimulation));
        when(repository.save(any(DistributorSimulation.class))).thenReturn(Mono.just(updatedSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.updateSimulationStatus(1L, "COMPLETED", 1L))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(repository).save(any(DistributorSimulation.class));
        verify(mapper).toDTO(updatedSimulation);
    }

    @Test
    void activateDistributorSimulation_WhenSimulationExists_ShouldActivateAndReturnSimulation() {
        // Arrange
        DistributorSimulation inactiveSimulation = DistributorSimulation.builder()
                .id(1L)
                .distributorId(1L)
                .applicationId(1L)
                .simulationStatus("PENDING")
                .isActive(false)
                .build();

        DistributorSimulation activeSimulation = DistributorSimulation.builder()
                .id(1L)
                .distributorId(1L)
                .applicationId(1L)
                .simulationStatus("PENDING")
                .isActive(true)
                .build();

        when(repository.findById(anyLong())).thenReturn(Mono.just(inactiveSimulation));
        when(repository.save(any(DistributorSimulation.class))).thenReturn(Mono.just(activeSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.activateDistributorSimulation(1L, 1L))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(repository).save(any(DistributorSimulation.class));
        verify(mapper).toDTO(activeSimulation);
    }

    @Test
    void deactivateDistributorSimulation_WhenSimulationExists_ShouldDeactivateAndReturnSimulation() {
        // Arrange
        DistributorSimulation inactiveSimulation = DistributorSimulation.builder()
                .id(1L)
                .distributorId(1L)
                .applicationId(1L)
                .simulationStatus("PENDING")
                .isActive(false)
                .build();

        when(repository.findById(anyLong())).thenReturn(Mono.just(distributorSimulation));
        when(repository.save(any(DistributorSimulation.class))).thenReturn(Mono.just(inactiveSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.deactivateDistributorSimulation(1L, 1L))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(repository).save(any(DistributorSimulation.class));
        verify(mapper).toDTO(inactiveSimulation);
    }

    @Test
    void deleteDistributorSimulation_ShouldDeleteSimulation() {
        // Arrange
        when(repository.deleteById(anyLong())).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDistributorSimulation(1L))
                .verifyComplete();

        // Verify
        verify(repository).deleteById(1L);
    }
}
