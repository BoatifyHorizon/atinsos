drop schema public cascade;

drop table WYDZIAL if exists;
CREATE TABLE WYDZIAL (
                         ID INTEGER PRIMARY KEY,
                         NAZWA VARCHAR(100) NOT NULL
);

drop table ADRES if exists;
CREATE TABLE ADRES (
                       ID INTEGER  PRIMARY KEY,
                       LINIA_ADRESOWA_1 VARCHAR(255),
                       LINIA_ADRESOWA_2 VARCHAR(255)
);

drop table ROLA if exists;
CREATE TABLE ROLA (
                      ID INTEGER  PRIMARY KEY,
                      WYDZIAL_ID INTEGER,
                      NAZWA VARCHAR(50),
                      IS_ADMIN BOOLEAN,
                      FOREIGN KEY (WYDZIAL_ID) REFERENCES WYDZIAL(ID)
);

drop table UZYTKOWNIK if exists;
CREATE TABLE UZYTKOWNIK (
                            ID INTEGER  PRIMARY KEY,
                            NUMER_ALBUMU VARCHAR(50),
                            AKTYWNY BOOLEAN,
                            EMAIL VARCHAR(255),
                            HASLO VARCHAR(255),
                            TELEFON_2FA VARCHAR(50),
                            IMIE VARCHAR(100),
                            NAZWISKO VARCHAR(100),
                            ADRES_ZAMELDOWANIA INTEGER,
                            ADRES_KORESPONDENCYJNY INTEGER,
                            BLOKADA_KONTA BOOLEAN,
                            CZAS_BLOKADY TIMESTAMP,
                            FOREIGN KEY (ADRES_ZAMELDOWANIA) REFERENCES ADRES(ID),
                            FOREIGN KEY (ADRES_KORESPONDENCYJNY) REFERENCES ADRES(ID)
);

drop table UZYTKOWNIK_ROLA if exists;
CREATE TABLE UZYTKOWNIK_ROLA (
                                 UZYTKOWNIK_ID INTEGER NOT NULL,
                                 ROLA_ID INTEGER NOT NULL,
                                 PRIMARY KEY (UZYTKOWNIK_ID, ROLA_ID),
                                 FOREIGN KEY (UZYTKOWNIK_ID) REFERENCES UZYTKOWNIK(ID),
                                 FOREIGN KEY (ROLA_ID) REFERENCES ROLA(ID)
);

drop table NUMER_ALBUMU if exists;
CREATE TABLE NUMER_ALBUMU (
                              ID INTEGER  PRIMARY KEY,
                              NUMER_ALBUMU VARCHAR(50),
                              WAZNY BOOLEAN,
                              UZYTKOWNIK_ID INTEGER NOT NULL,
                              FOREIGN KEY (UZYTKOWNIK_ID) REFERENCES UZYTKOWNIK(ID)
);

drop table PROBA_LOGOWANIA if exists;
CREATE TABLE PROBA_LOGOWANIA (
                                 ID INTEGER  PRIMARY KEY,
                                 UZYTKOWNIK_ID INTEGER NOT NULL,
                                 DATA TIMESTAMP,
                                 CZY_UDANE BOOLEAN,
                                 FOREIGN KEY (UZYTKOWNIK_ID) REFERENCES UZYTKOWNIK(ID)
);

drop table POZYCJA_BIBLIOTECZNA if exists;
CREATE TABLE POZYCJA_BIBLIOTECZNA (
                                      ID INTEGER  PRIMARY KEY,
                                      LICZBA_KOPII INT
);

drop table WYPOZYCZENIE if exists;
CREATE TABLE WYPOZYCZENIE (
                              ID INTEGER  PRIMARY KEY,
                              DATA_WYPOZYCZENIA DATE,
                              TERMIN_ZWROTU DATE,       -- due date
                              DATA_ZWROTU DATE,         -- actual return date
                              UZYTKOWNIK_ID INTEGER NOT NULL,
                              POZYCJA_BIBLIOTECZNA_ID INTEGER NOT NULL,
                              FOREIGN KEY (UZYTKOWNIK_ID) REFERENCES UZYTKOWNIK(ID),
                              FOREIGN KEY (POZYCJA_BIBLIOTECZNA_ID) REFERENCES POZYCJA_BIBLIOTECZNA(ID)
);

