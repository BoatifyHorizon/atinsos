package com.wsis.atinsos.dao;

import java.util.List;

import com.wsis.atinsos.model.Platnosc;
import com.wsis.atinsos.model.PozycjaBiblioteczna;
import com.wsis.atinsos.model.Przedmiot;
import com.wsis.atinsos.model.Uzytkownik;

public interface UzytkownikDao extends GenericDao<Uzytkownik, Integer> {
    boolean zapiszNaPrzedmiot(int idPrzedmiot, int idUzytkownik);
    boolean wypiszZPrzedmiot(int idPrzedmiot, int idUzytkownik);
    double zwrocOcenaZPrzedmiot(int idPrzedmiot, int idUzytkownik);
    List<Przedmiot> zwrocListePrzypisanychPrzedmitow(int idUzytkownik);
    List<Przedmiot> zwrocPlanZajec(int idUzytkownik);
    List<PozycjaBiblioteczna> zwrocListeWypozyczonychPozycjaBiblioteczna(int idUzytkownik);
    List<PozycjaBiblioteczna> zwrocPrzeterminowanePozycjaBiblioteczna(int idUzytkownik);
    List<PozycjaBiblioteczna> zwrocOddanePozycjaBiblioteczna(int idUzytkownik);
    Platnosc zwrocPlatnosc(int idUzytkownik);
    boolean zmienAres(int idUzytkownik, String adres1, String adres2);
}