-- FUNCTION: public."DeleteContact"(integer, integer)

-- DROP FUNCTION public."DeleteContact"(integer, integer);

CREATE OR REPLACE FUNCTION public.DeleteContact(
	user_id integer,
	contact_id integer)
    RETURNS void
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 0
AS $BODY$

DELETE FROM "Contacts" WHERE "ContactID" = contact_id AND "UserID" = user_id

$BODY$;

ALTER FUNCTION public."DeleteContact"(integer, integer)
    OWNER TO postgres;
