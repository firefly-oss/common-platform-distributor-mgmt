-- Remove category column from product table

-- Drop the index on category column first
DROP INDEX IF EXISTS idx_product_category;

-- Remove the category column from product table
ALTER TABLE product DROP COLUMN IF EXISTS category;