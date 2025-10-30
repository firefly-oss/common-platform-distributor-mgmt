-- Create contract status enum
CREATE TYPE contract_status_enum AS ENUM (
    'DRAFT',
    'PENDING_APPROVAL',
    'APPROVED',
    'ACTIVE',
    'SUSPENDED',
    'TERMINATED',
    'EXPIRED',
    'RENEWED'
);

-- Create distributor_contract table
CREATE TABLE IF NOT EXISTS distributor_contract (
    id UUID PRIMARY KEY,
    distributor_id UUID NOT NULL,
    contract_number VARCHAR(100) UNIQUE NOT NULL,
    contract_type VARCHAR(100) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status contract_status_enum NOT NULL DEFAULT 'DRAFT',
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    renewal_date DATE,
    notice_period_days INTEGER DEFAULT 30,
    auto_renewal BOOLEAN DEFAULT FALSE,
    contract_value DECIMAL(15, 2),
    currency_code VARCHAR(3),
    payment_terms TEXT,
    special_terms TEXT,
    financial_conditions JSONB,
    product_conditions JSONB,
    service_level_agreements JSONB,
    metadata JSONB,
    signed_date DATE,
    signed_by UUID,
    approved_date DATE,
    approved_by UUID,
    terminated_date DATE,
    terminated_by UUID,
    termination_reason TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID,
    CONSTRAINT fk_distributor_contract_distributor
        FOREIGN KEY (distributor_id)
        REFERENCES distributor (id)
        ON DELETE CASCADE
);

-- Create indexes for distributor_contract table
CREATE INDEX idx_distributor_contract_distributor_id ON distributor_contract(distributor_id);
CREATE INDEX idx_distributor_contract_contract_number ON distributor_contract(contract_number);
CREATE INDEX idx_distributor_contract_contract_type ON distributor_contract(contract_type);
CREATE INDEX idx_distributor_contract_status ON distributor_contract(status);
CREATE INDEX idx_distributor_contract_start_date ON distributor_contract(start_date);
CREATE INDEX idx_distributor_contract_end_date ON distributor_contract(end_date);
CREATE INDEX idx_distributor_contract_is_active ON distributor_contract(is_active);
CREATE INDEX idx_distributor_contract_signed_date ON distributor_contract(signed_date);
