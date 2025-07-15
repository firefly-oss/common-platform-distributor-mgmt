-- Create theme enum type for distributor_branding
CREATE TYPE theme_enum AS ENUM ('LIGHT', 'DARK', 'CUSTOM');

-- Create action enum type for distributor_audit_log
CREATE TYPE distributor_action_enum AS ENUM ('CREATED', 'UPDATED', 'TERMINATED');