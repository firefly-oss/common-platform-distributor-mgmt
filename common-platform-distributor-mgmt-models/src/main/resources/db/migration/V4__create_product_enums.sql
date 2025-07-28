-- Create product category enum type
CREATE TYPE product_category_enum AS ENUM (
    'MEDICAL_EQUIPMENT',
    'VEHICLE',
    'CONSTRUCTION_EQUIPMENT',
    'TECHNOLOGY',
    'OFFICE_EQUIPMENT',
    'INDUSTRIAL_MACHINERY',
    'OTHER'
);

-- Create lending type enum type
CREATE TYPE lending_type_enum AS ENUM (
    'LEASE',
    'RENT',
    'LEASE_TO_OWN',
    'FINANCE',
    'SUBSCRIPTION',
    'INSTALLMENT'
);