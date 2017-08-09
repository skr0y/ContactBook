-- Table: public."Groups"

-- DROP TABLE public."Groups";

CREATE TABLE public."Groups"
(
    "GroupName" character varying(500) COLLATE pg_catalog."default" NOT NULL,
    "UserID" integer NOT NULL,
    "GroupID" integer NOT NULL DEFAULT nextval('"Groups_GroupID_seq"'::regclass),
    CONSTRAINT "Groups_pkey" PRIMARY KEY ("GroupID"),
    CONSTRAINT "Groups_UserID_fkey" FOREIGN KEY ("UserID")
        REFERENCES public."Users" ("UserID") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Groups"
    OWNER to postgres;

-- Index: GroupFkUser

-- DROP INDEX public."GroupFkUser";

CREATE INDEX "GroupFkUser"
    ON public."Groups" USING btree
    (UserID)
    TABLESPACE pg_default;

-- Index: GroupId

-- DROP INDEX public."GroupId";

CREATE UNIQUE INDEX "GroupId"
    ON public."Groups" USING btree
    (GroupID)
    TABLESPACE pg_default;

ALTER TABLE public."Groups"
    CLUSTER ON "GroupId";