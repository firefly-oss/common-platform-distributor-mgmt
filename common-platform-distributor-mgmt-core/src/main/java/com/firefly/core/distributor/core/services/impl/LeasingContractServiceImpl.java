package com.firefly.core.distributor.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.distributor.core.mappers.LeasingContractMapper;
import com.firefly.core.distributor.core.services.LeasingContractService;
import com.firefly.core.distributor.core.services.ProductService;
import com.firefly.core.distributor.core.services.ShipmentService;
import com.firefly.core.distributor.interfaces.dtos.LeasingContractDTO;
import com.firefly.core.distributor.models.entities.LeasingContract;
import com.firefly.core.distributor.models.repositories.LeasingContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Implementation of the LeasingContractService interface.
 */
@Service
public class LeasingContractServiceImpl implements LeasingContractService {

    @Autowired
    private LeasingContractRepository leasingContractRepository;

    @Autowired
    private LeasingContractMapper leasingContractMapper;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private ProductService productService;

    @Override
    @Transactional
    public Mono<LeasingContractDTO> createLeasingContract(LeasingContractDTO leasingContractDTO) {
        LeasingContract leasingContract = leasingContractMapper.toEntity(leasingContractDTO);
        leasingContract.setCreatedAt(LocalDateTime.now());
        leasingContract.setIsActive(true);
        
        return leasingContractRepository.save(leasingContract)
                .map(leasingContractMapper::toDto)
                .flatMap(this::enrichLeasingContractDTO);
    }

    @Override
    @Transactional
    public Mono<LeasingContractDTO> updateLeasingContract(Long id, LeasingContractDTO leasingContractDTO) {
        return leasingContractRepository.findById(id)
                .flatMap(existingContract -> {
                    LeasingContract updatedContract = leasingContractMapper.toEntity(leasingContractDTO);
                    updatedContract.setId(id);
                    updatedContract.setCreatedAt(existingContract.getCreatedAt());
                    updatedContract.setCreatedBy(existingContract.getCreatedBy());
                    updatedContract.setUpdatedAt(LocalDateTime.now());
                    
                    return leasingContractRepository.save(updatedContract);
                })
                .map(leasingContractMapper::toDto)
                .flatMap(this::enrichLeasingContractDTO);
    }

    @Override
    @Transactional
    public Mono<Void> deleteLeasingContract(Long id) {
        return leasingContractRepository.deleteById(id);
    }

    @Override
    public Mono<LeasingContractDTO> getLeasingContractById(Long id) {
        return leasingContractRepository.findById(id)
                .map(leasingContractMapper::toDto)
                .flatMap(this::enrichLeasingContractDTO);
    }

    @Override
    public Mono<LeasingContractDTO> getLeasingContractByContractId(Long contractId) {
        return leasingContractRepository.findByContractId(contractId)
                .map(leasingContractMapper::toDto)
                .flatMap(this::enrichLeasingContractDTO);
    }

    @Override
    public Flux<LeasingContractDTO> getLeasingContractsByDistributorId(Long distributorId) {
        return leasingContractRepository.findByDistributorId(distributorId)
                .map(leasingContractMapper::toDto)
                .flatMap(this::enrichLeasingContractDTO);
    }

    @Override
    public Flux<LeasingContractDTO> getLeasingContractsByProductId(Long productId) {
        return leasingContractRepository.findByProductId(productId)
                .map(leasingContractMapper::toDto)
                .flatMap(this::enrichLeasingContractDTO);
    }

    @Override
    public Flux<LeasingContractDTO> getLeasingContractsByPartyId(Long partyId) {
        return leasingContractRepository.findByPartyId(partyId)
                .map(leasingContractMapper::toDto)
                .flatMap(this::enrichLeasingContractDTO);
    }

    @Override
    public Flux<LeasingContractDTO> getLeasingContractsByStatus(String status) {
        return leasingContractRepository.findByStatus(status)
                .map(leasingContractMapper::toDto)
                .flatMap(this::enrichLeasingContractDTO);
    }

    @Override
    @Transactional
    public Mono<LeasingContractDTO> approveLeasingContract(Long id, Long approvedBy) {
        return leasingContractRepository.findById(id)
                .flatMap(contract -> {
                    contract.setStatus("APPROVED");
                    contract.setApprovalDate(LocalDateTime.now());
                    contract.setApprovedBy(approvedBy);
                    contract.setUpdatedAt(LocalDateTime.now());
                    contract.setUpdatedBy(approvedBy);
                    
                    return leasingContractRepository.save(contract);
                })
                .map(leasingContractMapper::toDto)
                .flatMap(this::enrichLeasingContractDTO)
                .flatMap(approvedContract -> {
                    // Create a shipment for the approved contract
                    return shipmentService.createShipmentForApprovedContract(approvedContract)
                            .thenReturn(approvedContract);
                });
    }

    @Override
    public Mono<PaginationResponse<LeasingContractDTO>> filterLeasingContracts(FilterRequest<LeasingContractDTO> filterRequest) {
        // Simplified implementation - just return all contracts with basic pagination
        // In a real implementation, this would use proper filtering utilities
        
        return leasingContractRepository.findAll()
                .map(leasingContractMapper::toDto)
                .flatMap(this::enrichLeasingContractDTO)
                .collectList()
                .map(list -> {
                    // Create a simple pagination response with all items
                    // In a real implementation, proper pagination would be applied
                    return new PaginationResponse<>(list, list.size(), 0, list.size());
                });
    }
    
    /**
     * Enrich a LeasingContractDTO with related data.
     *
     * @param leasingContractDTO the LeasingContractDTO to enrich
     * @return the enriched LeasingContractDTO
     */
    private Mono<LeasingContractDTO> enrichLeasingContractDTO(LeasingContractDTO leasingContractDTO) {
        // Enrich with product data if productId is available
        if (leasingContractDTO.getProductId() != null) {
            return productService.getProductById(leasingContractDTO.getProductId())
                    .map(productDTO -> {
                        leasingContractDTO.setProduct(productDTO);
                        return leasingContractDTO;
                    })
                    .defaultIfEmpty(leasingContractDTO);
        }
        
        return Mono.just(leasingContractDTO);
    }
}