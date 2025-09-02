-- Create leasing_contract table
CREATE TABLE IF NOT EXISTS leasing_contract (
    id UUID PRIMARY KEY,
    contract_id UUID NOT NULL,
    party_id UUID NOT NULL,
    distributor_id UUID NOT NULL,
    product_id UUID NOT NULL,
    lending_configuration_id UUID NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    monthly_payment DECIMAL(10,2) NOT NULL,
    down_payment DECIMAL(10,2) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    approval_date TIMESTAMP,
    approved_by UUID,
    terms_conditions TEXT,
    notes TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID,
    CONSTRAINT fk_leasing_contract_distributor
        FOREIGN KEY (distributor_id)
        REFERENCES distributor (id),
    CONSTRAINT fk_leasing_contract_product
        FOREIGN KEY (product_id)
        REFERENCES product (id),
    CONSTRAINT fk_leasing_contract_lending_configuration
        FOREIGN KEY (lending_configuration_id)
        REFERENCES lending_configuration (id)
);

-- Create shipment table
CREATE TABLE IF NOT EXISTS shipment (
    id UUID PRIMARY KEY,
    leasing_contract_id UUID NOT NULL,
    product_id UUID NOT NULL,
    tracking_number VARCHAR(100),
    carrier VARCHAR(100),
    shipping_address TEXT NOT NULL,
    shipping_date TIMESTAMP,
    estimated_delivery_date TIMESTAMP,
    actual_delivery_date TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID,
    CONSTRAINT fk_shipment_leasing_contract
        FOREIGN KEY (leasing_contract_id)
        REFERENCES leasing_contract (id),
    CONSTRAINT fk_shipment_product
        FOREIGN KEY (product_id)
        REFERENCES product (id)
);

-- Create indexes
CREATE INDEX idx_leasing_contract_contract_id ON leasing_contract(contract_id);
CREATE INDEX idx_leasing_contract_party_id ON leasing_contract(party_id);
CREATE INDEX idx_leasing_contract_distributor_id ON leasing_contract(distributor_id);
CREATE INDEX idx_leasing_contract_product_id ON leasing_contract(product_id);
CREATE INDEX idx_leasing_contract_lending_configuration_id ON leasing_contract(lending_configuration_id);
CREATE INDEX idx_leasing_contract_status ON leasing_contract(status);
CREATE INDEX idx_leasing_contract_is_active ON leasing_contract(is_active);

CREATE INDEX idx_shipment_leasing_contract_id ON shipment(leasing_contract_id);
CREATE INDEX idx_shipment_product_id ON shipment(product_id);
CREATE INDEX idx_shipment_tracking_number ON shipment(tracking_number);
CREATE INDEX idx_shipment_status ON shipment(status);