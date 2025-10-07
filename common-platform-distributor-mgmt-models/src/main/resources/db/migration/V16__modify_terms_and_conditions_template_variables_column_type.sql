-- Modify variables column type from JSONB to TEXT in terms_and_conditions_template table

-- Alter variables column type from JSONB to TEXT
ALTER TABLE terms_and_conditions_template ALTER COLUMN variables TYPE TEXT;