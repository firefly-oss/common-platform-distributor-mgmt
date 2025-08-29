package com.firefly.core.distributor.models.entities;
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
import java.time.LocalDateTime;

/**
 * Entity representing a lending configuration for a product.
 * Maps to the 'lending_configuration' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("lending_configuration")
public class LendingConfiguration {

    @Id
    private Long id;

    @Column("product_id")
    private Long productId;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("lending_type_id")
    private LendingType lendingType;

    @Column("min_term_months")
    private Integer minTermMonths;

    @Column("max_term_months")
    private Integer maxTermMonths;

    @Column("default_term_months")
    private Integer defaultTermMonths;

    @Column("min_down_payment_percentage")
    private BigDecimal minDownPaymentPercentage;

    @Column("default_down_payment_percentage")
    private BigDecimal defaultDownPaymentPercentage;

    @Column("interest_rate")
    private BigDecimal interestRate;

    @Column("processing_fee_percentage")
    private BigDecimal processingFeePercentage;

    @Column("early_termination_fee_percentage")
    private BigDecimal earlyTerminationFeePercentage;

    @Column("late_payment_fee_amount")
    private BigDecimal latePaymentFeeAmount;

    @Column("grace_period_days")
    private Integer gracePeriodDays;

    @Column("is_default")
    private Boolean isDefault;

    @Column("is_active")
    private Boolean isActive;

    @Column("terms_conditions")
    private String termsConditions;

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