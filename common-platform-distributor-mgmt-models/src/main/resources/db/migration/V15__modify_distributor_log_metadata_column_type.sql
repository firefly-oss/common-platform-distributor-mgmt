-- Modify metadata column type from JSONB to TEXT and rename columns in distributor_audit_log table

-- Alter metadata column type from JSONB to TEXT
ALTER TABLE distributor_audit_log ALTER COLUMN metadata TYPE TEXT;

-- Rename metadata column to audit_metadata
ALTER TABLE distributor_audit_log RENAME COLUMN metadata TO audit_metadata;

-- Rename timestamp column to audit_timestamp
ALTER TABLE distributor_audit_log RENAME COLUMN timestamp TO audit_timestamp;