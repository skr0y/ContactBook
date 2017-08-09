-- FUNCTION: public."UpdateContact"(integer, integer, character varying, character varying, character varying, integer)

-- DROP FUNCTION public."UpdateContact"(integer, integer, character varying, character varying, character varying, integer);

CREATE OR REPLACE FUNCTION public.UpdateContact(
	contactid integer,
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

UPDATE "Contacts" SET "FirstName" = firstname, "LastName" = lastname, "PhoneNumber" = phonenumber, "GroupID" = groupid, "UserID" = userid WHERE "ContactID" = contactid

$BODY$;

ALTER FUNCTION public."UpdateContact"(integer, integer, character varying, character varying, character varying, integer)
    OWNER TO postgres;
