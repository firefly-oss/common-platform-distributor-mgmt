-- Add agent_id and agency_id to distributor_simulation table
ALTER TABLE distributor_simulation
    ADD COLUMN agent_id UUID,
    ADD COLUMN agency_id UUID,
    ADD CONSTRAINT fk_distributor_simulation_agent
        FOREIGN KEY (agent_id)
        REFERENCES distributor_agent (id)
        ON DELETE SET NULL,
    ADD CONSTRAINT fk_distributor_simulation_agency
        FOREIGN KEY (agency_id)
        REFERENCES distributor_agency (id)
        ON DELETE SET NULL;

-- Add agent_id and agency_id to distributor_operation table
ALTER TABLE distributor_operation
    ADD COLUMN managed_by_agent_id UUID,
    ADD COLUMN agency_id UUID,
    ADD CONSTRAINT fk_distributor_operation_agent
        FOREIGN KEY (managed_by_agent_id)
        REFERENCES distributor_agent (id)
        ON DELETE SET NULL,
    ADD CONSTRAINT fk_distributor_operation_agency
        FOREIGN KEY (agency_id)
        REFERENCES distributor_agency (id)
        ON DELETE SET NULL;

-- Add agent_id to leasing_contract table
ALTER TABLE leasing_contract
    ADD COLUMN originating_agent_id UUID,
    ADD COLUMN agency_id UUID,
    ADD CONSTRAINT fk_leasing_contract_agent
        FOREIGN KEY (originating_agent_id)
        REFERENCES distributor_agent (id)
        ON DELETE SET NULL,
    ADD CONSTRAINT fk_leasing_contract_agency
        FOREIGN KEY (agency_id)
        REFERENCES distributor_agency (id)
        ON DELETE SET NULL;

-- Create indexes for new foreign keys
CREATE INDEX idx_distributor_simulation_agent_id ON distributor_simulation(agent_id);
CREATE INDEX idx_distributor_simulation_agency_id ON distributor_simulation(agency_id);
CREATE INDEX idx_distributor_operation_managed_by_agent_id ON distributor_operation(managed_by_agent_id);
CREATE INDEX idx_distributor_operation_agency_id ON distributor_operation(agency_id);
CREATE INDEX idx_leasing_contract_originating_agent_id ON leasing_contract(originating_agent_id);
CREATE INDEX idx_leasing_contract_agency_id ON leasing_contract(agency_id);
