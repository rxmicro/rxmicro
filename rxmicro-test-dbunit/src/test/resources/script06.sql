INSERT INTO public.account (id,email) VALUES
    (nextval('account_seq'),'richard.hendricks@piedpiper.com'),
    (nextval('account_seq'),'bertram.gilfoyle@piedpiper.com' ),
    (nextval('account_seq'),'dinesh.chugtai@piedpiper.com'   );

INSERT INTO public.account (id,email) VALUES
    (nextval('account_seq'),'richard;hendricks;@piedpiper.com');

DELETE FROM public.order;
