package com.firefly.core.distributor.models.repositories;

import com.firefly.core.distributor.models.entities.TermsAndConditionsTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository interface for managing {@link TermsAndConditionsTemplate} entities.
 * Extends {@link BaseRepository} to inherit common CRUD operations.
 */
@Repository
public interface TermsAndConditionsTemplateRepository extends BaseRepository<TermsAndConditionsTemplate, UUID> {
    
    /**
     * Find all active templates.
     *
     * @return a Flux of active templates
     */
    Flux<TermsAndConditionsTemplate> findByIsActiveTrue();
    
    /**
     * Find templates by category.
     *
     * @param category the category to search for
     * @return a Flux of templates
     */
    Flux<TermsAndConditionsTemplate> findByCategory(String category);
    
    /**
     * Find active templates by category.
     *
     * @param category the category to search for
     * @return a Flux of active templates
     */
    Flux<TermsAndConditionsTemplate> findByCategoryAndIsActiveTrue(String category);
    
    /**
     * Find template by name.
     *
     * @param name the template name
     * @return a Mono containing the template if found
     */
    Mono<TermsAndConditionsTemplate> findByName(String name);
    
    /**
     * Find active template by name.
     *
     * @param name the template name
     * @return a Mono containing the active template if found
     */
    Mono<TermsAndConditionsTemplate> findByNameAndIsActiveTrue(String name);
    
    /**
     * Find templates by version.
     *
     * @param version the version to search for
     * @return a Flux of templates
     */
    Flux<TermsAndConditionsTemplate> findByVersion(String version);
    
    /**
     * Find default templates.
     *
     * @return a Flux of default templates
     */
    Flux<TermsAndConditionsTemplate> findByIsDefaultTrue();
    
    /**
     * Find active default templates.
     *
     * @return a Flux of active default templates
     */
    Flux<TermsAndConditionsTemplate> findByIsDefaultTrueAndIsActiveTrue();
    
    /**
     * Find default template by category.
     *
     * @param category the category
     * @return a Mono containing the default template for the category
     */
    Mono<TermsAndConditionsTemplate> findByCategoryAndIsDefaultTrueAndIsActiveTrue(String category);
    
    /**
     * Find templates that require approval.
     *
     * @return a Flux of templates requiring approval
     */
    Flux<TermsAndConditionsTemplate> findByApprovalRequiredTrue();
    
    /**
     * Find templates with auto-renewal enabled.
     *
     * @return a Flux of auto-renewal templates
     */
    Flux<TermsAndConditionsTemplate> findByAutoRenewalTrue();
    
    /**
     * Find templates by renewal period.
     *
     * @param renewalPeriodMonths the renewal period in months
     * @return a Flux of templates
     */
    Flux<TermsAndConditionsTemplate> findByRenewalPeriodMonths(Integer renewalPeriodMonths);
    
    /**
     * Check if a template name already exists.
     *
     * @param name the template name
     * @return a Mono containing true if the name exists
     */
    Mono<Boolean> existsByName(String name);
    
    /**
     * Check if a template name exists for a different template ID.
     *
     * @param name the template name
     * @param id the template ID to exclude
     * @return a Mono containing true if the name exists for a different template
     */
    Mono<Boolean> existsByNameAndIdNot(String name, UUID id);
}
