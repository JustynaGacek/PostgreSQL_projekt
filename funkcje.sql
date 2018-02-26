--DOMENY

CREATE DOMAIN projekt.numer_dowodu_ok AS VARCHAR CONSTRAINT "poprawna forma numeru dowodu 3 duże litery, 6 cyfr" CHECK (VALUE ~ '^[A-Z]{3}[0-9]{6}$');

CREATE DOMAIN projekt.numer_telefonu_ok AS VARCHAR CONSTRAINT "numer telefonu powinien mieć 9 cyfr" CHECK (VALUE ~ '^[0-9]{9}$');

CREATE DOMAIN projekt.wiek_ok AS INTEGER CONSTRAINT "do szkoły przyjmowane są dzieci w wieku od 3 do 18 lat" CHECK (VALUE>=3 AND VALUE<=18);

CREATE DOMAIN projekt.data_przeszla_ok AS DATE CONSTRAINT "wymagane jest wydarzenie przeszłe a nie przyszłe" CHECK (CAST(VALUE as DATE)<=Now());

CREATE DOMAIN projekt.data_przyszla_ok AS DATE CONSTRAINT "wymagane jest wydarzenie przyszłe a nie przeszłe" CHECK (CAST(VALUE as DATE)>Now());

CREATE DOMAIN projekt.wielka_litera_ok AS VARCHAR CONSTRAINT "nazwa własna musi zaczynać się z dużej litery" CHECK (VALUE ~ '^[A-ZŁŻŹĆŃŚĆ]{1}[a-zęćóąśłżźćń-]*$');

CREATE DOMAIN projekt.dodatnia_liczba_ok AS INTEGER CONSTRAINT "należy podać liczbę z przedziału [0,10]" CHECK (VALUE>=0 AND VALUE<=10);

CREATE DOMAIN projekt.sala_ok AS INTEGER CONSTRAINT "należy wybrać salę o numerze 1 lub 2" CHECK (VALUE>=1 AND VALUE<=2);

CREATE DOMAIN projekt.dzien_ok AS VARCHAR CONSTRAINT "niepoprawny dzień, w niedziele szkoła jest zamknięta" CHECK (VALUE IN('poniedziałek', 'wtorek', 'środa', 'czwartek', 'piątek', 'sobota'));

CREATE DOMAIN projekt.liczba_wieksza_od_0 AS INTEGER CONSTRAINT "należy podać liczbę dodatnią" CHECK (VALUE>=1);

CREATE DOMAIN projekt.dlugosc_treningu_ok AS REAL CONSTRAINT "trening może trwać: 1.0, 1.5, 2.0, 2.5, 3.0 h" CHECK (VALUE IN(1.0, 1.5, 2.0, 2.5, 3.0));


--FUNKCJA OBLICZJĄCA KONIEC TRENINGU NA PODSTAWIE POCZĄTKU ORAZ DŁUGOŚCI TRWANIA
CREATE OR REPLACE FUNCTION projekt.czasZakonczenia(TIME, float8) RETURNS TIME LANGUAGE plpgsql AS '
	DECLARE
	dlugosc ALIAS FOR $2;
	rozpoczecie ALIAS FOR $1;
	czesc_dziesietna float8 := ABS(dlugosc)-FLOOR(ABS(dlugosc));
	zakonczenie TIME;

	BEGIN
		IF czesc_dziesietna=0.0 THEN
			zakonczenie = rozpoczecie + dlugosc * INTERVAL ''1 hour'';
			RETURN zakonczenie;
		ELSE
			zakonczenie := rozpoczecie + FLOOR(dlugosc) * INTERVAL ''1 hour'' + 30 * INTERVAL ''1 minute'';
			RETURN zakonczenie;
		END IF;
	END; 
';

