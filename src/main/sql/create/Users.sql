-- Table: public."Users"

-- DROP TABLE public."Users";

CREATE TABLE public."Users"
(
    "UserID" integer NOT NULL DEFAULT nextval('"Users_UserID_seq"'::regclass),
    "Login" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "Password" character varying(200) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY ("UserID")
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Users"
    OWNER to postgres;

-- Index: UserId

-- DROP INDEX public."UserId";

CREATE UNIQUE INDEX "UserId"
    ON public."Users" USING btree
    (UserID)
    TABLESPACE pg_default;

ALTER TABLE public."Users"
    CLUSTER ON "UserId";

-- Index: UserLogin

-- DROP INDEX public."UserLogin";

CREATE UNIQUE INDEX "UserLogin"
    ON public."Users" USING btree
    (Login COLLATE pg_catalog."default")
    TABLESPACE pg_default;