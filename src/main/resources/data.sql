INSERT INTO public.tenant(tenant_id, domain)
SELECT 'psg', 'trading.psg.deliver.ar'
WHERE NOT EXISTS (SELECT 1 FROM public.tenant WHERE tenant_id = 'psg');
INSERT INTO public.tenant(tenant_id, domain)
SELECT 'default', 'trading.deliver.ar'
WHERE NOT EXISTS (SELECT 1 FROM public.tenant WHERE tenant_id = 'default');
INSERT INTO public.tenant(tenant_id, domain)
SELECT 'lazio', 'lazio.trading.psg.deliver.ar'
WHERE NOT EXISTS (SELECT 1 FROM public.tenant WHERE tenant_id = 'lazio');
