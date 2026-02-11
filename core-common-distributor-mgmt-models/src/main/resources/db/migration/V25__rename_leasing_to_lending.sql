-- Rename leasing_contract table to lending_contract
ALTER TABLE leasing_contract RENAME TO lending_contract;

-- Rename indexes
ALTER INDEX IF EXISTS idx_leasing_contract_originating_agent_id RENAME TO idx_lending_contract_originating_agent_id;
ALTER INDEX IF EXISTS idx_leasing_contract_agency_id RENAME TO idx_lending_contract_agency_id;

-- Rename foreign key constraints (if needed)
-- Note: Constraint names may vary, adjust as needed
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.table_constraints 
               WHERE constraint_name = 'fk_leasing_contract_agent' 
               AND table_name = 'lending_contract') THEN
        ALTER TABLE lending_contract RENAME CONSTRAINT fk_leasing_contract_agent TO fk_lending_contract_agent;
    END IF;
    
    IF EXISTS (SELECT 1 FROM information_schema.table_constraints 
               WHERE constraint_name = 'fk_leasing_contract_agency' 
               AND table_name = 'lending_contract') THEN
        ALTER TABLE lending_contract RENAME CONSTRAINT fk_leasing_contract_agency TO fk_lending_contract_agency;
    END IF;
END $$;

-- Update shipment table foreign key reference
ALTER TABLE shipment
    DROP CONSTRAINT IF EXISTS fk_shipment_leasing_contract,
    ADD CONSTRAINT fk_shipment_lending_contract
        FOREIGN KEY (leasing_contract_id)
        REFERENCES lending_contract (id);

-- Rename the column in shipment table for clarity
ALTER TABLE shipment
    RENAME COLUMN leasing_contract_id TO lending_contract_id;

-- Update index name
ALTER INDEX IF EXISTS idx_shipment_leasing_contract_id RENAME TO idx_shipment_lending_contract_id;
