-- Create terms_and_conditions_template table
CREATE TABLE IF NOT EXISTS terms_and_conditions_template (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(50) NOT NULL,
    template_content TEXT NOT NULL,
    variables JSONB,
    version VARCHAR(50) NOT NULL,
    is_default BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    approval_required BOOLEAN DEFAULT TRUE,
    auto_renewal BOOLEAN DEFAULT FALSE,
    renewal_period_months INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT
);

-- Create distributor_terms_and_conditions table
CREATE TABLE IF NOT EXISTS distributor_terms_and_conditions (
    id BIGSERIAL PRIMARY KEY,
    distributor_id BIGINT NOT NULL,
    template_id BIGINT,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    version VARCHAR(50) NOT NULL,
    effective_date TIMESTAMP NOT NULL,
    expiration_date TIMESTAMP,
    signed_date TIMESTAMP,
    signed_by BIGINT,
    status VARCHAR(50) NOT NULL DEFAULT 'DRAFT',
    is_active BOOLEAN DEFAULT TRUE,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    CONSTRAINT fk_distributor_terms_distributor
        FOREIGN KEY (distributor_id)
        REFERENCES distributor (id),
    CONSTRAINT fk_distributor_terms_template
        FOREIGN KEY (template_id)
        REFERENCES terms_and_conditions_template (id)
);

-- Create indexes for terms_and_conditions_template table
CREATE INDEX idx_terms_template_name ON terms_and_conditions_template(name);
CREATE INDEX idx_terms_template_category ON terms_and_conditions_template(category);
CREATE INDEX idx_terms_template_is_active ON terms_and_conditions_template(is_active);
CREATE INDEX idx_terms_template_is_default ON terms_and_conditions_template(is_default);
CREATE INDEX idx_terms_template_version ON terms_and_conditions_template(version);

-- Create indexes for distributor_terms_and_conditions table
CREATE INDEX idx_distributor_terms_distributor_id ON distributor_terms_and_conditions(distributor_id);
CREATE INDEX idx_distributor_terms_template_id ON distributor_terms_and_conditions(template_id);
CREATE INDEX idx_distributor_terms_status ON distributor_terms_and_conditions(status);
CREATE INDEX idx_distributor_terms_effective_date ON distributor_terms_and_conditions(effective_date);
CREATE INDEX idx_distributor_terms_expiration_date ON distributor_terms_and_conditions(expiration_date);
CREATE INDEX idx_distributor_terms_is_active ON distributor_terms_and_conditions(is_active);
CREATE INDEX idx_distributor_terms_version ON distributor_terms_and_conditions(version);

-- Create unique constraint to prevent duplicate active terms for same distributor and template
CREATE UNIQUE INDEX idx_distributor_terms_unique_active 
ON distributor_terms_and_conditions(distributor_id, template_id) 
WHERE is_active = TRUE AND status IN ('SIGNED', 'PENDING_SIGNATURE');

-- Insert some default templates
INSERT INTO terms_and_conditions_template (name, description, category, template_content, variables, version, is_default, is_active, approval_required, auto_renewal, renewal_period_months, created_by) VALUES
('General Distributor Agreement', 'Standard terms and conditions for distributor partnerships', 'GENERAL', 
'DISTRIBUTOR AGREEMENT

This agreement is entered into between {{companyName}} and {{distributorName}} ({{distributorTaxId}}) effective {{effectiveDate}}.

1. TERRITORY
The distributor is authorized to operate in {{operationalTerritory}}.

2. TERM
This agreement is effective from {{effectiveDate}} to {{expirationDate}}.

3. COMMISSION STRUCTURE
Commission rate: {{commissionRate}}%
Payment terms: {{paymentTerms}}

4. OBLIGATIONS
- Maintain minimum sales targets
- Comply with all regulatory requirements
- Provide customer support

By signing below, both parties agree to these terms.

Distributor: {{distributorName}}
Date: {{signedDate}}', 
'{"companyName": {"type": "string", "default": "Firefly Financial Services"}, "distributorName": {"type": "string", "required": true}, "distributorTaxId": {"type": "string", "required": true}, "effectiveDate": {"type": "date", "required": true}, "expirationDate": {"type": "date", "required": true}, "operationalTerritory": {"type": "string", "required": true}, "commissionRate": {"type": "number", "default": 5.0}, "paymentTerms": {"type": "string", "default": "Net 30 days"}, "signedDate": {"type": "date", "required": true}}',
'1.0', TRUE, TRUE, TRUE, TRUE, 12, 1),

('Lending Terms Agreement', 'Specific terms for lending product distribution', 'LENDING',
'LENDING DISTRIBUTION AGREEMENT

This lending agreement supplements the general distributor agreement for {{distributorName}}.

1. LENDING AUTHORITY
Maximum loan amount: {{maxLoanAmount}}
Interest rate range: {{minInterestRate}}% - {{maxInterestRate}}%
Maximum term: {{maxLoanTermMonths}} months

2. RISK MANAGEMENT
Credit score minimum: {{minCreditScore}}
Debt-to-income ratio maximum: {{maxDebtToIncomeRatio}}%

3. COMPLIANCE
- All lending must comply with local regulations
- Monthly reporting required
- Audit rights reserved

Effective: {{effectiveDate}}
Expires: {{expirationDate}}',
'{"distributorName": {"type": "string", "required": true}, "maxLoanAmount": {"type": "number", "required": true}, "minInterestRate": {"type": "number", "required": true}, "maxInterestRate": {"type": "number", "required": true}, "maxLoanTermMonths": {"type": "number", "required": true}, "minCreditScore": {"type": "number", "default": 650}, "maxDebtToIncomeRatio": {"type": "number", "default": 40}, "effectiveDate": {"type": "date", "required": true}, "expirationDate": {"type": "date", "required": true}}',
'1.0', FALSE, TRUE, TRUE, TRUE, 12, 1);

-- Add constraint to ensure valid status values
ALTER TABLE distributor_terms_and_conditions 
ADD CONSTRAINT chk_distributor_terms_status 
CHECK (status IN ('DRAFT', 'PENDING_SIGNATURE', 'SIGNED', 'EXPIRED', 'TERMINATED'));

-- Add constraint to ensure valid category values
ALTER TABLE terms_and_conditions_template 
ADD CONSTRAINT chk_template_category 
CHECK (category IN ('GENERAL', 'LENDING', 'OPERATIONAL', 'COMPLIANCE', 'MARKETING', 'TECHNICAL'));
