-- Migration V26: Add territory authorization and payment methods
-- This migration adds:
-- 1. distributor_authorized_territory table for geographic authorization control
-- 2. agency_payment_method table for disbursement management
-- 3. Fiscal information fields to distributor_agency table
-- 4. Change country_code to country_id in distributor and distributor_agent tables

-- =====================================================
-- 1. Create distributor_authorized_territory table
-- =====================================================
CREATE TABLE IF NOT EXISTS distributor_authorized_territory (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    distributor_id UUID NOT NULL REFERENCES distributor(id) ON DELETE CASCADE,
    country_id UUID NOT NULL,
    administrative_division_id UUID,
    authorization_level VARCHAR(50),
    is_active BOOLEAN DEFAULT true,
    authorized_from TIMESTAMP,
    authorized_until TIMESTAMP,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID
);

-- Create indexes for distributor_authorized_territory
CREATE INDEX idx_distributor_authorized_territory_distributor_id 
    ON distributor_authorized_territory(distributor_id);
CREATE INDEX idx_distributor_authorized_territory_country_id 
    ON distributor_authorized_territory(country_id);
CREATE INDEX idx_distributor_authorized_territory_active 
    ON distributor_authorized_territory(is_active);
CREATE UNIQUE INDEX idx_distributor_authorized_territory_unique 
    ON distributor_authorized_territory(distributor_id, country_id, administrative_division_id) 
    WHERE is_active = true;

-- =====================================================
-- 2. Create agency_payment_method table
-- =====================================================
CREATE TABLE IF NOT EXISTS agency_payment_method (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    agency_id UUID NOT NULL REFERENCES distributor_agency(id) ON DELETE CASCADE,
    payment_method_type VARCHAR(50) NOT NULL,
    payment_provider VARCHAR(100),
    account_holder_name VARCHAR(200),
    account_number VARCHAR(100),
    routing_number VARCHAR(50),
    swift_code VARCHAR(20),
    iban VARCHAR(50),
    bank_name VARCHAR(200),
    bank_branch VARCHAR(200),
    bank_address VARCHAR(500),
    currency_code VARCHAR(3),
    wallet_id VARCHAR(100),
    wallet_phone VARCHAR(20),
    wallet_email VARCHAR(100),
    is_primary BOOLEAN DEFAULT false,
    is_verified BOOLEAN DEFAULT false,
    verified_at TIMESTAMP,
    verified_by UUID,
    is_active BOOLEAN DEFAULT true,
    notes TEXT,
    metadata TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID
);

-- Create indexes for agency_payment_method
CREATE INDEX idx_agency_payment_method_agency_id 
    ON agency_payment_method(agency_id);
CREATE INDEX idx_agency_payment_method_type 
    ON agency_payment_method(payment_method_type);
CREATE INDEX idx_agency_payment_method_active 
    ON agency_payment_method(is_active);
CREATE INDEX idx_agency_payment_method_primary 
    ON agency_payment_method(agency_id, is_primary) 
    WHERE is_primary = true;

-- =====================================================
-- 3. Add fiscal information to distributor_agency
-- =====================================================
ALTER TABLE distributor_agency 
    ADD COLUMN IF NOT EXISTS legal_entity_name VARCHAR(255),
    ADD COLUMN IF NOT EXISTS legal_entity_type VARCHAR(50),
    ADD COLUMN IF NOT EXISTS tax_id VARCHAR(100),
    ADD COLUMN IF NOT EXISTS registration_number VARCHAR(100),
    ADD COLUMN IF NOT EXISTS vat_number VARCHAR(100),
    ADD COLUMN IF NOT EXISTS fiscal_address_line VARCHAR(255),
    ADD COLUMN IF NOT EXISTS fiscal_postal_code VARCHAR(20),
    ADD COLUMN IF NOT EXISTS fiscal_city VARCHAR(100),
    ADD COLUMN IF NOT EXISTS fiscal_state VARCHAR(100),
    ADD COLUMN IF NOT EXISTS fiscal_country_id UUID;

-- Create index for fiscal_country_id
CREATE INDEX idx_distributor_agency_fiscal_country_id 
    ON distributor_agency(fiscal_country_id);

-- =====================================================
-- 4. Change country_code to country_id in distributor
-- =====================================================
-- Add new country_id column
ALTER TABLE distributor 
    ADD COLUMN IF NOT EXISTS country_id UUID;

-- Create index for country_id
CREATE INDEX idx_distributor_country_id 
    ON distributor(country_id);

-- Drop old country_code column (if exists)
ALTER TABLE distributor 
    DROP COLUMN IF EXISTS country_code;

-- =====================================================
-- 5. Change country_code to country_id in distributor_agent
-- =====================================================
-- Add new country_id column
ALTER TABLE distributor_agent 
    ADD COLUMN IF NOT EXISTS country_id UUID;

-- Create index for country_id
CREATE INDEX idx_distributor_agent_country_id 
    ON distributor_agent(country_id);

-- Drop old country_code column (if exists)
ALTER TABLE distributor_agent 
    DROP COLUMN IF EXISTS country_code;

