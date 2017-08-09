-- FUNCTION: public."AverageContactsPerUser"()

-- DROP FUNCTION public."AverageContactsPerUser"();

CREATE OR REPLACE FUNCTION public.AverageContactsPerUser(
	)
    RETURNS numeric
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 0
AS $BODY$

SELECT AVG(c) FROM(SELECT COUNT(*) AS c FROM "Contacts" GROUP BY "UserID") AS x

$BODY$;

ALTER FUNCTION public."AverageContactsPerUser"()
    OWNER TO postgres;
