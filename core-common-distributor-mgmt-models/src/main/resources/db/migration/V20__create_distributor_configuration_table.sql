-- Create configuration scope enum
CREATE TYPE configuration_scope_enum AS ENUM (
    'DISTRIBUTOR',
    'AGENCY',
    'AGENT'
);

-- Create configuration data type enum
CREATE TYPE configuration_data_type_enum AS ENUM (
    'STRING',
    'NUMBER',
    'BOOLEAN',
    'JSON',
    'DATE',
    'DATETIME'
);

-- Create distributor_configuration table
CREATE TABLE IF NOT EXISTS distributor_configuration (
    id UUID PRIMARY KEY,
    distributor_id UUID NOT NULL,
    agency_id UUID,
    agent_id UUID,
    scope configuration_scope_enum NOT NULL,
    config_key VARCHAR(255) NOT NULL,
    config_value TEXT NOT NULL,
    data_type configuration_data_type_enum NOT NULL DEFAULT 'STRING',
    category VARCHAR(100),
    description TEXT,
    is_sensitive BOOLEAN DEFAULT FALSE,
    is_overridable BOOLEAN DEFAULT TRUE,
    is_active BOOLEAN DEFAULT TRUE,
    effective_from TIMESTAMP,
    effective_until TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID,
    CONSTRAINT fk_distributor_configuration_distributor
        FOREIGN KEY (distributor_id)
        REFERENCES distributor (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_distributor_configuration_agency
        FOREIGN KEY (agency_id)
        REFERENCES distributor_agency (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_distributor_configuration_agent
        FOREIGN KEY (agent_id)
        REFERENCES distributor_agent (id)
        ON DELETE CASCADE,
    CONSTRAINT chk_scope_consistency CHECK (
        (scope = 'DISTRIBUTOR' AND agency_id IS NULL AND agent_id IS NULL) OR
        (scope = 'AGENCY' AND agency_id IS NOT NULL AND agent_id IS NULL) OR
        (scope = 'AGENT' AND agent_id IS NOT NULL)
    )
);

-- Create indexes for distributor_configuration table
CREATE INDEX idx_distributor_configuration_distributor_id ON distributor_configuration(distributor_id);
CREATE INDEX idx_distributor_configuration_agency_id ON distributor_configuration(agency_id);
CREATE INDEX idx_distributor_configuration_agent_id ON distributor_configuration(agent_id);
CREATE INDEX idx_distributor_configuration_scope ON distributor_configuration(scope);
CREATE INDEX idx_distributor_configuration_config_key ON distributor_configuration(config_key);
CREATE INDEX idx_distributor_configuration_category ON distributor_configuration(category);
CREATE INDEX idx_distributor_configuration_is_active ON distributor_configuration(is_active);
CREATE UNIQUE INDEX idx_distributor_configuration_unique_distributor ON distributor_configuration(distributor_id, config_key) 
    WHERE scope = 'DISTRIBUTOR' AND agency_id IS NULL AND agent_id IS NULL AND is_active = TRUE;
CREATE UNIQUE INDEX idx_distributor_configuration_unique_agency ON distributor_configuration(distributor_id, agency_id, config_key) 
    WHERE scope = 'AGENCY' AND agency_id IS NOT NULL AND agent_id IS NULL AND is_active = TRUE;
CREATE UNIQUE INDEX idx_distributor_configuration_unique_agent ON distributor_configuration(distributor_id, agent_id, config_key) 
    WHERE scope = 'AGENT' AND agent_id IS NOT NULL AND is_active = TRUE;