--WIDOK ZAWIERAJĄCY: NAZWĘ DRUŻYNY, ILOŚĆ MECZY ROZEGRANYCH, ILOŚĆ MECZY WYGRANYCH, ILOŚĆ REMISÓW, ILOŚĆ MECZY PRZEGRANYCH ORAZ PROCENT WYGRANYCH
--W ZAPYTANIU UŻYTA JEST FUNKCJA COALESCE KTÓRA PARZUJE NULL DO WARTOŚCI 0
--W ZAPYTANIU UŻYTA ZOSTAŁA INSTRUKCJA CASE, ABY ZAPOBIEC DZIELENIU PRZEZ 0 PODCZAS WYLICZNIA PROCENTU WYGRANYCH
--WYKORZYSTANE ZŁĄCZENIA TABEL 
--GRUPOWANIE PO ID DRUŻYNY
CREATE OR REPLACE VIEW statystyka_mecze AS
SELECT d.nazwa, COALESCE(COUNT(mr.data),0) AS "mecze rozegrane", COALESCE(tab1.wygrana,0) AS "mecze wygrane", COALESCE(tab2.remis,0) AS "mecze zremisowane", COALESCE(tab3.przegrana,0) AS "mecze przegrane",  
CASE COALESCE(COUNT(mr.data),0) WHEN 0 THEN 0 ELSE (COALESCE(tab1.wygrana,0)*100/COALESCE(COUNT(mr.data),0)) END AS "Procent wygranych"
FROM druzyny d LEFT JOIN meczerozegrane mr USING (id_druzyny) 
LEFT JOIN (SELECT mr1.id_druzyny, COUNT(mr1.id_druzyny) AS wygrana FROM meczerozegrane mr1 WHERE mr1.punkty_druzyny>mr1.punkty_przeciwnika GROUP BY id_druzyny) AS tab1 USING(id_druzyny) 
LEFT JOIN (SELECT mr2.id_druzyny, COUNT(mr2.id_druzyny) AS remis FROM meczerozegrane mr2 WHERE mr2.punkty_druzyny=mr2.punkty_przeciwnika GROUP BY id_druzyny) AS tab2 USING(id_druzyny) 
LEFT JOIN (SELECT mr3.id_druzyny, COUNT(mr3.id_druzyny) AS przegrana FROM meczerozegrane mr3 WHERE mr3.punkty_druzyny<mr3.punkty_przeciwnika GROUP BY id_druzyny) AS tab3 USING(id_druzyny)
GROUP BY d.nazwa, tab1.wygrana, tab2.remis, tab3.przegrana, d.id_druzyny ORDER BY d.id_druzyny;

--WIDOK ZAWIERAJACY ILOSC ZAJETYCH MIEJSC W DRUŻYNACH
CREATE OR REPLACE VIEW pojemnosc_druzyn AS SELECT dr.nazwa AS "nazwa druzyny", COALESCE(liczeblosc.ilosc,0) AS "ilosc_dzieci" FROM druzyny dr 
LEFT JOIN (SELECT d.id_druzyny, COUNT(*) AS "ilosc" FROM dzieci d GROUP BY d.id_druzyny) AS liczeblosc USING (id_druzyny);

--widok przedstawia plan zajęć dla każdej z drużyn, dzień tygodnia, początek zajęć, koniec zajęć oraz numer sali
--do oblicznia końca zajęć uazywana funkcja czasZakonczenia(TIME, float8)
CREATE OR REPLACE VIEW harmonogram_druzyn AS SELECT d.nazwa, t1.dzien, t1.godzina AS poczatek, koniectreningu.koniec, t1.numer_sali FROM druzyny d 
LEFT JOIN treningi t1 USING (id_druzyny) 
LEFT JOIN (SELECT t.id_treningu, czasZakonczenia(t.godzina, t.czas_trwania) AS koniec from treningi t) AS koniectreningu USING(id_treningu) ORDER BY d.nazwa;

