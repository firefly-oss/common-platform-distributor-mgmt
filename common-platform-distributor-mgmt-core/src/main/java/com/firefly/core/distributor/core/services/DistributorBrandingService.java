package com.firefly.core.distributor.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.distributor.interfaces.dtos.DistributorBrandingDTO;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing distributor branding.
 */
public interface DistributorBrandingService {
    /**
     * Filters the distributor branding based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for DistributorBrandingDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of distributor branding
     */
    Mono<PaginationResponse<DistributorBrandingDTO>> filterDistributorBranding(FilterRequest<DistributorBrandingDTO> filterRequest);
    
    /**
     * Creates a new distributor branding based on the provided information.
     *
     * @param distributorBrandingDTO the DTO object containing details of the distributor branding to be created
     * @return a Mono that emits the created DistributorBrandingDTO object
     */
    Mono<DistributorBrandingDTO> createDistributorBranding(DistributorBrandingDTO distributorBrandingDTO);
    
    /**
     * Updates an existing distributor branding with updated information.
     *
     * @param distributorBrandingId the unique identifier of the distributor branding to be updated
     * @param distributorBrandingDTO the data transfer object containing the updated details of the distributor branding
     * @return a reactive Mono containing the updated DistributorBrandingDTO
     */
    Mono<DistributorBrandingDTO> updateDistributorBranding(Long distributorBrandingId, DistributorBrandingDTO distributorBrandingDTO);
    
    /**
     * Deletes a distributor branding identified by its unique ID.
     *
     * @param distributorBrandingId the unique identifier of the distributor branding to be deleted
     * @return a Mono that completes when the distributor branding is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteDistributorBranding(Long distributorBrandingId);
    
    /**
     * Retrieves a distributor branding by its unique identifier.
     *
     * @param distributorBrandingId the unique identifier of the distributor branding to retrieve
     * @return a Mono emitting the {@link DistributorBrandingDTO} representing the distributor branding if found,
     *         or an empty Mono if the distributor branding does not exist
     */
    Mono<DistributorBrandingDTO> getDistributorBrandingById(Long distributorBrandingId);
}