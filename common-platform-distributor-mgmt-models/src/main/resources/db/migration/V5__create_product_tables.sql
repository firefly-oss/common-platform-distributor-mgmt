-- Create product table
CREATE TABLE IF NOT EXISTS product (
    id BIGSERIAL PRIMARY KEY,
    distributor_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    sku VARCHAR(100),
    model_number VARCHAR(100),
    manufacturer VARCHAR(255),
    category product_category_enum NOT NULL,
    image_url VARCHAR(255),
    specifications JSONB,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    CONSTRAINT fk_product_distributor
        FOREIGN KEY (distributor_id)
        REFERENCES distributor (id)
);

-- Create lending_configuration table
CREATE TABLE IF NOT EXISTS lending_configuration (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    lending_type lending_type_enum NOT NULL,
    min_term_months INTEGER,
    max_term_months INTEGER,
    default_term_months INTEGER,
    min_down_payment_percentage DECIMAL(5,2),
    default_down_payment_percentage DECIMAL(5,2),
    interest_rate DECIMAL(5,2),
    processing_fee_percentage DECIMAL(5,2),
    early_termination_fee_percentage DECIMAL(5,2),
    late_payment_fee_amount DECIMAL(10,2),
    grace_period_days INTEGER,
    is_default BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    terms_conditions TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    CONSTRAINT fk_lending_configuration_product
        FOREIGN KEY (product_id)
        REFERENCES product (id)
);

-- Create indexes
CREATE INDEX idx_product_distributor_id ON product(distributor_id);
CREATE INDEX idx_product_category ON product(category);
CREATE INDEX idx_product_sku ON product(sku);
CREATE INDEX idx_lending_configuration_product_id ON lending_configuration(product_id);
CREATE INDEX idx_lending_configuration_lending_type ON lending_configuration(lending_type);