-- widok przedstawia rozkład godzin pracy dla każdego trenera, dzień tygodnia, początek zajęć, koniec zajęć oraz numer sali
--do oblicznia końca zajęć uazywana funkcja czasZakonczenia(TIME, float8)
CREATE OR REPLACE VIEW harmonogram_trenerow AS SELECT trener.nazwisko, trener.imie, t1.dzien, t1.godzina AS poczatek, koniectreningu.koniec, t1.numer_sali FROM trenerzy trener 
JOIN treningi t1 USING(id_trenera) 
JOIN (SELECT t.id_treningu, czasZakonczenia(t.godzina, t.czas_trwania) AS koniec from treningi t) AS koniectreningu USING(id_treningu) ORDER BY trener.nazwisko;

--widok przedstawia rozkład godzin, w których sale są zajęte
--do oblicznia końca zajęć uazywana funkcja czasZakonczenia(TIME, float8)
CREATE OR REPLACE VIEW harmonogram_sal AS SELECT t.numer_sali, t.dzien, t.godzina AS "poczatek", koniectreningu.koniec FROM treningi t 
LEFT JOIN (SELECT t1.id_treningu, czasZakonczenia(t1.godzina, t1.czas_trwania) AS koniec from treningi t1) AS koniectreningu USING(id_treningu) 
ORDER BY t.numer_sali, t.dzien, t.godzina;

--widok przedstawia jak dużo godzin tygodniowo ma każdy z trenerów oraz ile grup prowadzi
CREATE OR REPLACE VIEW obciazenie_trenerow AS SELECT trener.nazwisko, trener.imie, COALESCE(SUM(t.czas_trwania),0) AS "ilosc godzin", COALESCE(iloscdruzyn.ilosc,0) AS "ilosc druzyn" FROM trenerzy trener 
LEFT JOIN treningi t USING(id_trenera) 
LEFT JOIN (SELECT tr.id_trenera, COUNT(*) AS "ilosc" FROM druzyny tr GROUP BY tr.id_trenera) AS "iloscdruzyn" USING(id_trenera) 
GROUP BY t.id_trenera, trener.nazwisko, trener.imie, iloscdruzyn.ilosc;

--widok przedstawia zestawienie ilości sponsorów prywatnych, sponsorów firmowych oraz sumy sponsorów dla każdej z drużyn
CREATE VIEW druzyny_ilosc_sponsorow AS 
SELECT d.nazwa AS "nazwa druzyny", (COALESCE(sp.ilosc,0)) AS "ilość sponsorów prywatnych", (COALESCE(sf.ilosc,0)) AS "ilość sponsorów firmowych", (COALESCE(sp.ilosc, 0)+COALESCE(sf.ilosc, 0)) AS "ilosc wszystkich sponsorów" FROM druzyny d
LEFT JOIN (SELECT dsp.id_druzyny, COUNT(dsp.id_s_prywatnego) AS "ilosc" FROM druzynasponsorprywatny dsp GROUP BY dsp.id_druzyny) AS sp USING(id_druzyny) 
LEFT JOIN (SELECT dsf.id_druzyny, COUNT(dsf.id_s_firmowgo) AS "ilosc" FROM druzynasponsorfirmowy dsf GROUP BY dsf.id_druzyny) AS sf USING(id_druzyny)
GROUP BY d.nazwa, sp.ilosc, sf.ilosc;


--funkcja wyzwalająca
--sprawdza czy podczas dodawania dziecka została podana odpowiednia grupa w stosunku do wieki dziecka
--jeśli nie insert jest odrzucany  
CREATE OR REPLACE FUNCTION projekt.poprawnaGrupa() RETURNS TRIGGER AS
$BODY$
DECLARE
wybranaWiek integer;
max integer;
min integer;
wybranaD integer;
BEGIN
wybranaD := (NEW.id_druzyny);
wybranaWiek := (NEW.wiek);
max := (SELECT max_wiek FROM projekt.druzyny WHERE id_druzyny=wybranaD);
min := (SELECT min_wiek FROM projekt.druzyny WHERE id_druzyny=wybranaD);
IF wybranaWiek>=min AND wybranaWiek<=max THEN
	RETURN NEW;
ELSE
	RAISE EXCEPTION 'Dziecko w tym wieku nie moze nalezec do wybranej grupy';
	RETURN NULL;
END IF;

END; 
$BODY$
LANGUAGE 'plpgsql';

