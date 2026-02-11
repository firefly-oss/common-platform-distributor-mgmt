-- Create agent_role table for reference data
CREATE TABLE IF NOT EXISTS agent_role (
    id UUID PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    level INTEGER NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create configuration_scope table for reference data
CREATE TABLE IF NOT EXISTS configuration_scope (
    id UUID PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    level INTEGER NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create configuration_data_type table for reference data
CREATE TABLE IF NOT EXISTS configuration_data_type (
    id UUID PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Insert default agent roles
INSERT INTO agent_role (id, code, name, description, level) VALUES
    (gen_random_uuid(), 'ADMINISTRATOR', 'Administrator', 'Full access to all agency operations', 1),
    (gen_random_uuid(), 'MANAGER', 'Manager', 'Oversight of teams and operations', 2),
    (gen_random_uuid(), 'SUPERVISOR', 'Supervisor', 'Direct oversight of agents', 3),
    (gen_random_uuid(), 'AGENT', 'Agent', 'Standard agent for operational activities', 4),
    (gen_random_uuid(), 'ANALYST', 'Analyst', 'Data analysis and reporting', 5),
    (gen_random_uuid(), 'SUPPORT', 'Support', 'Customer and operational support', 6),
    (gen_random_uuid(), 'VIEWER', 'Viewer', 'Read-only access', 7);

-- Insert default configuration scopes
INSERT INTO configuration_scope (id, code, name, description, level) VALUES
    (gen_random_uuid(), 'DISTRIBUTOR', 'Distributor', 'Configuration applies to entire distributor', 1),
    (gen_random_uuid(), 'AGENCY', 'Agency', 'Configuration applies to specific agency', 2),
    (gen_random_uuid(), 'AGENT', 'Agent', 'Configuration applies to specific agent', 3);

-- Insert default configuration data types
INSERT INTO configuration_data_type (id, code, name, description) VALUES
    (gen_random_uuid(), 'STRING', 'String', 'Text value'),
    (gen_random_uuid(), 'NUMBER', 'Number', 'Numeric value (integer or decimal)'),
    (gen_random_uuid(), 'BOOLEAN', 'Boolean', 'True or false value'),
    (gen_random_uuid(), 'JSON', 'JSON', 'JSON object or array'),
    (gen_random_uuid(), 'DATE', 'Date', 'Date without time'),
    (gen_random_uuid(), 'DATETIME', 'DateTime', 'Date with time');

-- Create indexes
CREATE INDEX idx_agent_role_code ON agent_role(code);
CREATE INDEX idx_agent_role_is_active ON agent_role(is_active);
CREATE INDEX idx_configuration_scope_code ON configuration_scope(code);
CREATE INDEX idx_configuration_scope_is_active ON configuration_scope(is_active);
CREATE INDEX idx_configuration_data_type_code ON configuration_data_type(code);
CREATE INDEX idx_configuration_data_type_is_active ON configuration_data_type(is_active);
