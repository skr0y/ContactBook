-- FUNCTION: public."DeleteUser"(integer)

-- DROP FUNCTION public."DeleteUser"(integer);

CREATE OR REPLACE FUNCTION public.DeleteUser(
	user_id integer)
    RETURNS void
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 0
AS $BODY$

DELETE FROM "Users" WHERE "UserID" = user_id

$BODY$;

ALTER FUNCTION public."DeleteUser"(integer)
    OWNER TO postgres;
