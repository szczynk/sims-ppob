-- user
CREATE TABLE users (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255),
    profile_image character varying(255)
);

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

CREATE SEQUENCE users_id_seq
    START WITH 0
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE users_id_seq OWNED BY users.id;

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);

-- banner
CREATE TABLE banners (
    id bigint NOT NULL,
    banner_image character varying(255),
    banner_name character varying(255),
    description character varying(255)
);

ALTER TABLE ONLY banners
    ADD CONSTRAINT banners_pkey PRIMARY KEY (id);

CREATE SEQUENCE banners_id_seq
    START WITH 0
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE banners_id_seq OWNED BY banners.id;

ALTER TABLE ONLY banners ALTER COLUMN id SET DEFAULT nextval('banners_id_seq'::regclass);

-- service
CREATE TABLE services (
    service_code character varying(255) NOT NULL,
    service_image character varying(255),
    service_name character varying(255),
    service_tariff bigint
);

ALTER TABLE ONLY services
    ADD CONSTRAINT services_pkey PRIMARY KEY (service_code);

-- balance
CREATE TABLE balances (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    balance_amount bigint
);

ALTER TABLE ONLY balances
    ADD CONSTRAINT balances_pkey PRIMARY KEY (id);

ALTER TABLE ONLY balances
    ADD CONSTRAINT fk_user_balance FOREIGN KEY (user_id) REFERENCES users(id);

CREATE SEQUENCE balances_id_seq
    START WITH 0
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE balances_id_seq OWNED BY balances.id;

ALTER TABLE ONLY balances ALTER COLUMN id SET DEFAULT nextval('balances_id_seq'::regclass);

-- transaction
CREATE TABLE transactions (
    id bigint NOT NULL,
    created_on timestamp(6) without time zone,
    description character varying(255),
    invoice_number character varying(255),
    total_amount bigint,
    transaction_type character varying(255),
    service_code character varying(255),
    user_id bigint NOT NULL
);

ALTER TABLE ONLY transactions
    ADD CONSTRAINT transactions_pkey PRIMARY KEY (id);

ALTER TABLE ONLY transactions
    ADD CONSTRAINT fk_transaction_service FOREIGN KEY (service_code) REFERENCES services(service_code);

ALTER TABLE ONLY transactions
    ADD CONSTRAINT fk_transaction_user FOREIGN KEY (user_id) REFERENCES users(id);

CREATE SEQUENCE transactions_id_seq
    START WITH 0
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE transactions_id_seq OWNED BY transactions.id;

ALTER TABLE ONLY transactions ALTER COLUMN id SET DEFAULT nextval('transactions_id_seq'::regclass);

CREATE FUNCTION create_invoice_number() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    NEW.invoice_number := 'INV-' || to_char(NEW.created_on, 'DDMMYYYY') || '-' || currval('transactions_id_seq');
    RETURN NEW;
END;
$$;

CREATE TRIGGER set_invoice_number BEFORE INSERT ON transactions FOR EACH ROW EXECUTE FUNCTION create_invoice_number();

-- insert banners and services
insert into banners (banner_image, banner_name, description) values 
('https://nutech-integrasi.app/dummy-banner-1.jpg', 'Banner 1', '1 Lerem Ipsum Dolor sit amet'),
('https://nutech-integrasi.app/dummy-banner-2.jpg', 'Banner 2', '2 Lerem Ipsum Dolor sit amet'),
('https://nutech-integrasi.app/dummy-banner-3.jpg', 'Banner 3', '3 Lerem Ipsum Dolor sit amet'),
('https://nutech-integrasi.app/dummy-banner-4.jpg', 'Banner 4', '4 Lerem Ipsum Dolor sit amet'),
('https://nutech-integrasi.app/dummy-banner-5.jpg', 'Banner 5', '5 Lerem Ipsum Dolor sit amet'),
('https://nutech-integrasi.app/dummy-banner-6.jpg', 'Banner 6', '6 Lerem Ipsum Dolor sit amet');

insert into services (service_code, service_name, service_image, service_tariff) values 
('PAJAK', 'Pajak PBB', 'https://nutech-integrasi.app/dummy-pajak.jpg', 40000),
('PLN', 'Listrik', 'https://nutech-integrasi.app/dummy-listrik.jpg', 10000),
('PDAM', 'PDAM Berlangganan', 'https://nutech-integrasi.app/dummy-pdam.jpg', 40000),
('PULSA', 'Pulsa', 'https://nutech-integkrasi.app/dummy-pulsa.jpg', 40000),
('PGN', 'PGN Berlangganan', 'https://nutech-integrasi.app/dummy-pgn.jpg', 50000),
('MUSIK', 'Musik Berlangganan', 'https://nutech-integrasi.app/dummy-musik.jpg', 50000),
('TV', 'TV Berlangganan', 'https://nutech-integrasi.app/dummy-tv.jpg', 50000),
('PAKET_DATA','Paket data','https://nutech-integrasi.app/dummy-paket-data.jpg',50000),
('VOUCHER_GAME','Voucher Game','https://nutech-integrasi.app/dummy-voucher-game.jpg',100000),
('VOUCHER_MAKANAN','Voucher Makanan','https://nutech-integrasi.app/dummy-voucher-makanan.jpg',100000),
('QURBAN','Qurban','https://nutech-integrasi.app/dummy-qurban.jpg',200000),
('ZAKAT','Zakat','https://nutech-integrasi.app/dummy-zakat.jpg',300000);