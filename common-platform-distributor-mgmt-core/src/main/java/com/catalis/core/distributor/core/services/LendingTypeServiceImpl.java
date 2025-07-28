package com.catalis.core.distributor.core.services;

import com.catalis.core.distributor.core.mappers.LendingTypeMapper;
import com.catalis.core.distributor.interfaces.dtos.LendingTypeDTO;
import com.catalis.core.distributor.models.entities.LendingType;
import com.catalis.core.distributor.models.repositories.LendingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Implementation of the LendingTypeService interface.
 */
@Service
@RequiredArgsConstructor
public class LendingTypeServiceImpl implements LendingTypeService {

    private final LendingTypeRepository lendingTypeRepository;
    private final LendingTypeMapper lendingTypeMapper;

    @Override
    public Flux<LendingTypeDTO> getAllLendingTypes() {
        return lendingTypeRepository.findAll()
                .map(lendingTypeMapper::toDto);
    }

    @Override
    public Flux<LendingTypeDTO> getActiveLendingTypes() {
        return lendingTypeRepository.findByIsActiveTrue()
                .map(lendingTypeMapper::toDto);
    }

    @Override
    public Mono<LendingTypeDTO> getLendingTypeById(Long id) {
        return lendingTypeRepository.findById(id)
                .map(lendingTypeMapper::toDto);
    }

    @Override
    public Mono<LendingTypeDTO> getLendingTypeByCode(String code) {
        return lendingTypeRepository.findByCode(code)
                .map(lendingTypeMapper::toDto);
    }

    @Override
    public Mono<LendingTypeDTO> createLendingType(LendingTypeDTO lendingTypeDTO) {
        LendingType lendingType = lendingTypeMapper.toEntity(lendingTypeDTO);
        lendingType.setCreatedAt(LocalDateTime.now());
        lendingType.setIsActive(true);
        
        return lendingTypeRepository.save(lendingType)
                .map(lendingTypeMapper::toDto);
    }

    @Override
    public Mono<LendingTypeDTO> updateLendingType(Long id, LendingTypeDTO lendingTypeDTO) {
        return lendingTypeRepository.findById(id)
                .flatMap(existingType -> {
                    LendingType updatedType = lendingTypeMapper.toEntity(lendingTypeDTO);
                    updatedType.setId(id);
                    updatedType.setCreatedAt(existingType.getCreatedAt());
                    updatedType.setCreatedBy(existingType.getCreatedBy());
                    updatedType.setUpdatedAt(LocalDateTime.now());
                    
                    return lendingTypeRepository.save(updatedType);
                })
                .map(lendingTypeMapper::toDto);
    }

    @Override
    public Mono<Void> deleteLendingType(Long id) {
        return lendingTypeRepository.deleteById(id);
    }
}