package com.wsis.atinsos.dao.impl;

import java.sql.Timestamp;
import java.time.Instant;

import com.wsis.atinsos.dao.ZarzadzanieKontemDao;
import org.springframework.stereotype.Repository;

import com.wsis.atinsos.model.Uzytkownik;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ZarzadzanieKontemDaoImpl implements ZarzadzanieKontemDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean zmienHaslo(int uzytkownikId, String stareHaslo, String noweHaslo) {
        Uzytkownik u = entityManager.find(Uzytkownik.class, uzytkownikId);
        if (u == null) {
            return false; // Nie ma takiego użytkownika
        }
        // Weryfikacja starego hasła
        if (!u.getHaslo().equals(stareHaslo)) {
            return false; // Stare hasło się nie zgadza
        }
        // Ustawienie nowego hasła
        u.setHaslo(noweHaslo);
        entityManager.merge(u);
        return true;
    }

    @Override
    public void zablokujKonto(int uzytkownikId) {
        Uzytkownik u = entityManager.find(Uzytkownik.class, uzytkownikId);
        if (u != null) {
            u.setBlokadaKonta(true);
            u.setCzasBlokady(Timestamp.from(Instant.now()));
            entityManager.merge(u);
        }
    }

    @Override
    public void odblokujKonto(int uzytkownikId) {
        Uzytkownik u = entityManager.find(Uzytkownik.class, uzytkownikId);
        if (u != null) {
            u.setBlokadaKonta(false);
            u.setCzasBlokady(null);
            entityManager.merge(u);
        }
    }

    @Override
    public void resetujHaslo(String email) {
        Uzytkownik u = entityManager.createQuery("SELECT usr FROM Uzytkownik usr WHERE usr.email = :em", Uzytkownik.class).setParameter("em", email).getResultStream().findFirst().orElse(null);

        if (u != null) {
            u.setHaslo("reset123"); // lub generujemy losowe hasło i wysyłamy je e-mailem
            // i.e sendEmail(u.getEmail(), "Nowe hasło: " + noweHaslo);
            entityManager.merge(u);
        }
    }

    @Override
    public void aktualizujDaneKonta(Uzytkownik uzytkownik) {
        entityManager.merge(uzytkownik);
    }

    @Override
    public void usunKonto(int uzytkownikId) {
        Uzytkownik u = entityManager.find(Uzytkownik.class, uzytkownikId);
        if (u != null) {
            entityManager.remove(u);
        }
    }
}