CREATE TRIGGER wyborGrupyTrigger
BEFORE INSERT ON projekt.dzieci
FOR EACH ROW
EXECUTE PROCEDURE projekt.poprawnaGrupa();

--funkcja wyzwalająca
--sprawdza czy podczas dodawania treningu dla danej drużyny został podany trener który powadzi tę grupę
--jeśli nie insert jest odrzucany, pojawi się komunikat z podpowiedzią
CREATE OR REPLACE FUNCTION projekt.poprawnyTrener() RETURNs TRIGGER AS
$BODY$
DECLARE
wybranaDruzyna int;
wybranyTrener int;
prawidlowyTrener int;
BEGIN
wybranaDruzyna := (SELECT id_druzyny FROM projekt.druzyny WHERE id_druzyny=new.id_druzyny);
wybranyTrener := (SELECT id_trenera FROM projekt.trenerzy WHERE id_trenera=new.id_trenera);
prawidlowyTrener := (SELECT id_trenera FROM projekt.druzyny WHERE id_druzyny=wybranaDruzyna);
IF wybranyTrener=prawidlowyTrener THEN
	RETURN NEW;
ELSE
	RAISE EXCEPTION 'Ta grupa ma innego trenera! Sprubuj wybrać id trenera %', prawidlowyTrener;
	RETURN NULL;
END IF;
END;
$BODY$
LANGUAGE 'plpgsql';

CREATE TRIGGER dodawanieTreninguPoprawnyTrener
BEFORE INSERT ON projekt.treningi
FOR EACH ROW
EXECUTE PROCEDURE projekt.poprawnyTrener();

--funkcja wyzwalająca
--sprawdza czy dodawana pozycja nie istnieje juz w tabeli
--jeśli istnieje insert jest odrzucany
CREATE OR REPLACE FUNCTION projekt.poprawnaPozycja() RETURNS TRIGGER AS
$BODY$
DECLARE
artykul record;
BEGIN
SELECT * INTO artykul FROM projekt.pozycje WHERE nazwa_pozycji=new.nazwa_pozycji;
IF NOT FOUND THEN
	RETURN NEW;
ELSE 
	RAISE EXCEPTION 'Ta pozycja istnieje już w tabeli!';
	RETURN NULL;
END IF;
END; 
$BODY$
LANGUAGE 'plpgsql';

CREATE TRIGGER sprawdzenieCzyIstniejePozycja
BEFORE INSERT ON projekt.pozycje
FOR EACH ROW
EXECUTE PROCEDURE projekt.poprawnaPozycja();

--funkcja wyzwalająca
--sprawdza czy maksymalna pojemność drużyn (czyli z założenia 20) nie wyczerpała się
--jeśli nie ma już miejsca w drużynie dziecko nie zostanie dodane
CREATE OR REPLACE FUNCTION projekt.poprawnaPojemnoscDruzyn() RETURNS TRIGGER AS
$BODY$
DECLARE
wybranaDruzyna int;
iloscDzieciWDruzynie int;
BEGIN
wybranaDruzyna := (NEW.id_druzyny);
iloscDzieciWDruzynie := (SELECT COUNT(*) FROM projekt.dzieci WHERE id_druzyny=wybranaDruzyna);
IF iloscDzieciWDruzynie<20 THEN
	RETURN NEW;
ELSE 
	RAISE EXCEPTION 'W tej drużynie jest juz 20 dzieci. Nazeży stwożyć nową drużynę.';
	RETURN NULL;
END IF;
END; 
$BODY$
LANGUAGE 'plpgsql';

CREATE TRIGGER sprawdzenieCzyJestMiejsceWGrupie
BEFORE INSERT ON projekt.dzieci
FOR EACH ROW
EXECUTE PROCEDURE projekt.poprawnaPojemnoscDruzyn();

