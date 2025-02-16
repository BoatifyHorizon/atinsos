package com.wsis.atinsos.dao.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.wsis.atinsos.IdGenerator;
import org.springframework.stereotype.Repository;

import com.wsis.atinsos.dao.MenedzerLogowaniaDao;
import com.wsis.atinsos.model.ProbaLogowania;
import com.wsis.atinsos.model.StatusKonta; // Twój enum
import com.wsis.atinsos.model.Uzytkownik;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class MenedzerLogowaniaDaoImpl implements MenedzerLogowaniaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean zaloguj(String email, String haslo) {
        // Sprawdzamy poprawność danych
        boolean daneOk = zweryfikujDaneLogowania(email, haslo);
        if (!daneOk) {
            // Szukamy użytkownika, żeby zapisać nieudaną próbę
            Uzytkownik u = znajdzPoEmailu(email);
            if (u != null) {
                zapiszProbeLogowania(u.getId(), false);
            }
            return false;
        }
        // Jeśli dane ok, zapisz udaną próbę
        Uzytkownik u = znajdzPoEmailu(email);
        if (u != null) {
            // Można np. sprawdzić, czy konto nie jest zablokowane
            // lub zresetować liczbę nieudanych prób
            zapiszProbeLogowania(u.getId(), true);
            return true;
        }
        return false;
    }

    @Override
    public void wyloguj(int uzytkownikId) {
        // Metoda może np. ustawiać w bazie jakąś flagę „ostatnio wylogowany o…”
        // Albo nic nie robić, jeśli trzymamy tylko informację w sesji.
        Uzytkownik u = entityManager.find(Uzytkownik.class, uzytkownikId);
        if (u != null) {
            // Możemy np. zapisać w logach
            System.out.println("Wylogowano użytkownika: " + u.getEmail());
        }
    }

    @Override
    public StatusKonta sprawdzStatusKonta(int uzytkownikId) {
        Uzytkownik u = entityManager.find(Uzytkownik.class, uzytkownikId);
        if (u == null) {
            return StatusKonta.NIE_ISTNIEJE;
        }
        if (Boolean.TRUE.equals(u.getBlokadaKonta())) {
            return StatusKonta.ZABLOKOWANE;
        }
        return StatusKonta.AKTYWNE;
    }

    @Override
    public boolean zweryfikujDaneLogowania(String email, String haslo) {
        // Sprawdzamy, czy w bazie jest użytkownik o podanym emailu i haśle
        Uzytkownik u = entityManager.createQuery("SELECT usr FROM Uzytkownik usr WHERE usr.email = :em AND usr.haslo = :hs", Uzytkownik.class).setParameter("em", email).setParameter("hs", haslo).getResultStream().findFirst().orElse(null);

        return (u != null);
    }

    @Override
    public void zapiszProbeLogowania(int uzytkownikId, boolean sukces) {
        Uzytkownik u = entityManager.find(Uzytkownik.class, uzytkownikId);
        if (u == null) {
            return;
        }
        ProbaLogowania proba = new ProbaLogowania();
        proba.setId(IdGenerator.generateNumericId());
        proba.setUzytkownik(u);
        proba.setData(Timestamp.from(Instant.now()));
        proba.setCzyUdane(sukces);
        entityManager.persist(proba);
    }

    @Override
    public int sprawdzLiczbeNieudanychProb(int uzytkownikId) {
        // Liczymy, ile było nieudanych prób logowania w ProbaLogowania
        Long count = entityManager.createQuery("SELECT COUNT(p) FROM ProbaLogowania p WHERE p.uzytkownik.id = :uid AND p.czyUdane = false", Long.class).setParameter("uid", uzytkownikId).getSingleResult();
        return count == null ? 0 : count.intValue();
    }

    private Uzytkownik znajdzPoEmailu(String email) {
        return entityManager.createQuery("SELECT usr FROM Uzytkownik usr WHERE usr.email = :em", Uzytkownik.class).setParameter("em", email).getResultStream().findFirst().orElse(null);
    }
}
