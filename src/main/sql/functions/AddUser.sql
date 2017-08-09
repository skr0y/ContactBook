-- FUNCTION: public."AddUser"(character varying, character varying)

-- DROP FUNCTION public."AddUser"(character varying, character varying);

CREATE OR REPLACE FUNCTION public.AddUser(
	login character varying,
	password character varying)
    RETURNS void
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 0
AS $BODY$

INSERT INTO "Users" ("Login", "Password") VALUES (login, password)

$BODY$;

ALTER FUNCTION public."AddUser"(character varying, character varying)
    OWNER TO postgres;
