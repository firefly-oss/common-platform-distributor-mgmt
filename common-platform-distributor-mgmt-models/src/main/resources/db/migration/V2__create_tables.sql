-- Create distributor table
CREATE TABLE IF NOT EXISTS distributor (
    id UUID PRIMARY KEY,
    external_code VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    display_name VARCHAR(255),
    tax_id VARCHAR(50),
    registration_number VARCHAR(100),
    website_url VARCHAR(255),
    phone_number VARCHAR(50),
    email VARCHAR(255),
    support_email VARCHAR(255),
    address_line VARCHAR(255),
    postal_code VARCHAR(20),
    city VARCHAR(100),
    state VARCHAR(100),
    country_code VARCHAR(2),
    is_active BOOLEAN DEFAULT TRUE,
    is_test_distributor BOOLEAN DEFAULT FALSE,
    time_zone VARCHAR(50),
    default_locale VARCHAR(10),
    onboarded_at TIMESTAMP,
    terminated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID
);

-- Create distributor_branding table
CREATE TABLE IF NOT EXISTS distributor_branding (
    id UUID PRIMARY KEY,
    distributor_id UUID NOT NULL,
    logo_url VARCHAR(255),
    favicon_url VARCHAR(255),
    primary_color VARCHAR(20),
    secondary_color VARCHAR(20),
    background_color VARCHAR(20),
    font_family VARCHAR(100),
    theme theme_enum,
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID,
    CONSTRAINT fk_distributor_branding_distributor
        FOREIGN KEY (distributor_id)
        REFERENCES distributor (id)
);

-- Create distributor_audit_log table
CREATE TABLE IF NOT EXISTS distributor_audit_log (
    id UUID PRIMARY KEY,
    distributor_id UUID NOT NULL,
    action distributor_action_enum NOT NULL,
    entity VARCHAR(50) NOT NULL,
    entity_id VARCHAR(50) NOT NULL,
    metadata JSONB,
    ip_address VARCHAR(45),
    user_id UUID,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_distributor_audit_log_distributor
        FOREIGN KEY (distributor_id)
        REFERENCES distributor (id)
);

-- Create indexes
CREATE INDEX idx_distributor_external_code ON distributor(external_code);
CREATE INDEX idx_distributor_branding_distributor_id ON distributor_branding(distributor_id);
CREATE INDEX idx_distributor_audit_log_distributor_id ON distributor_audit_log(distributor_id);
CREATE INDEX idx_distributor_audit_log_entity_id ON distributor_audit_log(entity_id);
CREATE INDEX idx_distributor_audit_log_timestamp ON distributor_audit_log(timestamp);