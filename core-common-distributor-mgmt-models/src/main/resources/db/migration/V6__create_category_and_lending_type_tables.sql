-- Create product_category table
CREATE TABLE IF NOT EXISTS product_category (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID
);

-- Create lending_type table
CREATE TABLE IF NOT EXISTS lending_type (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID
);

-- Create indexes
CREATE INDEX idx_product_category_code ON product_category(code);
CREATE INDEX idx_product_category_is_active ON product_category(is_active);
CREATE INDEX idx_lending_type_code ON lending_type(code);
CREATE INDEX idx_lending_type_is_active ON lending_type(is_active);

-- Insert data from product_category_enum values
INSERT INTO product_category (id, name, code, description, is_active)
VALUES
    ('550e8400-e29b-41d4-a716-446655440001', 'Medical Equipment', 'MEDICAL_EQUIPMENT', 'Equipment used in medical and healthcare settings', TRUE),
    ('550e8400-e29b-41d4-a716-446655440002', 'Vehicle', 'VEHICLE', 'Automobiles, trucks, and other vehicles', TRUE),
    ('550e8400-e29b-41d4-a716-446655440003', 'Construction Equipment', 'CONSTRUCTION_EQUIPMENT', 'Equipment used in construction and building', TRUE),
    ('550e8400-e29b-41d4-a716-446655440004', 'Technology', 'TECHNOLOGY', 'Computers, servers, and other technology products', TRUE),
    ('550e8400-e29b-41d4-a716-446655440005', 'Office Equipment', 'OFFICE_EQUIPMENT', 'Equipment used in office settings', TRUE),
    ('550e8400-e29b-41d4-a716-446655440006', 'Industrial Machinery', 'INDUSTRIAL_MACHINERY', 'Machinery used in industrial settings', TRUE),
    ('550e8400-e29b-41d4-a716-446655440007', 'Other', 'OTHER', 'Other types of products not fitting into specific categories', TRUE);

-- Insert data from lending_type_enum values
INSERT INTO lending_type (id, name, code, description, is_active)
VALUES
    ('550e8400-e29b-41d4-a716-446655440011', 'Lease', 'LEASE', 'Traditional lease with fixed term', TRUE),
    ('550e8400-e29b-41d4-a716-446655440012', 'Rent', 'RENT', 'Short-term rental', TRUE),
    ('550e8400-e29b-41d4-a716-446655440013', 'Lease to Own', 'LEASE_TO_OWN', 'Lease with option to purchase at end of term', TRUE),
    ('550e8400-e29b-41d4-a716-446655440014', 'Finance', 'FINANCE', 'Direct financing option', TRUE),
    ('550e8400-e29b-41d4-a716-446655440015', 'Subscription', 'SUBSCRIPTION', 'Recurring payment model', TRUE),
    ('550e8400-e29b-41d4-a716-446655440016', 'Installment', 'INSTALLMENT', 'Payment in installments', TRUE);