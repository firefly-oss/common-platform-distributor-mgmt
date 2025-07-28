-- Create product_category table
CREATE TABLE IF NOT EXISTS product_category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT
);

-- Create lending_type table
CREATE TABLE IF NOT EXISTS lending_type (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT
);

-- Create indexes
CREATE INDEX idx_product_category_code ON product_category(code);
CREATE INDEX idx_product_category_is_active ON product_category(is_active);
CREATE INDEX idx_lending_type_code ON lending_type(code);
CREATE INDEX idx_lending_type_is_active ON lending_type(is_active);

-- Insert data from product_category_enum values
INSERT INTO product_category (name, code, description, is_active)
VALUES 
    ('Medical Equipment', 'MEDICAL_EQUIPMENT', 'Equipment used in medical and healthcare settings', TRUE),
    ('Vehicle', 'VEHICLE', 'Automobiles, trucks, and other vehicles', TRUE),
    ('Construction Equipment', 'CONSTRUCTION_EQUIPMENT', 'Equipment used in construction and building', TRUE),
    ('Technology', 'TECHNOLOGY', 'Computers, servers, and other technology products', TRUE),
    ('Office Equipment', 'OFFICE_EQUIPMENT', 'Equipment used in office settings', TRUE),
    ('Industrial Machinery', 'INDUSTRIAL_MACHINERY', 'Machinery used in industrial settings', TRUE),
    ('Other', 'OTHER', 'Other types of products not fitting into specific categories', TRUE);

-- Insert data from lending_type_enum values
INSERT INTO lending_type (name, code, description, is_active)
VALUES 
    ('Lease', 'LEASE', 'Traditional lease with fixed term', TRUE),
    ('Rent', 'RENT', 'Short-term rental', TRUE),
    ('Lease to Own', 'LEASE_TO_OWN', 'Lease with option to purchase at end of term', TRUE),
    ('Finance', 'FINANCE', 'Direct financing option', TRUE),
    ('Subscription', 'SUBSCRIPTION', 'Recurring payment model', TRUE),
    ('Installment', 'INSTALLMENT', 'Payment in installments', TRUE);