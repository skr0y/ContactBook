-- FUNCTION: public."AverageContactsPerGroup"()

-- DROP FUNCTION public."AverageContactsPerGroup"();

CREATE OR REPLACE FUNCTION public.AverageContactsPerGroup(
	)
    RETURNS numeric
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 0
AS $BODY$
SELECT AVG(c) FROM(SELECT COUNT(*) AS c FROM "Contacts" GROUP BY "GroupID") AS x

$BODY$;

ALTER FUNCTION public."AverageContactsPerGroup"()
    OWNER TO postgres;
