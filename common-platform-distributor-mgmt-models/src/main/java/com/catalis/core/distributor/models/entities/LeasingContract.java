package com.catalis.core.distributor.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing a leasing contract between a customer and a distributor.
 * Maps to the 'leasing_contract' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("leasing_contract")
public class LeasingContract {

    @Id
    private Long id;

    @Column("contract_id")
    private Long contractId;

    @Column("party_id")
    private Long partyId;

    @Column("distributor_id")
    private Long distributorId;

    @Column("product_id")
    private Long productId;

    @Column("lending_configuration_id")
    private Long lendingConfigurationId;

    @Column("start_date")
    private LocalDate startDate;

    @Column("end_date")
    private LocalDate endDate;

    @Column("monthly_payment")
    private BigDecimal monthlyPayment;

    @Column("down_payment")
    private BigDecimal downPayment;

    @Column("total_amount")
    private BigDecimal totalAmount;

    @Column("status")
    private String status;

    @Column("approval_date")
    private LocalDateTime approvalDate;

    @Column("approved_by")
    private Long approvedBy;

    @Column("terms_conditions")
    private String termsConditions;

    @Column("notes")
    private String notes;

    @Column("is_active")
    private Boolean isActive;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("created_by")
    private Long createdBy;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("updated_by")
    private Long updatedBy;
}