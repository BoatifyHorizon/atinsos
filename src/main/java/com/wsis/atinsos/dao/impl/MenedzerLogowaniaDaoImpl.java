package com.wsis.atinsos.dao.impl;

import java.sql.Timestamp;
import java.time.Instant;

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
        boolean daneOk = zweryfikujDaneLogowania(email, haslo);
        if (!daneOk) {
            Uzytkownik u = znajdzPoEmailu(email);
            if (u != null) {
                zapiszProbeLogowania(u.getId(), false);
            }
            return false;
        }
        Uzytkownik u = znajdzPoEmailu(email);
        if (u != null) {
            zapiszProbeLogowania(u.getId(), true);
            return true;
        }
        return false;
    }

    @Override
    public void wyloguj(int uzytkownikId) {
        Uzytkownik u = entityManager.find(Uzytkownik.class, uzytkownikId);
        if (u != null) {
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
        Long count = entityManager.createQuery("SELECT COUNT(p) FROM ProbaLogowania p WHERE p.uzytkownik.id = :uid AND p.czyUdane = false", Long.class).setParameter("uid", uzytkownikId).getSingleResult();
        return count == null ? 0 : count.intValue();
    }

    private Uzytkownik znajdzPoEmailu(String email) {
        return entityManager.createQuery("SELECT usr FROM Uzytkownik usr WHERE usr.email = :em", Uzytkownik.class).setParameter("em", email).getResultStream().findFirst().orElse(null);
    }
}
