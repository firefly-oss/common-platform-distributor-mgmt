package com.firefly.core.distributor.models.repositories;

import com.firefly.core.distributor.models.entities.DistributorTermsAndConditions;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Repository interface for managing {@link DistributorTermsAndConditions} entities.
 * Extends {@link BaseRepository} to inherit common CRUD operations.
 */
@Repository
public interface DistributorTermsAndConditionsRepository extends BaseRepository<DistributorTermsAndConditions, Long> {
    
    /**
     * Find all terms and conditions for a specific distributor.
     *
     * @param distributorId the distributor ID to search for
     * @return a Flux of distributor terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByDistributorId(Long distributorId);
    
    /**
     * Find all active terms and conditions for a specific distributor.
     *
     * @param distributorId the distributor ID to search for
     * @return a Flux of active distributor terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByDistributorIdAndIsActiveTrue(Long distributorId);
    
    /**
     * Find terms and conditions by status.
     *
     * @param status the status to search for
     * @return a Flux of distributor terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByStatus(String status);
    
    /**
     * Find terms and conditions by distributor and status.
     *
     * @param distributorId the distributor ID
     * @param status the status
     * @return a Flux of distributor terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByDistributorIdAndStatus(Long distributorId, String status);
    
    /**
     * Find terms and conditions by template ID.
     *
     * @param templateId the template ID
     * @return a Flux of distributor terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByTemplateId(Long templateId);
    
    /**
     * Find active terms and conditions for a distributor and template.
     *
     * @param distributorId the distributor ID
     * @param templateId the template ID
     * @return a Mono containing the active terms and conditions if found
     */
    Mono<DistributorTermsAndConditions> findByDistributorIdAndTemplateIdAndIsActiveTrue(Long distributorId, Long templateId);
    
    /**
     * Find terms and conditions expiring before a certain date.
     *
     * @param expirationDate the expiration date threshold
     * @return a Flux of expiring terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByExpirationDateBeforeAndIsActiveTrue(LocalDateTime expirationDate);
    
    /**
     * Find terms and conditions effective after a certain date.
     *
     * @param effectiveDate the effective date threshold
     * @return a Flux of terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByEffectiveDateAfter(LocalDateTime effectiveDate);
    
    /**
     * Find signed terms and conditions for a distributor.
     *
     * @param distributorId the distributor ID
     * @return a Flux of signed terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByDistributorIdAndStatusAndIsActiveTrue(Long distributorId, String status);
    
    /**
     * Check if a distributor has active terms and conditions.
     *
     * @param distributorId the distributor ID
     * @return a Mono containing true if active terms exist
     */
    Mono<Boolean> existsByDistributorIdAndStatusAndIsActiveTrue(Long distributorId, String status);
    
    /**
     * Find terms and conditions by version.
     *
     * @param version the version to search for
     * @return a Flux of terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByVersion(String version);
    
    /**
     * Find the latest version of terms and conditions for a distributor.
     *
     * @param distributorId the distributor ID
     * @return a Mono containing the latest terms and conditions
     */
    Mono<DistributorTermsAndConditions> findTopByDistributorIdAndIsActiveTrueOrderByCreatedAtDesc(Long distributorId);
}
