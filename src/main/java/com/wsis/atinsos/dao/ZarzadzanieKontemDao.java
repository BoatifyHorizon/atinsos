package com.wsis.atinsos.dao;

import com.wsis.atinsos.model.Uzytkownik;

public interface ZarzadzanieKontemDao {

    boolean zmienHaslo(int uzytkownikId, String stareHaslo, String noweHaslo);

    void zablokujKonto(int uzytkownikId);

    void odblokujKonto(int uzytkownikId);

    void resetujHaslo(String email);

    void aktualizujDaneKonta(Uzytkownik uzytkownik);

    void usunKonto(int uzytkownikId);
}
