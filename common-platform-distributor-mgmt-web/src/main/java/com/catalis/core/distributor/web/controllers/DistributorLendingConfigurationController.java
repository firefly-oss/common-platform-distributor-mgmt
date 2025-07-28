package com.catalis.core.distributor.web.controllers;

import com.catalis.core.distributor.core.services.LendingConfigurationService;
import com.catalis.core.distributor.interfaces.dtos.LendingConfigurationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing distributor-level lending configurations.
 */
@RestController
@RequestMapping("/api/v1/distributors/{distributorId}/lending-configurations")
public class DistributorLendingConfigurationController {

    @Autowired
    private LendingConfigurationService lendingConfigurationService;

    /**
     * GET /api/v1/distributors/{distributorId}/lending-configurations : Get all lending configurations for a distributor
     *
     * @param distributorId the ID of the distributor
     * @return the ResponseEntity with status 200 (OK) and with body the list of lending configurations
     */
    @GetMapping
    public Mono<ResponseEntity<Flux<LendingConfigurationDTO>>> getLendingConfigurationsByDistributorId(
            @PathVariable Long distributorId) {
        
        return Mono.just(ResponseEntity.ok(lendingConfigurationService.getLendingConfigurationsByDistributorId(distributorId)));
    }
}