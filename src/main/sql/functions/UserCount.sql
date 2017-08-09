-- FUNCTION: public."UserCount"()

-- DROP FUNCTION public."UserCount"();

CREATE OR REPLACE FUNCTION public.UserCount(
	)
    RETURNS bigint
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 0
AS $BODY$
SELECT COUNT(*) FROM "Users"
$BODY$;

ALTER FUNCTION public."UserCount"()
    OWNER TO postgres;
