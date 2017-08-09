-- FUNCTION: public."AddContact"(integer, character varying, character varying, character varying, integer)

-- DROP FUNCTION public."AddContact"(integer, character varying, character varying, character varying, integer);

CREATE OR REPLACE FUNCTION public.AddContact(
	userid integer,
	firstname character varying,
	lastname character varying DEFAULT NULL::character varying,
	phonenumber character varying DEFAULT NULL::character varying,
	groupid integer DEFAULT NULL::integer)
    RETURNS void
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 0
AS $BODY$

INSERT INTO "Contacts" ("UserID", "FirstName", "LastName", "PhoneNumber", "GroupID") VALUES (userid, firstname, lastname, phonenumber, groupid)

$BODY$;

ALTER FUNCTION public."AddContact"(integer, character varying, character varying, character varying, integer)
    OWNER TO postgres;
