-- Create distributor_operation table
CREATE TABLE IF NOT EXISTS distributor_operation (
    id UUID PRIMARY KEY,
    distributor_id UUID NOT NULL,
    country_id UUID NOT NULL,
    administrative_division_id UUID NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID,
    CONSTRAINT fk_distributor_operation_distributor
        FOREIGN KEY (distributor_id)
        REFERENCES distributor (id)
);

-- Create distributor_simulation table
CREATE TABLE IF NOT EXISTS distributor_simulation (
    id UUID PRIMARY KEY,
    distributor_id UUID NOT NULL,
    application_id UUID NOT NULL,
    simulation_status VARCHAR(50),
    notes TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID,
    CONSTRAINT fk_distributor_simulation_distributor
        FOREIGN KEY (distributor_id)
        REFERENCES distributor (id)
);

-- Create indexes for distributor_operation table
CREATE INDEX idx_distributor_operation_distributor_id ON distributor_operation(distributor_id);
CREATE INDEX idx_distributor_operation_country_id ON distributor_operation(country_id);
CREATE INDEX idx_distributor_operation_administrative_division_id ON distributor_operation(administrative_division_id);
CREATE INDEX idx_distributor_operation_is_active ON distributor_operation(is_active);
CREATE UNIQUE INDEX idx_distributor_operation_unique ON distributor_operation(distributor_id, country_id, administrative_division_id) WHERE is_active = TRUE;

-- Create indexes for distributor_simulation table
CREATE INDEX idx_distributor_simulation_distributor_id ON distributor_simulation(distributor_id);
CREATE INDEX idx_distributor_simulation_application_id ON distributor_simulation(application_id);
CREATE INDEX idx_distributor_simulation_status ON distributor_simulation(simulation_status);
CREATE INDEX idx_distributor_simulation_is_active ON distributor_simulation(is_active);
CREATE UNIQUE INDEX idx_distributor_simulation_unique ON distributor_simulation(distributor_id, application_id) WHERE is_active = TRUE;
