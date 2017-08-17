-- FUNCTION: public."RemoveGroupIdFromContacts"(integer)

-- DROP FUNCTION public."RemoveGroupIdFromContacts"(integer);

CREATE OR REPLACE FUNCTION public.RemoveGroupIdFromContacts(
	param integer)
    RETURNS void
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 0
AS $BODY$

UPDATE "Contacts" SET "GroupID" = NULL WHERE "GroupID" = param

$BODY$;

ALTER FUNCTION public."RemoveGroupIdFromContacts"(integer)
    OWNER TO postgres;
