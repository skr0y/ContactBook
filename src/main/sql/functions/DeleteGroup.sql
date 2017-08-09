-- FUNCTION: public."DeleteGroup"(integer, integer)

-- DROP FUNCTION public."DeleteGroup"(integer, integer);

CREATE OR REPLACE FUNCTION public.DeleteGroup(
	user_id integer,
	group_id integer)
    RETURNS void
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 0
AS $BODY$

DELETE FROM "Groups" WHERE "GroupID" = group_id AND "UserID" = user_id

$BODY$;

ALTER FUNCTION public."DeleteGroup"(integer, integer)
    OWNER TO postgres;
