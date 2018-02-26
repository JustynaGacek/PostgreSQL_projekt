

CREATE SEQUENCE projekt.sponsorzyfirmowi_id_s_firmowgo_seq;

CREATE TABLE projekt.SponsorzyFirmowi (
                id_s_firmowgo INTEGER NOT NULL DEFAULT nextval('projekt.sponsorzyfirmowi_id_s_firmowgo_seq'),
                nazwa_firmy VARCHAR NOT NULL,
                adres_siedziby VARCHAR NOT NULL,
                CONSTRAINT id_s_firmowego PRIMARY KEY (id_s_firmowgo)
);


ALTER SEQUENCE projekt.sponsorzyfirmowi_id_s_firmowgo_seq OWNED BY projekt.SponsorzyFirmowi.id_s_firmowgo;

CREATE SEQUENCE projekt.sponsorzyprywatni_id_s_prywatnego_seq;

CREATE TABLE projekt.SponsorzyPrywatni (
                id_s_prywatnego INTEGER NOT NULL DEFAULT nextval('projekt.sponsorzyprywatni_id_s_prywatnego_seq'),
                imie projekt.wielka_litera_ok NOT NULL,
                nazwisko projekt.wielka_litera_ok NOT NULL,
                adres VARCHAR NOT NULL,
                CONSTRAINT id_prywatnego PRIMARY KEY (id_s_prywatnego)
);


ALTER SEQUENCE projekt.sponsorzyprywatni_id_s_prywatnego_seq OWNED BY projekt.SponsorzyPrywatni.id_s_prywatnego;

CREATE SEQUENCE projekt.trenerzy_id_trenera_seq_1;

CREATE TABLE projekt.Trenerzy (
                id_trenera INTEGER NOT NULL DEFAULT nextval('projekt.trenerzy_id_trenera_seq_1'),
                imie projekt.wielka_litera_ok NOT NULL,
                nazwisko projekt.wielka_litera_ok NOT NULL,
                nr_telefonu projekt.numer_telefonu_ok NOT NULL,
                numer_dowodu projekt.numer_dowodu_ok NOT NULL,
                adres VARCHAR NOT NULL,
                CONSTRAINT id_trenera PRIMARY KEY (id_trenera)
);


ALTER SEQUENCE projekt.trenerzy_id_trenera_seq_1 OWNED BY projekt.Trenerzy.id_trenera;

CREATE SEQUENCE projekt.druzyny_id_druzyny_seq_1;

CREATE TABLE projekt.Druzyny (
                id_druzyny INTEGER NOT NULL DEFAULT nextval('projekt.druzyny_id_druzyny_seq_1'),
                nazwa VARCHAR NOT NULL,
                id_trenera INTEGER NOT NULL,
                min_wiek projekt.wiek_ok NOT NULL,
                max_wiek projekt.wiek_ok NOT NULL,
                CONSTRAINT id_druzyny PRIMARY KEY (id_druzyny)
);


ALTER SEQUENCE projekt.druzyny_id_druzyny_seq_1 OWNED BY projekt.Druzyny.id_druzyny;

CREATE SEQUENCE projekt.treningi_id_treningu_seq;

CREATE TABLE projekt.Treningi (
                id_treningu INTEGER NOT NULL DEFAULT nextval('projekt.treningi_id_treningu_seq'),
                dzien projekt.dzien_ok NOT NULL,
                godzina TIME NOT NULL,
                czas_trwania projekt.dlugosc_treningu_ok NOT NULL,
                numer_sali projekt.sala_ok NOT NULL,
                id_trenera INTEGER NOT NULL,
                id_druzyny INTEGER NOT NULL,
                CONSTRAINT id_terminu_treningu PRIMARY KEY (id_treningu)
);


ALTER SEQUENCE projekt.treningi_id_treningu_seq OWNED BY projekt.Treningi.id_treningu;


CREATE TABLE projekt.DruzynaSponsorFirmowy (
                id_druzyny INTEGER NOT NULL,
                id_s_firmowgo INTEGER NOT NULL,
                CONSTRAINT druzynasponsorfirmowy_pk PRIMARY KEY (id_druzyny, id_s_firmowgo)
);


CREATE TABLE projekt.DruzynaSponsorPrywatny (
                id_druzyny INTEGER NOT NULL,
                id_s_prywatnego INTEGER NOT NULL,
                CONSTRAINT druzynasponsorprywatny_pk PRIMARY KEY (id_druzyny, id_s_prywatnego)
);


CREATE SEQUENCE projekt.meczerozegrane_id_m_rozegranego_seq;

CREATE TABLE projekt.MeczeRozegrane (
                id_m_rozegranego INTEGER NOT NULL DEFAULT nextval('projekt.meczerozegrane_id_m_rozegranego_seq'),
                data projekt.data_przeszla_ok NOT NULL,
                punkty_druzyny projekt.dodatnia_liczba_ok NOT NULL,
                punkty_przeciwnika projekt.dodatnia_liczba_ok NOT NULL,
                nazwa_przeciwnika VARCHAR NOT NULL,
                id_druzyny INTEGER NOT NULL,
                CONSTRAINT id_m_rozegr PRIMARY KEY (id_m_rozegranego)
);


ALTER SEQUENCE projekt.meczerozegrane_id_m_rozegranego_seq OWNED BY projekt.MeczeRozegrane.id_m_rozegranego;

CREATE SEQUENCE projekt.meczeplanowane_id_m_planowanego_seq;

