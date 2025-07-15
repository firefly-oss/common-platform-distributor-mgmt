package com.catalis.core.distributor.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.core.distributor.core.mappers.DistributorMapper;
import com.catalis.core.distributor.interfaces.dtos.DistributorDTO;
import com.catalis.core.distributor.models.entities.Distributor;
import com.catalis.core.distributor.models.repositories.DistributorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class DistributorServiceImplTest {

    private DistributorRepository repository;
    private DistributorMapper mapper;
    private DistributorServiceImpl service;

    private Distributor distributor;
    private DistributorDTO distributorDTO;
    private FilterRequest<DistributorDTO> filterRequest;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        repository = mock(DistributorRepository.class);
        mapper = mock(DistributorMapper.class);
        service = new DistributorServiceImpl();

        // Use reflection to set the mocked dependencies
        try {
            java.lang.reflect.Field repoField = DistributorServiceImpl.class.getDeclaredField("repository");
            repoField.setAccessible(true);
            repoField.set(service, repository);

            java.lang.reflect.Field mapperField = DistributorServiceImpl.class.getDeclaredField("mapper");
            mapperField.setAccessible(true);
            mapperField.set(service, mapper);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up test", e);
        }

        // Initialize test data
        distributor = new Distributor();
        distributor.setId(1L);
        distributor.setName("Test Distributor");

        distributorDTO = new DistributorDTO();
        distributorDTO.setId(1L);
        distributorDTO.setName("Test Distributor");

        filterRequest = new FilterRequest<>();
    }

    @Test
    void createDistributor_ShouldCreateAndReturnDistributor() {
        // Arrange
        when(mapper.toEntity(any(DistributorDTO.class))).thenReturn(distributor);
        when(repository.save(any(Distributor.class))).thenReturn(Mono.just(distributor));
        when(mapper.toDTO(any(Distributor.class))).thenReturn(distributorDTO);

        // Act & Assert
        StepVerifier.create(service.createDistributor(distributorDTO))
                .expectNext(distributorDTO)
                .verifyComplete();

        // Verify
        verify(mapper).toEntity(distributorDTO);
        verify(repository).save(distributor);
        verify(mapper).toDTO(distributor);
    }

    @Test
    void updateDistributor_WhenDistributorExists_ShouldUpdateAndReturnDistributor() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.just(distributor));
        when(mapper.toEntity(any(DistributorDTO.class))).thenReturn(distributor);
        when(repository.save(any(Distributor.class))).thenReturn(Mono.just(distributor));
        when(mapper.toDTO(any(Distributor.class))).thenReturn(distributorDTO);

        // Act & Assert
        StepVerifier.create(service.updateDistributor(1L, distributorDTO))
                .expectNext(distributorDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(mapper).toEntity(distributorDTO);
        verify(repository).save(distributor);
        verify(mapper).toDTO(distributor);
    }

    @Test
    void updateDistributor_WhenDistributorDoesNotExist_ShouldReturnError() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.updateDistributor(1L, distributorDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Distributor not found with ID: 1"))
                .verify();

        // Verify
        verify(repository).findById(1L);
        verify(mapper, never()).toEntity(any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void deleteDistributor_WhenDistributorExists_ShouldDeleteDistributor() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.just(distributor));
        when(repository.deleteById(anyLong())).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDistributor(1L))
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void deleteDistributor_WhenDistributorDoesNotExist_ShouldReturnError() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDistributor(1L))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Distributor not found with ID: 1"))
                .verify();

        // Verify
        verify(repository).findById(1L);
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    void getDistributorById_WhenDistributorExists_ShouldReturnDistributor() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.just(distributor));
        when(mapper.toDTO(any(Distributor.class))).thenReturn(distributorDTO);

        // Act & Assert
        StepVerifier.create(service.getDistributorById(1L))
                .expectNext(distributorDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(mapper).toDTO(distributor);
    }

    @Test
    void getDistributorById_WhenDistributorDoesNotExist_ShouldReturnError() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getDistributorById(1L))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Distributor not found with ID: 1"))
                .verify();

        // Verify
        verify(repository).findById(1L);
        verify(mapper, never()).toDTO(any());
    }
}
