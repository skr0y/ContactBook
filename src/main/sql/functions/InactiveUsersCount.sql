-- FUNCTION: public."InactiveUsersCount"()

-- DROP FUNCTION public."InactiveUsersCount"();

CREATE OR REPLACE FUNCTION public.InactiveUsersCount(
	)
    RETURNS SETOF character varying 
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 1000
AS $BODY$

SELECT users."Login" FROM "Users" users JOIN(SELECT "UserID" FROM "Contacts" GROUP BY "UserID" HAVING COUNT(*) < 10) x ON users."UserID" = x."UserID"

$BODY$;

ALTER FUNCTION public."InactiveUsersCount"()
    OWNER TO postgres;
