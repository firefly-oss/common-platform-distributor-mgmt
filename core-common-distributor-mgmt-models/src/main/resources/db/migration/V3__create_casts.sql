-- Cast for theme_enum
CREATE CAST (VARCHAR AS theme_enum)
    WITH INOUT AS IMPLICIT;

-- Cast for distributor_action_enum
CREATE CAST (VARCHAR AS distributor_action_enum)
    WITH INOUT AS IMPLICIT;