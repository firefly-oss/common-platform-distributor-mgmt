-- Remove the lending_type column from lending_configuration table
-- This column was replaced by lending_type_id foreign key in V7 migration

-- First, drop the index on the old column
DROP INDEX IF EXISTS idx_lending_configuration_lending_type;

-- Drop the lending_type column
ALTER TABLE lending_configuration DROP COLUMN IF EXISTS lending_type;

-- Optional: Add comment for documentation
COMMENT ON TABLE lending_configuration IS 'Lending configuration table - lending_type column removed in favor of lending_type_id foreign key';