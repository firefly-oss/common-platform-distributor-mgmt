-- Add UUID auto-generation to all primary keys using gen_random_uuid()

-- V2 tables
ALTER TABLE distributor ALTER COLUMN id SET DEFAULT gen_random_uuid();
ALTER TABLE distributor_branding ALTER COLUMN id SET DEFAULT gen_random_uuid();
ALTER TABLE distributor_audit_log ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- V5 tables
ALTER TABLE product ALTER COLUMN id SET DEFAULT gen_random_uuid();
ALTER TABLE lending_configuration ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- V6 tables
ALTER TABLE product_category ALTER COLUMN id SET DEFAULT gen_random_uuid();
ALTER TABLE lending_type ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- V8 tables
ALTER TABLE leasing_contract ALTER COLUMN id SET DEFAULT gen_random_uuid();
ALTER TABLE shipment ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- V9 tables
ALTER TABLE distributor_operation ALTER COLUMN id SET DEFAULT gen_random_uuid();
ALTER TABLE distributor_simulation ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- V10 tables
ALTER TABLE terms_and_conditions_template ALTER COLUMN id SET DEFAULT gen_random_uuid();
ALTER TABLE distributor_terms_and_conditions ALTER COLUMN id SET DEFAULT gen_random_uuid();