--funkcja wyzwalająca
--sprawdza czy w podawanym czasie dana sala nie jest już zajęta
--jeśli jest zajęta trening nie zostanie dodany
CREATE OR REPLACE FUNCTION projekt.wolnyCzasWSali() RETURNS TRIGGER AS
$BODY$
DECLARE
wybranaSala int;
wybranyCzasRozpoczecia TIME;
wybranyCzasZakonczenia TIME;
wybranyDzien varchar;
wybranaDlugosc float8;
trening record;
kursor CURSOR FOR SELECT * FROM projekt.treningi WHERE numer_sali=new.numer_sali AND dzien=new.dzien;
BEGIN
wybranaSala := (NEW.numer_sali);
wybranyDzien := (NEW.dzien);
wybranyCzasRozpoczecia := (NEW.godzina);
wybranaDlugosc := (NEW.czas_trwania);
wybranyCzasZakonczenia := (czasZakonczenia(wybranyCzasRozpoczecia, wybranaDlugosc));

OPEN kursor;

LOOP FETCH kursor INTO trening;
EXIT WHEN NOT FOUND;
	IF (wybranaDlugosc=1.0) THEN
	IF ((wybranyCzasRozpoczecia>trening.godzina AND wybranyCzasRozpoczecia<czasZakonczenia(trening.godzina, trening.czas_trwania)) 
		OR (wybranyCzasZakonczenia>trening.godzina AND wybranyCzasZakonczenia<czasZakonczenia(trening.godzina, trening.czas_trwania))
		)
		THEN 
			RAISE EXCEPTION 'W tym terminie sala jest zajęta!. Sprawdź widok Harmonogram Sal w celu wybrania poprawnego terminu';
			RETURN NULL;
	END IF;
	END IF;
	IF (wybranaDlugosc=1.5) THEN
	IF ((wybranyCzasRozpoczecia>trening.godzina AND wybranyCzasRozpoczecia<czasZakonczenia(trening.godzina, trening.czas_trwania)) 
		OR (wybranyCzasZakonczenia>trening.godzina AND wybranyCzasZakonczenia<czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,0.5)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,0.5)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,1.0)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,1.0)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		)
		THEN 
			RAISE EXCEPTION 'W tym terminie sala jest zajęta!. Sprawdź widok Harmonogram Sal w celu wybrania poprawnego terminu';
			RETURN NULL;	
	END IF;
	END IF;
	IF (wybranaDlugosc=2.0) THEN
		IF ((wybranyCzasRozpoczecia>trening.godzina AND wybranyCzasRozpoczecia<czasZakonczenia(trening.godzina, trening.czas_trwania)) 
		OR (czasZakonczenia(wybranyCzasRozpoczecia,0.5)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,0.5)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,1.0)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,1.0)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,1.5)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,1.5)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		)
		THEN 
			RAISE EXCEPTION 'W tym terminie sala jest zajęta!. Sprawdź widok Harmonogram Sal w celu wybrania poprawnego terminu';
			RETURN NULL;	
	END IF;
	END IF;
	IF (wybranaDlugosc=2.5) THEN
	IF ((wybranyCzasRozpoczecia>trening.godzina AND wybranyCzasRozpoczecia<czasZakonczenia(trening.godzina, trening.czas_trwania)) 
		OR (wybranyCzasZakonczenia>trening.godzina AND wybranyCzasZakonczenia<czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,0.5)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,0.5)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,1.0)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,1.0)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,1.5)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,1.5)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,2.0)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,2.0)<=czasZakonczenia(trening.godzina, trening.czas_trwania))		
		)
		THEN 
			RAISE EXCEPTION 'W tym terminie sala jest zajęta!. Sprawdź widok Harmonogram Sal w celu wybrania poprawnego terminu';
			RETURN NULL;	
	END IF;
	END IF;
	IF (wybranaDlugosc=3.0) THEN
	IF ((wybranyCzasRozpoczecia>trening.godzina AND wybranyCzasRozpoczecia<czasZakonczenia(trening.godzina, trening.czas_trwania)) 
		OR (wybranyCzasZakonczenia>trening.godzina AND wybranyCzasZakonczenia<czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,0.5)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,0.5)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,1.0)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,1.0)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,1.5)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,1.5)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,2.0)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,2.0)<czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,2.5)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,2.5)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		)
		THEN 
			RAISE EXCEPTION 'W tym terminie sala jest zajęta!. Sprawdź widok Harmonogram Sal w celu wybrania poprawnego terminu';
			RETURN NULL;	
	END IF;
	END IF;
