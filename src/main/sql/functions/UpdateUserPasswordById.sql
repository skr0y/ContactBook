-- FUNCTION: public."UpdateUserPasswordById"(integer, character varying)

-- DROP FUNCTION public."UpdateUserPasswordById"(integer, character varying);

CREATE OR REPLACE FUNCTION public.UpdateUserPasswordById(
	userid integer,
	password character varying)
    RETURNS void
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 0
AS $BODY$

UPDATE "Users" SET "Password" = password WHERE "UserID" = userid

$BODY$;

ALTER FUNCTION public."UpdateUserPasswordById"(integer, character varying)
    OWNER TO postgres;
