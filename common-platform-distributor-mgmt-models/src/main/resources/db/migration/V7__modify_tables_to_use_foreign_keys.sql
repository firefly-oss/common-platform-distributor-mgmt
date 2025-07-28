-- First, add new columns to the product table
ALTER TABLE product ADD COLUMN category_id BIGINT;

-- Create a temporary function to help with the migration
CREATE OR REPLACE FUNCTION migrate_product_categories() RETURNS void AS $$
DECLARE
    product_record RECORD;
    category_id BIGINT;
BEGIN
    FOR product_record IN SELECT id, category FROM product LOOP
        SELECT pc.id INTO category_id FROM product_category pc WHERE pc.code = product_record.category::text;
        UPDATE product SET category_id = category_id WHERE id = product_record.id;
    END LOOP;
END;
$$ LANGUAGE plpgsql;

-- Execute the function to migrate data
SELECT migrate_product_categories();

-- Drop the temporary function
DROP FUNCTION migrate_product_categories();

-- Make the new column not null and add foreign key constraint
ALTER TABLE product ALTER COLUMN category_id SET NOT NULL;
ALTER TABLE product ADD CONSTRAINT fk_product_category
    FOREIGN KEY (category_id) REFERENCES product_category (id);

-- Create index on the new column
CREATE INDEX idx_product_category_id ON product(category_id);

-- Now do the same for lending_configuration table
ALTER TABLE lending_configuration ADD COLUMN lending_type_id BIGINT;

-- Create a temporary function to help with the migration
CREATE OR REPLACE FUNCTION migrate_lending_types() RETURNS void AS $$
DECLARE
    lending_record RECORD;
    type_id BIGINT;
BEGIN
    FOR lending_record IN SELECT id, lending_type FROM lending_configuration LOOP
        SELECT lt.id INTO type_id FROM lending_type lt WHERE lt.code = lending_record.lending_type::text;
        UPDATE lending_configuration SET lending_type_id = type_id WHERE id = lending_record.id;
    END LOOP;
END;
$$ LANGUAGE plpgsql;

-- Execute the function to migrate data
SELECT migrate_lending_types();

-- Drop the temporary function
DROP FUNCTION migrate_lending_types();

-- Make the new column not null and add foreign key constraint
ALTER TABLE lending_configuration ALTER COLUMN lending_type_id SET NOT NULL;
ALTER TABLE lending_configuration ADD CONSTRAINT fk_lending_type
    FOREIGN KEY (lending_type_id) REFERENCES lending_type (id);

-- Create index on the new column
CREATE INDEX idx_lending_configuration_lending_type_id ON lending_configuration(lending_type_id);

-- Drop the old columns (optional - can be done in a later migration after verifying everything works)
-- ALTER TABLE product DROP COLUMN category;
-- ALTER TABLE lending_configuration DROP COLUMN lending_type;

-- Drop the enum types (optional - can be done in a later migration after verifying everything works)
-- DROP TYPE product_category_enum;
-- DROP TYPE lending_type_enum;