END LOOP;
CLOSE kursor;
RETURN NEW;
END; 
$BODY$
LANGUAGE 'plpgsql';

CREATE TRIGGER sprawdzenieCzyJestCzasWolnyWSali
BEFORE INSERT ON projekt.treningi
FOR EACH ROW
EXECUTE PROCEDURE projekt.wolnyCzasWSali();

--funkcja wyzwalająca
--sprawdza czy w podawanym czasie trener nie prowadzi innych zajęć
--jeśli trener w tym czasie jest zajęty trening nie zostanie dodany
CREATE OR REPLACE FUNCTION projekt.wolnyCzasTrenera() RETURNS TRIGGER AS
$BODY$
DECLARE
wybranyTrener int;
wybranyCzasRozpoczecia TIME;
wybranyCzasZakonczenia TIME;
wybranyDzien varchar;
wybranaDlugosc float8;
trening record;
kursor CURSOR FOR SELECT * FROM projekt.treningi WHERE id_trenera=new.id_trenera AND dzien=new.dzien;
BEGIN
wybranyTrener := (NEW.id_trenera);
wybranyDzien := (NEW.dzien);
wybranyCzasRozpoczecia := (NEW.godzina);
wybranaDlugosc := (NEW.czas_trwania);
wybranyCzasZakonczenia := (czasZakonczenia(wybranyCzasRozpoczecia, wybranaDlugosc));

OPEN kursor;
LOOP FETCH kursor INTO trening;
EXIT WHEN NOT FOUND;
	IF (wybranaDlugosc=1.0) THEN
	IF ((wybranyCzasRozpoczecia>trening.godzina AND wybranyCzasRozpoczecia<czasZakonczenia(trening.godzina, trening.czas_trwania)) 
		OR (wybranyCzasZakonczenia>trening.godzina AND wybranyCzasZakonczenia<czasZakonczenia(trening.godzina, trening.czas_trwania))
		)
		THEN 
			RAISE EXCEPTION 'W tym terminie wybrany trener jest zajęty!. Sprawdź widok Harmonogram Trenerów w celu wybrania poprawnego terminu';
			RETURN NULL;
	END IF;
	END IF;
	IF (wybranaDlugosc=1.5) THEN
	IF ((wybranyCzasRozpoczecia>trening.godzina AND wybranyCzasRozpoczecia<czasZakonczenia(trening.godzina, trening.czas_trwania)) 
		OR (wybranyCzasZakonczenia>trening.godzina AND wybranyCzasZakonczenia<czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,0.5)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,0.5)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,1.0)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,1.0)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		)
		THEN 
			RAISE EXCEPTION 'W tym terminie wybrany trener jest zajęty!. Sprawdź widok Harmonogram Trenerów w celu wybrania poprawnego terminu';
			RETURN NULL;	
	END IF;
	END IF;
	IF (wybranaDlugosc=2.0) THEN
		IF ((wybranyCzasRozpoczecia>trening.godzina AND wybranyCzasRozpoczecia<czasZakonczenia(trening.godzina, trening.czas_trwania)) 
		OR (czasZakonczenia(wybranyCzasRozpoczecia,0.5)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,0.5)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,1.0)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,1.0)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,1.5)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,1.5)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		)
		THEN 
			RAISE EXCEPTION 'W tym terminie wybrany trener jest zajęty!. Sprawdź widok Harmonogram Trenerów w celu wybrania poprawnego terminu';
			RETURN NULL;	
	END IF;
	END IF;
	IF (wybranaDlugosc=2.5) THEN
	IF ((wybranyCzasRozpoczecia>trening.godzina AND wybranyCzasRozpoczecia<czasZakonczenia(trening.godzina, trening.czas_trwania)) 
		OR (wybranyCzasZakonczenia>trening.godzina AND wybranyCzasZakonczenia<czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,0.5)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,0.5)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,1.0)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,1.0)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,1.5)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,1.5)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,2.0)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,2.0)<=czasZakonczenia(trening.godzina, trening.czas_trwania))		
		)
		THEN 
			RAISE EXCEPTION 'W tym terminie wybrany trener jest zajęty!. Sprawdź widok Harmonogram Trenerów w celu wybrania poprawnego terminu';
			RETURN NULL;	
	END IF;
	END IF;
	IF (wybranaDlugosc=3.0) THEN
	IF ((wybranyCzasRozpoczecia>trening.godzina AND wybranyCzasRozpoczecia<czasZakonczenia(trening.godzina, trening.czas_trwania)) 
		OR (wybranyCzasZakonczenia>trening.godzina AND wybranyCzasZakonczenia<czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,0.5)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,0.5)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,1.0)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,1.0)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,1.5)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,1.5)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,2.0)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,2.0)<czasZakonczenia(trening.godzina, trening.czas_trwania))
		OR (czasZakonczenia(wybranyCzasRozpoczecia,2.5)>=trening.godzina AND czasZakonczenia(wybranyCzasRozpoczecia,2.5)<=czasZakonczenia(trening.godzina, trening.czas_trwania))
		)
		THEN 
			RAISE EXCEPTION 'W tym terminie wybrany trener jest zajęty!. Sprawdź widok Harmonogram Trenerów w celu wybrania poprawnego terminu';
			RETURN NULL;	
	END IF;
	END IF;
