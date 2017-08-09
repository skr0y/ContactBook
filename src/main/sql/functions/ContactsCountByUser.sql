-- FUNCTION: public."ContactsCountByUser"(integer)

-- DROP FUNCTION public."ContactsCountByUser"(integer);

CREATE OR REPLACE FUNCTION public.ContactsCountByUser(
	param integer)
    RETURNS bigint
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 0
AS $BODY$

SELECT COUNT(*) FROM "Contacts" WHERE "UserID" = param

$BODY$;

ALTER FUNCTION public."ContactsCountByUser"(integer)
    OWNER TO postgres;
