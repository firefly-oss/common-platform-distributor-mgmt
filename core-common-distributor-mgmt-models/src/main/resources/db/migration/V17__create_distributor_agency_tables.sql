-- Create distributor_agency table
CREATE TABLE IF NOT EXISTS distributor_agency (
    id UUID PRIMARY KEY,
    distributor_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    country_id UUID NOT NULL,
    administrative_division_id UUID,
    address_line VARCHAR(255),
    postal_code VARCHAR(20),
    city VARCHAR(100),
    state VARCHAR(100),
    phone_number VARCHAR(50),
    email VARCHAR(255),
    is_headquarters BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    opened_at TIMESTAMP,
    closed_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID,
    CONSTRAINT fk_distributor_agency_distributor
        FOREIGN KEY (distributor_id)
        REFERENCES distributor (id)
        ON DELETE CASCADE
);

-- Create indexes for distributor_agency table
CREATE INDEX idx_distributor_agency_distributor_id ON distributor_agency(distributor_id);
CREATE INDEX idx_distributor_agency_country_id ON distributor_agency(country_id);
CREATE INDEX idx_distributor_agency_administrative_division_id ON distributor_agency(administrative_division_id);
CREATE INDEX idx_distributor_agency_code ON distributor_agency(code);
CREATE INDEX idx_distributor_agency_is_active ON distributor_agency(is_active);
CREATE UNIQUE INDEX idx_distributor_agency_unique_code ON distributor_agency(distributor_id, code) WHERE is_active = TRUE;