END LOOP;
CLOSE kursor;
RETURN NEW;
END; 
$BODY$
LANGUAGE 'plpgsql';

CREATE TRIGGER sprawdzenieCzyTrenerMaCzas
BEFORE INSERT ON projekt.treningi
FOR EACH ROW
EXECUTE PROCEDURE projekt.wolnyCzasTrenera();

--funkcja wyzwalająca
--sprawdza czy minimalny wiek nie jest większy niż maksymalny
--jeśli wartości są błędznie podane drużyna nie zostanie dodana
CREATE OR REPLACE FUNCTION projekt.minWiekMniejszyNizMax() RETURNS TRIGGER AS
$BODY$
DECLARE
min integer;
max integer;
BEGIN
min := (NEW.min_wiek);
max := (NEW.max_wiek);
IF min<=max THEN
	RETURN NEW;
ELSE
	RAISE EXCEPTION 'Wiek maksymalny nie może być mniejszy niż minimalny';
	RETURN NULL;
END IF;
END; 
$BODY$
LANGUAGE 'plpgsql';

CREATE TRIGGER sprCzyMinWiekMniejszyOdMax
BEFORE INSERT ON projekt.druzyny
FOR EACH ROW
EXECUTE PROCEDURE projekt.minWiekMniejszyNizMax();

--funkcja wyzwalająca
--z założenia w projekcie można podawać jedynie pałne oraz połówkowe godziny 
--fonkcja sprawdza czy podana podczas wstawiania godzina jest zgodna z założeniami
--jeśli nie trening nie zostanie dodany
CREATE OR REPLACE FUNCTION projekt.formatGodziny() RETURNS TRIGGER AS
$BODY$
DECLARE
godzinaW TIME;
minuty int;
BEGIN
godzinaW := (NEW.godzina);
minuty := EXTRACT(MINUTE FROM godzinaW);
IF minuty=0 OR minuty=30 THEN
	RETURN NEW;
ELSE
	RAISE EXCEPTION 'Można dodać tylko pełne godziny oraz połókowe (np. 13:00:00/13:30:00)';
	RETURN NULL;
END IF;
END; 
$BODY$
LANGUAGE 'plpgsql';

CREATE TRIGGER sprCzyFormatGodzinyPoprawny
BEFORE INSERT ON projekt.treningi
FOR EACH ROW
EXECUTE PROCEDURE projekt.formatGodziny();


