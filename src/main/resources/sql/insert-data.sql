-- 1. WYDZIAL
INSERT INTO WYDZIAL (ID, NAZWA) VALUES
                                    (1, 'Wydział Informatyki'),
                                    (2, 'Wydział Zarządzania');

-- 2. ADRES
INSERT INTO ADRES (ID, LINIA_ADRESOWA_1, LINIA_ADRESOWA_2) VALUES
                                                               (1, 'ul. Kwiatowa 4, 00-000 Warszawa', 'Mieszkanie 15'),
                                                               (2, 'ul. Polna 5, 01-001 Warszawa', NULL);

-- 3. ROLA (references WYDZIAL)
INSERT INTO ROLA (ID, WYDZIAL_ID, NAZWA, IS_ADMIN) VALUES
                                                       (1, 1, 'Student', FALSE),
                                                       (2, 1, 'Dziekan', FALSE),
                                                       (3, 1, 'Admin', TRUE),
                                                       (4, 2, 'Prowadzący', FALSE);

-- 4. UZYTKOWNIK (references ADRES)
INSERT INTO UZYTKOWNIK (
    ID, NUMER_ALBUMU, AKTYWNY, EMAIL, HASLO, TELEFON_2FA,
    IMIE, NAZWISKO, ADRES_ZAMELDOWANIA, ADRES_KORESPONDENCYJNY,
    BLOKADA_KONTA, CZAS_BLOKADY
) VALUES
      (1, '1000', TRUE,  'jan.kowalski@example.com',   'haslo123',   '123456789',  'Jan',       'Kowalski',  1, 1, FALSE, NULL),
      (2, '1001', TRUE,  'adam.nowak@example.com',     'hasloABC',   '987654321',  'Adam',      'Nowak',     2, 2, FALSE, NULL),
      (3, 'admin',TRUE,  'admin@domain.com',           'adminPass',  '555111222',  'Admin',     'One',       2, 2, FALSE, NULL),
      (4, '2001', TRUE,  'k.wisniewska@example.com',   'pass1234',   '555123999',  'Katarzyna', 'Wiśniewska',2, 2, FALSE, NULL);

-- 5. UZYTKOWNIK_ROLA (many-to-many join table)
INSERT INTO UZYTKOWNIK_ROLA (UZYTKOWNIK_ID, ROLA_ID) VALUES
                                                         (1, 1),  -- Jan Kowalski as Student
                                                         (2, 1),  -- Adam Nowak as Student
                                                         (3, 3),  -- Admin One as Admin
                                                         (4, 4);  -- Katarzyna Wiśniewska as Prowadzący

-- 6. NUMER_ALBUMU (references UZYTKOWNIK)
INSERT INTO NUMER_ALBUMU (ID, NUMER_ALBUMU, WAZNY, UZYTKOWNIK_ID) VALUES
                                                                      (1, '1000', TRUE, 1),
                                                                      (2, '1001', TRUE, 2);

-- 7. POZYCJA_BIBLIOTECZNA
INSERT INTO POZYCJA_BIBLIOTECZNA (ID, LICZBA_KOPII) VALUES
                                                        (1, 3),
                                                        (2, 2),
                                                        (3, 1);

-- 8. WYPOZYCZENIE (references UZYTKOWNIK, POZYCJA_BIBLIOTECZNA)
INSERT INTO WYPOZYCZENIE (
    ID, DATA_WYPOZYCZENIA, TERMIN_ZWROTU, DATA_ZWROTU,
    UZYTKOWNIK_ID, POZYCJA_BIBLIOTECZNA_ID
) VALUES
      (1, DATE '2025-01-10', DATE '2025-02-10', DATE '2025-01-20', 1, 1),
      (2, DATE '2025-01-11', DATE '2025-02-11', NULL,             2, 2);

-- 9. REZERWACJA (references UZYTKOWNIK, POZYCJA_BIBLIOTECZNA)
INSERT INTO REZERWACJA (
    ID, DATA_REZERWACJI, STATUS_REZERWACJI,
    UZYTKOWNIK_ID, POZYCJA_BIBLIOTECZNA_ID
) VALUES
      (1, DATE '2025-01-12', 'Oczekująca', 1, 3),
      (2, DATE '2025-01-14', 'W realizacji', 2, 1);

