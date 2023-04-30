INSERT INTO public.tenant(tenant_id, domain)
SELECT 'psg', 'trading.psg.deliver.ar'
WHERE NOT EXISTS (SELECT 1 FROM public.tenant WHERE tenant_id = 'psg');
INSERT INTO public.tenant(tenant_id, domain)
SELECT 'default', 'trading.deliver.ar'
WHERE NOT EXISTS (SELECT 1 FROM public.tenant WHERE tenant_id = 'default');
INSERT INTO public.tenant(tenant_id, domain)
SELECT 'lazio', 'lazio.trading.psg.deliver.ar'
WHERE NOT EXISTS (SELECT 1 FROM public.tenant WHERE tenant_id = 'lazio');


INSERT INTO public.symbol (symbol, istoken, name) VALUES ('USD', false, 'FIAT') ON CONFLICT (symbol) DO NOTHING;
INSERT INTO public.symbol (symbol, istoken, name) VALUES ('BAR', true, 'FC Barcelona Fan Token') ON CONFLICT (symbol) DO NOTHING
INSERT INTO public.symbol (symbol, istoken, name) VALUES ('PSG', true, 'Paris Saint-Germain Fan Token') ON CONFLICT (symbol) DO NOTHING;
INSERT INTO public.symbol (symbol, istoken, name) VALUES ('LAZIO', true, 'Lazio Fan Token') ON CONFLICT (symbol) DO NOTHING;
INSERT INTO public.symbol (symbol, istoken, name) VALUES ('CITY', true, 'Manchester City Fan Token') ON CONFLICT (symbol) DO NOTHING;
INSERT INTO public.symbol (symbol, istoken, name) VALUES ('SANTOS', true, 'Santos FC Fan Token') ON CONFLICT (symbol) DO NOTHING;
INSERT INTO public.symbol (symbol, istoken, name) VALUES ('AFC', true, 'Arsenal Fan Token') ON CONFLICT (symbol) DO NOTHING;
INSERT INTO public.symbol (symbol, istoken, name) VALUES ('ACM', true, 'AC Milan Fan Token') ON CONFLICT (symbol) DO NOTHING;
INSERT INTO public.symbol (symbol, istoken, name) VALUES ('JUV', true, 'Juventus Fan Token') ON CONFLICT (symbol) DO NOTHING;
INSERT INTO public.symbol (symbol, istoken, name) VALUES ('NAP', true, 'Napoli Fan Token') ON CONFLICT (symbol) DO NOTHING;
INSERT INTO public.symbol (symbol, istoken, name) VALUES ('PORTO', true, 'FC Porto Fan Token') ON CONFLICT (symbol) DO NOTHING;