CREATE TABLE projekt.MeczePlanowane (
                id_m_planowanego INTEGER NOT NULL DEFAULT nextval('projekt.meczeplanowane_id_m_planowanego_seq'),
                data projekt.data_przyszla_ok NOT NULL,
                miejsce VARCHAR NOT NULL,
                nazwa_przeciwnika VARCHAR NOT NULL,
                id_druzyny INTEGER NOT NULL,
                CONSTRAINT id_m_plan PRIMARY KEY (id_m_planowanego)
);


ALTER SEQUENCE projekt.meczeplanowane_id_m_planowanego_seq OWNED BY projekt.MeczePlanowane.id_m_planowanego;

CREATE SEQUENCE projekt.opiekunowie_id_opiekuna_seq;

CREATE TABLE projekt.Opiekunowie (
                id_opiekuna INTEGER NOT NULL DEFAULT nextval('projekt.opiekunowie_id_opiekuna_seq'),
                imie projekt.wielka_litera_ok NOT NULL,
                nazwisko projekt.wielka_litera_ok NOT NULL,
                nr_telefonu projekt.numer_telefonu_ok NOT NULL,
                nr_dowodu projekt.numer_dowodu_ok NOT NULL,
                adres VARCHAR NOT NULL,
                CONSTRAINT id_opiekuna PRIMARY KEY (id_opiekuna)
);


ALTER SEQUENCE projekt.opiekunowie_id_opiekuna_seq OWNED BY projekt.Opiekunowie.id_opiekuna;

CREATE SEQUENCE projekt.pozycje_id_pozycji_seq;

CREATE TABLE projekt.Pozycje (
                id_pozycji INTEGER NOT NULL DEFAULT nextval('projekt.pozycje_id_pozycji_seq'),
                nazwa_pozycji VARCHAR NOT NULL,
                CONSTRAINT id_pozycji PRIMARY KEY (id_pozycji)
);


ALTER SEQUENCE projekt.pozycje_id_pozycji_seq OWNED BY projekt.Pozycje.id_pozycji;

CREATE SEQUENCE projekt.dzieci_id_dziecka_seq;

CREATE TABLE projekt.Dzieci (
                id_dziecka INTEGER NOT NULL DEFAULT nextval('projekt.dzieci_id_dziecka_seq'),
                imie projekt.wielka_litera_ok NOT NULL,
                nazwisko projekt.wielka_litera_ok NOT NULL,
                wiek projekt.wiek_ok NOT NULL,
                data_przyjecia projekt.data_przeszla_ok NOT NULL,
                id_pozycji INTEGER NOT NULL,
                id_druzyny INTEGER NOT NULL,
                id_opiekuna INTEGER NOT NULL,
                CONSTRAINT id_dziecka PRIMARY KEY (id_dziecka)
);


ALTER SEQUENCE projekt.dzieci_id_dziecka_seq OWNED BY projekt.Dzieci.id_dziecka;


ALTER TABLE projekt.DruzynaSponsorFirmowy ADD CONSTRAINT sponsorzyfirmowi_druzynasponsorfirmowy_fk
FOREIGN KEY (id_s_firmowgo)
REFERENCES projekt.SponsorzyFirmowi (id_s_firmowgo)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.DruzynaSponsorPrywatny ADD CONSTRAINT sponsorzy_druzynasponsor_fk
FOREIGN KEY (id_s_prywatnego)
REFERENCES projekt.SponsorzyPrywatni (id_s_prywatnego)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.Druzyny ADD CONSTRAINT trenerzy_druzyny_fk
FOREIGN KEY (id_trenera)
REFERENCES projekt.Trenerzy (id_trenera)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.Treningi ADD CONSTRAINT trenerzy_treningi_fk
FOREIGN KEY (id_trenera)
REFERENCES projekt.Trenerzy (id_trenera)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.Dzieci ADD CONSTRAINT druzyny_dzieic_fk
FOREIGN KEY (id_druzyny)
REFERENCES projekt.Druzyny (id_druzyny)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.MeczePlanowane ADD CONSTRAINT druzyny_meczrozgrywany_fk
FOREIGN KEY (id_druzyny)
REFERENCES projekt.Druzyny (id_druzyny)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.MeczeRozegrane ADD CONSTRAINT druzyny_meczerozegrane_fk
FOREIGN KEY (id_druzyny)
REFERENCES projekt.Druzyny (id_druzyny)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.DruzynaSponsorPrywatny ADD CONSTRAINT druzyny_druzynasponsor_fk
FOREIGN KEY (id_druzyny)
REFERENCES projekt.Druzyny (id_druzyny)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.DruzynaSponsorFirmowy ADD CONSTRAINT druzyny_druzynasponsorfirmowy_fk
FOREIGN KEY (id_druzyny)
REFERENCES projekt.Druzyny (id_druzyny)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.Treningi ADD CONSTRAINT druzyny_treningi_fk
FOREIGN KEY (id_druzyny)
REFERENCES projekt.Druzyny (id_druzyny)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;


ALTER TABLE projekt.Dzieci ADD CONSTRAINT rodzic_dzieic_fk
FOREIGN KEY (id_opiekuna)
REFERENCES projekt.Opiekunowie (id_opiekuna)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.Dzieci ADD CONSTRAINT pozycja_dzieic_fk
FOREIGN KEY (id_pozycji)
REFERENCES projekt.Pozycje (id_pozycji)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
