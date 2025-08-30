package com.firefly.core.distributor.core.services;

import com.firefly.core.distributor.core.mappers.DistributorTermsAndConditionsMapper;
import com.firefly.core.distributor.core.services.impl.DistributorTermsAndConditionsServiceImpl;
import com.firefly.core.distributor.interfaces.dtos.DistributorTermsAndConditionsDTO;
import com.firefly.core.distributor.models.entities.DistributorTermsAndConditions;
import com.firefly.core.distributor.models.repositories.DistributorTermsAndConditionsRepository;
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

public class DistributorTermsAndConditionsServiceImplTest {

    private DistributorTermsAndConditionsRepository repository;
    private DistributorTermsAndConditionsMapper mapper;
    private DistributorTermsAndConditionsServiceImpl service;

    private DistributorTermsAndConditions distributorTermsAndConditions;
    private DistributorTermsAndConditionsDTO distributorTermsAndConditionsDTO;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        repository = mock(DistributorTermsAndConditionsRepository.class);
        mapper = mock(DistributorTermsAndConditionsMapper.class);
        service = new DistributorTermsAndConditionsServiceImpl();

        // Use reflection to set the mocked dependencies
        try {
            java.lang.reflect.Field repoField = DistributorTermsAndConditionsServiceImpl.class.getDeclaredField("repository");
            repoField.setAccessible(true);
            repoField.set(service, repository);

            java.lang.reflect.Field mapperField = DistributorTermsAndConditionsServiceImpl.class.getDeclaredField("mapper");
            mapperField.setAccessible(true);
            mapperField.set(service, mapper);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up test", e);
        }

