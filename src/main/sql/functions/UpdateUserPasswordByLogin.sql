-- FUNCTION: public."UpdateUserPasswordByLogin"(character varying, character varying)

-- DROP FUNCTION public."UpdateUserPasswordByLogin"(character varying, character varying);

CREATE OR REPLACE FUNCTION public.UpdateUserPasswordByLogin(
	login character varying,
	password character varying)
    RETURNS void
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 0
AS $BODY$

UPDATE "Users" SET "Password" = password WHERE "Login" = login

$BODY$;

ALTER FUNCTION public."UpdateUserPasswordByLogin"(character varying, character varying)
    OWNER TO postgres;
