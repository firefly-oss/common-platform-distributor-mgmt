package com.firefly.core.distributor.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.distributor.interfaces.dtos.TermsAndConditionsTemplateDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing terms and conditions templates.
 */
public interface TermsAndConditionsTemplateService {

    /**
     * Filters the terms and conditions templates based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list
     */
    Mono<PaginationResponse<TermsAndConditionsTemplateDTO>> filterTermsAndConditionsTemplates(FilterRequest<TermsAndConditionsTemplateDTO> filterRequest);

    /**
     * Creates a new terms and conditions template.
     *
     * @param templateDTO the template to create
     * @return a reactive {@code Mono} emitting the created template
     */
    Mono<TermsAndConditionsTemplateDTO> createTemplate(TermsAndConditionsTemplateDTO templateDTO);

    /**
     * Updates an existing terms and conditions template.
     *
     * @param id the ID of the template to update
     * @param templateDTO the updated template data
     * @return a reactive {@code Mono} emitting the updated template
     */
    Mono<TermsAndConditionsTemplateDTO> updateTemplate(Long id, TermsAndConditionsTemplateDTO templateDTO);

    /**
     * Deletes a terms and conditions template by its ID.
     *
     * @param id the ID of the template to delete
     * @return a reactive {@code Mono} that completes when the template is deleted
     */
    Mono<Void> deleteTemplate(Long id);

    /**
     * Retrieves a terms and conditions template by its ID.
     *
     * @param id the ID of the template to retrieve
     * @return a reactive {@code Mono} emitting the template if found
     */
    Mono<TermsAndConditionsTemplateDTO> getTemplateById(Long id);

    /**
     * Retrieves all active templates.
     *
     * @return a reactive {@code Flux} emitting all active templates
     */
    Flux<TermsAndConditionsTemplateDTO> getActiveTemplates();

    /**
     * Retrieves templates by category.
     *
     * @param category the category
     * @return a reactive {@code Flux} emitting templates in the specified category
     */
    Flux<TermsAndConditionsTemplateDTO> getTemplatesByCategory(String category);

    /**
     * Retrieves active templates by category.
     *
     * @param category the category
     * @return a reactive {@code Flux} emitting active templates in the specified category
     */
    Flux<TermsAndConditionsTemplateDTO> getActiveTemplatesByCategory(String category);

    /**
     * Retrieves a template by name.
     *
     * @param name the template name
     * @return a reactive {@code Mono} emitting the template if found
     */
    Mono<TermsAndConditionsTemplateDTO> getTemplateByName(String name);

    /**
     * Retrieves templates by version.
     *
     * @param version the version
     * @return a reactive {@code Flux} emitting templates with the specified version
     */
    Flux<TermsAndConditionsTemplateDTO> getTemplatesByVersion(String version);

    /**
     * Retrieves all default templates.
     *
     * @return a reactive {@code Flux} emitting all default templates
     */
    Flux<TermsAndConditionsTemplateDTO> getDefaultTemplates();

    /**
     * Retrieves active default templates.
     *
     * @return a reactive {@code Flux} emitting all active default templates
     */
    Flux<TermsAndConditionsTemplateDTO> getActiveDefaultTemplates();

    /**
     * Retrieves the default template for a specific category.
     *
     * @param category the category
     * @return a reactive {@code Mono} emitting the default template for the category
     */
    Mono<TermsAndConditionsTemplateDTO> getDefaultTemplateByCategory(String category);

    /**
     * Retrieves templates that require approval.
     *
     * @return a reactive {@code Flux} emitting templates requiring approval
     */
    Flux<TermsAndConditionsTemplateDTO> getTemplatesRequiringApproval();

    /**
     * Retrieves templates with auto-renewal enabled.
     *
     * @return a reactive {@code Flux} emitting auto-renewal templates
     */
    Flux<TermsAndConditionsTemplateDTO> getAutoRenewalTemplates();

    /**
     * Activates a template.
     *
     * @param id the ID of the template to activate
     * @param updatedBy the ID of the user performing the update
     * @return a reactive {@code Mono} emitting the updated template
     */
    Mono<TermsAndConditionsTemplateDTO> activateTemplate(Long id, Long updatedBy);

    /**
     * Deactivates a template.
     *
     * @param id the ID of the template to deactivate
     * @param updatedBy the ID of the user performing the update
     * @return a reactive {@code Mono} emitting the updated template
     */
    Mono<TermsAndConditionsTemplateDTO> deactivateTemplate(Long id, Long updatedBy);

    /**
     * Sets a template as default for its category.
     *
     * @param id the ID of the template to set as default
     * @param updatedBy the ID of the user performing the update
     * @return a reactive {@code Mono} emitting the updated template
     */
    Mono<TermsAndConditionsTemplateDTO> setAsDefault(Long id, Long updatedBy);

    /**
     * Removes default status from a template.
     *
     * @param id the ID of the template to remove default status from
     * @param updatedBy the ID of the user performing the update
     * @return a reactive {@code Mono} emitting the updated template
     */
    Mono<TermsAndConditionsTemplateDTO> removeDefault(Long id, Long updatedBy);

    /**
     * Checks if a template name already exists.
     *
     * @param name the template name
     * @return a reactive {@code Mono} emitting true if the name exists
     */
    Mono<Boolean> templateNameExists(String name);

    /**
     * Checks if a template name exists for a different template ID.
     *
     * @param name the template name
     * @param excludeId the template ID to exclude from the check
     * @return a reactive {@code Mono} emitting true if the name exists for a different template
     */
    Mono<Boolean> templateNameExistsForDifferentTemplate(String name, Long excludeId);
}
