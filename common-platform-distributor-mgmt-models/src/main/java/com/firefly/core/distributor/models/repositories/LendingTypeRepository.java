package com.firefly.core.distributor.models.repositories;

import com.firefly.core.distributor.models.entities.LendingType;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing LendingType entities.
 */
@Repository
public interface LendingTypeRepository extends BaseRepository<LendingType, UUID> {

    /**
     * Find a lending type by its code.
     *
     * @param code The unique code of the lending type
     * @return A Mono containing the lending type if found
     */
    Mono<LendingType> findByCode(String code);

    /**
     * Find all active lending types.
     *
     * @return A Flux of active lending types
     */
    Flux<LendingType> findByIsActiveTrue();

    /**
     * Find a lending type by its name.
     *
     * @param name The name of the lending type
     * @return A Mono containing the lending type if found
     */
    Mono<LendingType> findByName(String name);
}