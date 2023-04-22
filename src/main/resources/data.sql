DROP TABLE IF EXISTS public.tenant;

CREATE TABLE IF NOT EXISTS public.tenant
(
    tenant_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    domine character varying(255)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tenant
    OWNER to root;

INSERT INTO public.tenant(
	tenant_id, domine)
	VALUES ('psg','trading.psg.deliver.ar');
INSERT INTO public.tenant(
	tenant_id, domine)
	VALUES ('default','trading.deliver.ar');
INSERT INTO public.tenant(
	tenant_id, domine)
	VALUES ('river','trading.river.deliver.ar');