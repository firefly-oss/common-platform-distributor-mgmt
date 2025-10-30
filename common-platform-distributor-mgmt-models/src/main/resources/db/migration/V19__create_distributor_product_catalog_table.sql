-- Create distributor_product_catalog table
CREATE TABLE IF NOT EXISTS distributor_product_catalog (
    id UUID PRIMARY KEY,
    distributor_id UUID NOT NULL,
    product_id UUID NOT NULL,
    catalog_code VARCHAR(100),
    display_name VARCHAR(255),
    custom_description TEXT,
    is_featured BOOLEAN DEFAULT FALSE,
    is_available BOOLEAN DEFAULT TRUE,
    availability_start_date TIMESTAMP,
    availability_end_date TIMESTAMP,
    display_order INTEGER DEFAULT 0,
    min_quantity INTEGER DEFAULT 1,
    max_quantity INTEGER,
    shipping_available BOOLEAN DEFAULT TRUE,
    shipping_cost DECIMAL(15, 2),
    shipping_time_days INTEGER,
    special_conditions TEXT,
    metadata JSONB,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID,
    CONSTRAINT fk_distributor_product_catalog_distributor
        FOREIGN KEY (distributor_id)
        REFERENCES distributor (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_distributor_product_catalog_product
        FOREIGN KEY (product_id)
        REFERENCES product (id)
        ON DELETE CASCADE
);

-- Create indexes for distributor_product_catalog table
CREATE INDEX idx_distributor_product_catalog_distributor_id ON distributor_product_catalog(distributor_id);
CREATE INDEX idx_distributor_product_catalog_product_id ON distributor_product_catalog(product_id);
CREATE INDEX idx_distributor_product_catalog_catalog_code ON distributor_product_catalog(catalog_code);
CREATE INDEX idx_distributor_product_catalog_is_featured ON distributor_product_catalog(is_featured);
CREATE INDEX idx_distributor_product_catalog_is_available ON distributor_product_catalog(is_available);
CREATE INDEX idx_distributor_product_catalog_is_active ON distributor_product_catalog(is_active);
CREATE INDEX idx_distributor_product_catalog_display_order ON distributor_product_catalog(display_order);
CREATE UNIQUE INDEX idx_distributor_product_catalog_unique ON distributor_product_catalog(distributor_id, product_id) WHERE is_active = TRUE;
