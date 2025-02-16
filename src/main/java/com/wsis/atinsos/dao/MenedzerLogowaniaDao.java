package com.wsis.atinsos.dao;

import com.wsis.atinsos.model.StatusKonta;

public interface MenedzerLogowaniaDao {

    boolean zaloguj(String email, String haslo);

    void wyloguj(int uzytkownikId);

    StatusKonta sprawdzStatusKonta(int uzytkownikId);

    boolean zweryfikujDaneLogowania(String email, String haslo);

    void zapiszProbeLogowania(int uzytkownikId, boolean sukces);

    int sprawdzLiczbeNieudanychProb(int uzytkownikId);
}

