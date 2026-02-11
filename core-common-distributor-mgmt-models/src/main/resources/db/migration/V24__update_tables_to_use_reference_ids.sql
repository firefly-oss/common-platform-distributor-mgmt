-- Update distributor_agent_agency to use role_id
-- First, drop the index that uses the role column
DROP INDEX IF EXISTS idx_distributor_agent_agency_role;

-- Convert role column from enum to UUID
ALTER TABLE distributor_agent_agency
    ALTER COLUMN role DROP DEFAULT;

ALTER TABLE distributor_agent_agency
    ALTER COLUMN role TYPE UUID USING NULL;

ALTER TABLE distributor_agent_agency
    RENAME COLUMN role TO role_id;

ALTER TABLE distributor_agent_agency
    ADD CONSTRAINT fk_distributor_agent_agency_role
        FOREIGN KEY (role_id)
        REFERENCES agent_role (id);

CREATE INDEX idx_distributor_agent_agency_role_id ON distributor_agent_agency(role_id);

-- Update distributor_configuration to use scope_id and data_type_id
-- First, drop the check constraint that uses scope
ALTER TABLE distributor_configuration
    DROP CONSTRAINT IF EXISTS chk_scope_consistency;

-- Drop unique indexes that use scope column
DROP INDEX IF EXISTS idx_distributor_configuration_unique_distributor;
DROP INDEX IF EXISTS idx_distributor_configuration_unique_agency;
DROP INDEX IF EXISTS idx_distributor_configuration_unique_agent;

-- Drop regular indexes that use these columns
DROP INDEX IF EXISTS idx_distributor_configuration_scope;
DROP INDEX IF EXISTS idx_distributor_configuration_data_type;

-- Convert scope column from enum to UUID
ALTER TABLE distributor_configuration
    ALTER COLUMN scope DROP DEFAULT;

ALTER TABLE distributor_configuration
    ALTER COLUMN scope TYPE UUID USING NULL;

ALTER TABLE distributor_configuration
    RENAME COLUMN scope TO scope_id;

-- Convert data_type column from enum to UUID
ALTER TABLE distributor_configuration
    ALTER COLUMN data_type DROP DEFAULT;

ALTER TABLE distributor_configuration
    ALTER COLUMN data_type TYPE UUID USING NULL;

ALTER TABLE distributor_configuration
    RENAME COLUMN data_type TO data_type_id;

ALTER TABLE distributor_configuration
    ADD CONSTRAINT fk_distributor_configuration_scope
        FOREIGN KEY (scope_id)
        REFERENCES configuration_scope (id),
    ADD CONSTRAINT fk_distributor_configuration_data_type
        FOREIGN KEY (data_type_id)
        REFERENCES configuration_data_type (id);

CREATE INDEX idx_distributor_configuration_scope_id ON distributor_configuration(scope_id);
CREATE INDEX idx_distributor_configuration_data_type_id ON distributor_configuration(data_type_id);

-- Note: We dropped the check constraint chk_scope_consistency earlier
-- We'll handle scope validation in application layer since we now use UUIDs

-- Now drop the enum types since we're no longer using them
DROP TYPE IF EXISTS agent_role_enum;
DROP TYPE IF EXISTS configuration_scope_enum;
DROP TYPE IF EXISTS configuration_data_type_enum;
