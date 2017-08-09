-- FUNCTION: public."UpdateGroup"(integer, character varying, integer)

-- DROP FUNCTION public."UpdateGroup"(integer, character varying, integer);

CREATE OR REPLACE FUNCTION public.UpdateGroup(
	groupid integer,
	groupname character varying,
	userid integer)
    RETURNS void
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 0
AS $BODY$

UPDATE "Groups" SET "GroupName" = groupname, "UserID" = userid WHERE "GroupID" = groupid

$BODY$;

ALTER FUNCTION public."UpdateGroup"(integer, character varying, integer)
    OWNER TO postgres;
