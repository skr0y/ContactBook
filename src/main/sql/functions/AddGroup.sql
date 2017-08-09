-- FUNCTION: public."AddGroup"(character varying, integer)

-- DROP FUNCTION public."AddGroup"(character varying, integer);

CREATE OR REPLACE FUNCTION public.AddGroup(
	groupname character varying,
	userid integer)
    RETURNS void
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 0
AS $BODY$

INSERT INTO "Groups" ("GroupName", "UserID") VALUES (groupname, userid)

$BODY$;

ALTER FUNCTION public."AddGroup"(character varying, integer)
    OWNER TO postgres;
