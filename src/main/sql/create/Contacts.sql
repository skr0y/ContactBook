-- Table: public."Contacts"

-- DROP TABLE public."Contacts";

CREATE TABLE public."Contacts"
(
    "FirstName" character varying(200) COLLATE pg_catalog."default" NOT NULL,
    "LastName" character varying(200) COLLATE pg_catalog."default",
    "PhoneNumber" character varying(20) COLLATE pg_catalog."default",
    "GroupID" integer,
    "UserID" integer NOT NULL,
    "ContactID" integer NOT NULL DEFAULT nextval('"Contacts_ContactID_seq"'::regclass),
    CONSTRAINT "Contacts_pkey" PRIMARY KEY ("ContactID"),
    CONSTRAINT "Contacts_GroupID_fkey" FOREIGN KEY ("GroupID")
        REFERENCES public."Groups" ("GroupID") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT "Contacts_UserID_fkey" FOREIGN KEY ("UserID")
        REFERENCES public."Users" ("UserID") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Contacts"
    OWNER to postgres;

-- Index: ContactFkGroup

-- DROP INDEX public."ContactFkGroup";

CREATE INDEX "ContactFkGroup"
    ON public."Contacts" USING btree
    (GroupID)
    TABLESPACE pg_default;

-- Index: ContactFkUser

-- DROP INDEX public."ContactFkUser";

CREATE INDEX "ContactFkUser"
    ON public."Contacts" USING btree
    (UserID)
    TABLESPACE pg_default;

-- Index: ContactId

-- DROP INDEX public."ContactId";

CREATE UNIQUE INDEX "ContactId"
    ON public."Contacts" USING btree
    (ContactID)
    TABLESPACE pg_default;

ALTER TABLE public."Contacts"
    CLUSTER ON "ContactId";