        // Initialize test data
        distributorTermsAndConditions = DistributorTermsAndConditions.builder()
                .id(1L)
                .distributorId(1L)
                .templateId(1L)
                .title("Test Terms and Conditions")
                .content("Test content")
                .version("1.0")
                .effectiveDate(LocalDateTime.now())
                .status("DRAFT")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        distributorTermsAndConditionsDTO = DistributorTermsAndConditionsDTO.builder()
                .id(1L)
                .distributorId(1L)
                .templateId(1L)
                .title("Test Terms and Conditions")
                .content("Test content")
                .version("1.0")
                .effectiveDate(LocalDateTime.now())
                .status("DRAFT")
                .isActive(true)
                .build();
    }

    @Test
    void createDistributorTermsAndConditions_ShouldCreateAndReturnTermsAndConditions() {
        // Arrange
        when(mapper.toEntity(any(DistributorTermsAndConditionsDTO.class))).thenReturn(distributorTermsAndConditions);
        when(repository.save(any(DistributorTermsAndConditions.class))).thenReturn(Mono.just(distributorTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.createDistributorTermsAndConditions(distributorTermsAndConditionsDTO))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(mapper).toEntity(distributorTermsAndConditionsDTO);
        verify(repository).save(any(DistributorTermsAndConditions.class));
        verify(mapper).toDTO(distributorTermsAndConditions);
    }

    @Test
    void updateDistributorTermsAndConditions_WhenTermsAndConditionsExists_ShouldUpdateAndReturnTermsAndConditions() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.just(distributorTermsAndConditions));
        when(mapper.toEntity(any(DistributorTermsAndConditionsDTO.class))).thenReturn(distributorTermsAndConditions);
        when(repository.save(any(DistributorTermsAndConditions.class))).thenReturn(Mono.just(distributorTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.updateDistributorTermsAndConditions(1L, distributorTermsAndConditionsDTO))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(mapper).toEntity(distributorTermsAndConditionsDTO);
        verify(repository).save(any(DistributorTermsAndConditions.class));
        verify(mapper).toDTO(distributorTermsAndConditions);
    }

    @Test
    void getDistributorTermsAndConditionsById_WhenTermsAndConditionsExists_ShouldReturnTermsAndConditions() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.just(distributorTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.getDistributorTermsAndConditionsById(1L))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(mapper).toDTO(distributorTermsAndConditions);
    }

    @Test
    void getDistributorTermsAndConditionsById_WhenTermsAndConditionsDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getDistributorTermsAndConditionsById(1L))
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void getTermsAndConditionsByDistributorId_ShouldReturnTermsAndConditions() {
        // Arrange
        when(repository.findByDistributorId(anyLong())).thenReturn(Flux.just(distributorTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.getTermsAndConditionsByDistributorId(1L))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByDistributorId(1L);
        verify(mapper).toDTO(distributorTermsAndConditions);
    }

    @Test
    void getActiveTermsAndConditionsByDistributorId_ShouldReturnActiveTermsAndConditions() {
        // Arrange
        when(repository.findByDistributorIdAndIsActiveTrue(anyLong())).thenReturn(Flux.just(distributorTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.getActiveTermsAndConditionsByDistributorId(1L))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByDistributorIdAndIsActiveTrue(1L);
        verify(mapper).toDTO(distributorTermsAndConditions);
    }

    @Test
    void getTermsAndConditionsByStatus_ShouldReturnTermsAndConditionsWithStatus() {
        // Arrange
        when(repository.findByStatus(anyString())).thenReturn(Flux.just(distributorTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.getTermsAndConditionsByStatus("DRAFT"))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByStatus("DRAFT");
        verify(mapper).toDTO(distributorTermsAndConditions);
    }

    @Test
    void updateStatus_WhenTermsAndConditionsExists_ShouldUpdateStatusAndReturnTermsAndConditions() {
        // Arrange
        DistributorTermsAndConditions updatedTermsAndConditions = DistributorTermsAndConditions.builder()
                .id(1L)
                .distributorId(1L)
                .templateId(1L)
                .title("Test Terms and Conditions")
                .content("Test content")
                .version("1.0")
                .effectiveDate(LocalDateTime.now())
                .status("SIGNED")
                .isActive(true)
                .build();

        when(repository.findById(anyLong())).thenReturn(Mono.just(distributorTermsAndConditions));
        when(repository.save(any(DistributorTermsAndConditions.class))).thenReturn(Mono.just(updatedTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.updateStatus(1L, "SIGNED", 1L))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(repository).save(any(DistributorTermsAndConditions.class));
        verify(mapper).toDTO(updatedTermsAndConditions);
    }

    @Test
    void signTermsAndConditions_WhenTermsAndConditionsExists_ShouldSignAndReturnTermsAndConditions() {
        // Arrange
        DistributorTermsAndConditions signedTermsAndConditions = DistributorTermsAndConditions.builder()
                .id(1L)
                .distributorId(1L)
                .templateId(1L)
                .title("Test Terms and Conditions")
                .content("Test content")
                .version("1.0")
                .effectiveDate(LocalDateTime.now())
                .status("SIGNED")
                .signedDate(LocalDateTime.now())
                .signedBy(1L)
                .isActive(true)
                .build();

        when(repository.findById(anyLong())).thenReturn(Mono.just(distributorTermsAndConditions));
        when(repository.save(any(DistributorTermsAndConditions.class))).thenReturn(Mono.just(signedTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.signTermsAndConditions(1L, 1L))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(repository).save(any(DistributorTermsAndConditions.class));
        verify(mapper).toDTO(signedTermsAndConditions);
    }

    @Test
    void hasActiveSignedTerms_WhenActiveSignedTermsExist_ShouldReturnTrue() {
        // Arrange
        when(repository.existsByDistributorIdAndStatusAndIsActiveTrue(anyLong(), anyString())).thenReturn(Mono.just(true));

        // Act & Assert
        StepVerifier.create(service.hasActiveSignedTerms(1L))
                .expectNext(true)
                .verifyComplete();

        // Verify
        verify(repository).existsByDistributorIdAndStatusAndIsActiveTrue(1L, "SIGNED");
    }

    @Test
    void hasActiveSignedTerms_WhenNoActiveSignedTermsExist_ShouldReturnFalse() {
        // Arrange
        when(repository.existsByDistributorIdAndStatusAndIsActiveTrue(anyLong(), anyString())).thenReturn(Mono.just(false));

        // Act & Assert
        StepVerifier.create(service.hasActiveSignedTerms(1L))
                .expectNext(false)
                .verifyComplete();

        // Verify
        verify(repository).existsByDistributorIdAndStatusAndIsActiveTrue(1L, "SIGNED");
    }

    @Test
    void getLatestTermsAndConditions_WhenTermsAndConditionsExists_ShouldReturnLatestTermsAndConditions() {
        // Arrange
        when(repository.findTopByDistributorIdAndIsActiveTrueOrderByCreatedAtDesc(anyLong())).thenReturn(Mono.just(distributorTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.getLatestTermsAndConditions(1L))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findTopByDistributorIdAndIsActiveTrueOrderByCreatedAtDesc(1L);
        verify(mapper).toDTO(distributorTermsAndConditions);
    }

    @Test
    void activateTermsAndConditions_WhenTermsAndConditionsExists_ShouldActivateAndReturnTermsAndConditions() {
        // Arrange
        DistributorTermsAndConditions inactiveTermsAndConditions = DistributorTermsAndConditions.builder()
                .id(1L)
                .distributorId(1L)
                .templateId(1L)
                .title("Test Terms and Conditions")
                .content("Test content")
                .version("1.0")
                .effectiveDate(LocalDateTime.now())
                .status("DRAFT")
                .isActive(false)
                .build();

        DistributorTermsAndConditions activeTermsAndConditions = DistributorTermsAndConditions.builder()
                .id(1L)
                .distributorId(1L)
                .templateId(1L)
                .title("Test Terms and Conditions")
                .content("Test content")
                .version("1.0")
                .effectiveDate(LocalDateTime.now())
                .status("DRAFT")
                .isActive(true)
                .build();

        when(repository.findById(anyLong())).thenReturn(Mono.just(inactiveTermsAndConditions));
        when(repository.save(any(DistributorTermsAndConditions.class))).thenReturn(Mono.just(activeTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.activateTermsAndConditions(1L, 1L))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(1L);
        verify(repository).save(any(DistributorTermsAndConditions.class));
        verify(mapper).toDTO(activeTermsAndConditions);
    }

    @Test
    void deleteDistributorTermsAndConditions_ShouldDeleteTermsAndConditions() {
        // Arrange
        when(repository.deleteById(anyLong())).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDistributorTermsAndConditions(1L))
                .verifyComplete();

        // Verify
        verify(repository).deleteById(1L);
    }
}
