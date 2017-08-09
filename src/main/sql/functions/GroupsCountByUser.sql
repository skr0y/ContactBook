-- FUNCTION: public."GroupsCountByUser"(integer)

-- DROP FUNCTION public."GroupsCountByUser"(integer);

CREATE OR REPLACE FUNCTION public.GroupsCountByUser(
	param integer)
    RETURNS bigint
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 0
AS $BODY$

SELECT COUNT(*) FROM "Groups" WHERE "UserID" = param

$BODY$;

ALTER FUNCTION public."GroupsCountByUser"(integer)
    OWNER TO postgres;
