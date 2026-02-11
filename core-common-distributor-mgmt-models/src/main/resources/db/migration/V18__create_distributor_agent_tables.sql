-- Create agent role enum
CREATE TYPE agent_role_enum AS ENUM (
    'ADMINISTRATOR',
    'MANAGER',
    'SUPERVISOR',
    'AGENT',
    'ANALYST',
    'SUPPORT',
    'VIEWER'
);

-- Create distributor_agent table
CREATE TABLE IF NOT EXISTS distributor_agent (
    id UUID PRIMARY KEY,
    distributor_id UUID NOT NULL,
    user_id UUID NOT NULL,
    employee_code VARCHAR(100),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50),
    avatar_url VARCHAR(255),
    department VARCHAR(100),
    unit VARCHAR(100),
    job_title VARCHAR(100),
    address_line VARCHAR(255),
    postal_code VARCHAR(20),
    city VARCHAR(100),
    state VARCHAR(100),
    country_code VARCHAR(2),
    hire_date DATE,
    termination_date DATE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID,
    CONSTRAINT fk_distributor_agent_distributor
        FOREIGN KEY (distributor_id)
        REFERENCES distributor (id)
        ON DELETE CASCADE
);

-- Create distributor_agent_agency table (many-to-many relationship)
CREATE TABLE IF NOT EXISTS distributor_agent_agency (
    id UUID PRIMARY KEY,
    agent_id UUID NOT NULL,
    agency_id UUID NOT NULL,
    role agent_role_enum NOT NULL,
    is_primary_agency BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    unassigned_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID,
    CONSTRAINT fk_distributor_agent_agency_agent
        FOREIGN KEY (agent_id)
        REFERENCES distributor_agent (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_distributor_agent_agency_agency
        FOREIGN KEY (agency_id)
        REFERENCES distributor_agency (id)
        ON DELETE CASCADE
);

-- Create indexes for distributor_agent table
CREATE INDEX idx_distributor_agent_distributor_id ON distributor_agent(distributor_id);
CREATE INDEX idx_distributor_agent_user_id ON distributor_agent(user_id);
CREATE INDEX idx_distributor_agent_employee_code ON distributor_agent(employee_code);
CREATE INDEX idx_distributor_agent_email ON distributor_agent(email);
CREATE INDEX idx_distributor_agent_is_active ON distributor_agent(is_active);
CREATE UNIQUE INDEX idx_distributor_agent_unique_user ON distributor_agent(distributor_id, user_id) WHERE is_active = TRUE;

-- Create indexes for distributor_agent_agency table
CREATE INDEX idx_distributor_agent_agency_agent_id ON distributor_agent_agency(agent_id);
CREATE INDEX idx_distributor_agent_agency_agency_id ON distributor_agent_agency(agency_id);
CREATE INDEX idx_distributor_agent_agency_role ON distributor_agent_agency(role);
CREATE INDEX idx_distributor_agent_agency_is_active ON distributor_agent_agency(is_active);
CREATE UNIQUE INDEX idx_distributor_agent_agency_unique ON distributor_agent_agency(agent_id, agency_id) WHERE is_active = TRUE;