-- 10. TYP_OPLATY
INSERT INTO TYP_OPLATY (ID, NAZWA) VALUES
                                       (1, 'Czesne'),
                                       (2, 'Legitymacja'),
                                       (3, 'Kaucja biblioteczna');

-- 11. PLATNOSC (references UZYTKOWNIK, TYP_OPLATY)
INSERT INTO PLATNOSC (
    ID, STUDENT_ID, TYP_OPLATY_ID, KWOTA, DATA_PLATNOSCI
) VALUES
      (1, 1, 1, 2000.00, DATE '2025-01-15'),
      (2, 2, 2,   25.00, DATE '2025-01-15'),
      (3, 1, 3,   50.00, DATE '2025-01-16');

-- 12. PRZEDMIOT (references UZYTKOWNIK as PROWADZACY_ID, WYDZIAL)
INSERT INTO PRZEDMIOT (
    ID, OPIS, TYTUL, IS_ACTIVE, SEMESTR, ROK_AKADEMICKI,
    ZAPISY_OD, ZAPISY_DO, NUMER_SALI, PROWADZACY_ID, WYDZIAL_ID
) VALUES
      (1, 'Podstawy programowania w języku Java', 'Wstęp do Programowania', TRUE, 1, '2024/2025',
       TIMESTAMP '2025-01-15 00:00:00', TIMESTAMP '2025-02-15 00:00:00', 'A1', 4, 1),
      (2, 'Zaawansowane zagadnienia systemowe',   'Systemy Operacyjne',     TRUE, 1, '2024/2025',
       TIMESTAMP '2025-01-15 00:00:00', TIMESTAMP '2025-02-15 00:00:00', 'A2', 4, 1);

-- 13. ZAJECIE (references PRZEDMIOT)
INSERT INTO ZAJECIE (
    ID, PRZEDMIOT_ID, ROZPOCZECIE, ZAKONCZENIE
) VALUES
      (1, 1, TIMESTAMP '2025-03-01 08:00:00', TIMESTAMP '2025-03-01 09:30:00'),
      (2, 2, TIMESTAMP '2025-03-02 08:00:00', TIMESTAMP '2025-03-02 09:30:00');

-- 14. PRZEDMIOT_STUDENTA (references UZYTKOWNIK as STUDENT_ID, PRZEDMIOT)
INSERT INTO PRZEDMIOT_STUDENTA (
    ID, STUDENT_ID, PRZEDMIOT_ID, CZAS_ZAPISANIA_SIE
) VALUES
      (1, 1, 1, TIMESTAMP '2025-01-20 10:00:00'),  -- Jan Kowalski -> Wstęp do Programowania
      (2, 2, 1, TIMESTAMP '2025-01-20 11:00:00'),  -- Adam Nowak   -> Wstęp do Programowania
      (3, 2, 2, TIMESTAMP '2025-01-20 12:00:00');  -- Adam Nowak   -> Systemy Operacyjne

-- 15. OCENA (references PRZEDMIOT_STUDENTA)
INSERT INTO OCENA (
    ID, PRZEDMIOT_STUDENTA_ID, WARTOSC, RODZAJ, DATA_WYSTAWIENIA_OCENY
) VALUES
      (1, 1, 4.5, 'cząstkowa', DATE '2025-01-25'),
      (2, 2, 3.0, 'końcowa',   DATE '2025-01-26'),
      (3, 3, 5.0, 'końcowa',   DATE '2025-01-28');

-- 16. PROBA_LOGOWANIA (references UZYTKOWNIK)
INSERT INTO PROBA_LOGOWANIA (
    ID, UZYTKOWNIK_ID, DATA, CZY_UDANE
) VALUES
      (1, 1, TIMESTAMP '2025-01-01 10:00:00', TRUE),
      (2, 1, TIMESTAMP '2025-01-02 11:30:00', FALSE),
      (3, 2, TIMESTAMP '2025-01-05 08:45:00', TRUE);

COMMIT;