drop table REZERWACJA if exists;
CREATE TABLE REZERWACJA (
                            ID INTEGER  PRIMARY KEY,
                            DATA_REZERWACJI DATE,
                            STATUS_REZERWACJI VARCHAR(50),
                            UZYTKOWNIK_ID INTEGER NOT NULL,
                            POZYCJA_BIBLIOTECZNA_ID INTEGER NOT NULL,
                            FOREIGN KEY (UZYTKOWNIK_ID) REFERENCES UZYTKOWNIK(ID),
                            FOREIGN KEY (POZYCJA_BIBLIOTECZNA_ID) REFERENCES POZYCJA_BIBLIOTECZNA(ID)
);


drop table TYP_OPLATY if exists;
CREATE TABLE TYP_OPLATY (
                            ID INTEGER  PRIMARY KEY,
                            NAZWA VARCHAR(100)
);

drop table PLATNOSC if exists;
CREATE TABLE PLATNOSC (
                          ID INTEGER  PRIMARY KEY,
                          STUDENT_ID INTEGER NOT NULL,
                          TYP_OPLATY_ID INTEGER NOT NULL,
                          KWOTA DECIMAL(10,2),
                          DATA_PLATNOSCI DATE,
                          FOREIGN KEY (STUDENT_ID) REFERENCES UZYTKOWNIK(ID),
                          FOREIGN KEY (TYP_OPLATY_ID) REFERENCES TYP_OPLATY(ID)
);

drop table PRZEDMIOT if exists;
CREATE TABLE PRZEDMIOT (
                           ID INTEGER  PRIMARY KEY,
                           OPIS VARCHAR(500),
                           TYTUL VARCHAR(255),
                           IS_ACTIVE BOOLEAN,
                           SEMESTR INT,
                           ROK_AKADEMICKI VARCHAR(20),
                           ZAPISY_OD TIMESTAMP,
                           ZAPISY_DO TIMESTAMP,
                           NUMER_SALI VARCHAR(50),
                           PROWADZACY_ID INTEGER,
                           WYDZIAL_ID INTEGER,
                           FOREIGN KEY (PROWADZACY_ID) REFERENCES UZYTKOWNIK(ID),
                           FOREIGN KEY (WYDZIAL_ID) REFERENCES WYDZIAL(ID)
);

drop table ZAJECIE if exists;
CREATE TABLE ZAJECIE (
                         ID INTEGER  PRIMARY KEY,
                         PRZEDMIOT_ID INTEGER NOT NULL,
                         ROZPOCZECIE TIMESTAMP,
                         ZAKONCZENIE TIMESTAMP,
                         FOREIGN KEY (PRZEDMIOT_ID) REFERENCES PRZEDMIOT(ID)
);

drop table PRZEDMIOT_STUDENTA if exists;
CREATE TABLE PRZEDMIOT_STUDENTA (
                                    ID INTEGER  PRIMARY KEY,
                                    STUDENT_ID INTEGER NOT NULL,
                                    PRZEDMIOT_ID INTEGER NOT NULL,
                                    CZAS_ZAPISANIA_SIE TIMESTAMP,
                                    FOREIGN KEY (STUDENT_ID) REFERENCES UZYTKOWNIK(ID),
                                    FOREIGN KEY (PRZEDMIOT_ID) REFERENCES PRZEDMIOT(ID)
);

drop table OCENA if exists;
CREATE TABLE OCENA (
                       ID INTEGER  PRIMARY KEY,
                       PRZEDMIOT_STUDENTA_ID INTEGER NOT NULL,
                       WARTOSC DECIMAL(2,1),           -- e.g. 2.0, 3.0, 3.5, 4.0, 4.5, 5.0
                       RODZAJ VARCHAR(50),            -- e.g. 'cząstkowa', 'końcowa'
                       DATA_WYSTAWIENIA_OCENY DATE,
                       FOREIGN KEY (PRZEDMIOT_STUDENTA_ID) REFERENCES PRZEDMIOT_STUDENTA(ID)
);

COMMIT;
