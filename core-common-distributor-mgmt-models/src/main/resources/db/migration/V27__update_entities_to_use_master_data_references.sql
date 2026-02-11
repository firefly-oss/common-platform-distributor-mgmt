-- Migration V27: Update entities to use master data references
-- This migration updates DistributorAgency and DistributorAgent to use UUID references to master data tables

-- Update distributor_agency table
-- 1. Add legal_form_id column
ALTER TABLE distributor_agency ADD COLUMN legal_form_id UUID;

-- 2. Drop the old legal_entity_type column
ALTER TABLE distributor_agency DROP COLUMN IF EXISTS legal_entity_type;

-- 3. Add foreign key constraint to legal_form (from master data)
-- Note: This assumes the legal_form table exists in the master data schema
-- If it's in a different schema, adjust accordingly
COMMENT ON COLUMN distributor_agency.legal_form_id IS 'References legal_form(legal_form_id) from master data';

-- 4. Create index for performance
CREATE INDEX IF NOT EXISTS idx_distributor_agency_legal_form_id ON distributor_agency(legal_form_id);

-- Update distributor_agent table
-- 1. Add administrative_division_id column
ALTER TABLE distributor_agent ADD COLUMN administrative_division_id UUID;

-- 2. Add comment for documentation
COMMENT ON COLUMN distributor_agent.administrative_division_id IS 'References administrative_division(division_id) from master data';

-- 3. Create index for performance
CREATE INDEX IF NOT EXISTS idx_distributor_agent_administrative_division_id ON distributor_agent(administrative_division_id);

