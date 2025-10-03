-- Modify specifications column type from JSONB to TEXT in product table

-- Alter specifications column type from JSONB to TEXT
ALTER TABLE product ALTER COLUMN specifications TYPE TEXT;