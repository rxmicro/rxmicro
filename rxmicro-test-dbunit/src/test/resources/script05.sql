CREATE TYPE public.role AS ENUM (
    'CEO', 'Lead_Engineer', 'Systems_Architect'
);

ALTER TYPE public.role OWNER TO rxmicro;

CREATE TABLE public.account (
	id int8 NOT NULL,
	email varchar(50) NOT NULL,
	CONSTRAINT account_email_uniq UNIQUE (email),
	CONSTRAINT account_id_pk PRIMARY KEY (id)
);

ALTER TABLE public.account OWNER TO rxmicro;
GRANT ALL ON TABLE public.account TO rxmicro;

CREATE SEQUENCE public.account_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	CACHE 1
	NO CYCLE;

ALTER SEQUENCE public.account_seq OWNER TO rxmicro;
GRANT ALL ON SEQUENCE public.account_seq TO rxmicro;
