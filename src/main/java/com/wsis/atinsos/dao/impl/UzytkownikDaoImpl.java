package com.wsis.atinsos.dao.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wsis.atinsos.IdGenerator;
import com.wsis.atinsos.dao.UzytkownikDao;
import com.wsis.atinsos.model.Adres;
import com.wsis.atinsos.model.PozycjaBiblioteczna;
import com.wsis.atinsos.model.Przedmiot;
import com.wsis.atinsos.model.PrzedmiotStudenta;
import com.wsis.atinsos.model.Uzytkownik;
import com.wsis.atinsos.model.Wypozyczenie;

import jakarta.persistence.TypedQuery;

@Repository
public class UzytkownikDaoImpl extends GenericDaoImpl<Uzytkownik, Integer> implements UzytkownikDao {

    public UzytkownikDaoImpl() {
        super(Uzytkownik.class);
    }

    @Override
    public boolean zapiszNaPrzedmiot(int idPrzedmiot, int idUzytkownik) {
        try {
            Uzytkownik uzytkownik = entityManager.find(Uzytkownik.class, idUzytkownik);
            Przedmiot przedmiot = entityManager.find(Przedmiot.class, idPrzedmiot);

            if (uzytkownik == null || przedmiot == null) {
                return false;
            }

            TypedQuery<PrzedmiotStudenta> query = entityManager.createQuery("SELECT ps FROM PrzedmiotStudenta ps " + "WHERE ps.student.id = :uId AND ps.przedmiot.id = :pId", PrzedmiotStudenta.class);
            query.setParameter("uId", idUzytkownik);
            query.setParameter("pId", idPrzedmiot);

            List<PrzedmiotStudenta> lista = query.getResultList();
            if (!lista.isEmpty()) {
                return false;
            }

            PrzedmiotStudenta ps = new PrzedmiotStudenta();
            ps.setId(IdGenerator.generateNumericId());
            ps.setStudent(uzytkownik);
            ps.setPrzedmiot(przedmiot);

            entityManager.persist(ps);
            return true;
        } catch (Exception e) {
            throw new Error("Błąd przy zapisywaniu na przedmiot", e);
        }
    }

    @Override
    public boolean wypiszZPrzedmiot(int idPrzedmiot, int idUzytkownik) {
        try {
            TypedQuery<PrzedmiotStudenta> query = entityManager.createQuery("SELECT ps FROM PrzedmiotStudenta ps " + "WHERE ps.student.id = :uId AND ps.przedmiot.id = :pId", PrzedmiotStudenta.class);
            query.setParameter("uId", idUzytkownik);
            query.setParameter("pId", idPrzedmiot);

            List<PrzedmiotStudenta> lista = query.getResultList();
            if (lista.isEmpty()) {
                return false;
            }

            for (PrzedmiotStudenta ps : lista) {
                entityManager.remove(ps);
            }
            return true;
        } catch (Exception e) {
            throw new Error("Błąd przy wypisywaniu z przedmiotu", e);
        }
    }


    @Override
    public double zwrocOcenaZPrzedmiot(int idPrzedmiot, int idUzytkownik) {
        try {
            TypedQuery<BigDecimal> query = entityManager.createQuery("SELECT o.wartosc FROM Ocena o " + "JOIN o.przedmiotStudenta ps " + "WHERE ps.przedmiot.id = :idPrzedmiot " + "AND ps.student.id = :idUzytkownik", BigDecimal.class).setParameter("idUzytkownik", idUzytkownik).setParameter("idPrzedmiot", idPrzedmiot);

            BigDecimal result = query.getSingleResult();

            return result.doubleValue();
        } catch (Exception e) {
            throw new Error("Blad przy pobieraniu oceny");
        }
    }

    @Override
    public List<Przedmiot> zwrocListePrzypisanychPrzedmitow(int idUzytkownik) {
        try {
            TypedQuery<PrzedmiotStudenta> queryPrzedmiotStudenta = entityManager.createQuery("SELECT ps FROM PrzedmiotStudenta ps WHERE ps.student.id = :idUzytkownik", PrzedmiotStudenta.class);
            queryPrzedmiotStudenta.setParameter("idUzytkownik", idUzytkownik);
            List<PrzedmiotStudenta> przedmiotStudentaList = queryPrzedmiotStudenta.getResultList();

            List<Integer> idPrzedmiotList = przedmiotStudentaList.stream().map(ps -> ps.getPrzedmiot().getId()).toList();

            TypedQuery<Przedmiot> queryPrzedmiot = entityManager.createQuery("SELECT p FROM Przedmiot p WHERE p.id IN :idPrzedmiotList", Przedmiot.class);
            queryPrzedmiot.setParameter("idPrzedmiotList", idPrzedmiotList);

            return queryPrzedmiot.getResultList();
        } catch (Exception e) {
            throw new Error("Blad przy pobieraniu przypisanych przedmiotow");
        }
    }

    @Override
    public List<PozycjaBiblioteczna> zwrocListeWypozyczonychPozycjaBiblioteczna(int idUzytkownik) {
        try {
            TypedQuery<Wypozyczenie> queryWypozyczenie = entityManager.createQuery("SELECT w FROM Wypozyczenie w WHERE w.uzytkownik.id = :idUzytkownik", Wypozyczenie.class);
            queryWypozyczenie.setParameter("idUzytkownik", idUzytkownik);
            List<Wypozyczenie> wypozyczenieList = queryWypozyczenie.getResultList();

            List<Integer> idPozycjaBibliotecznaList = wypozyczenieList.stream().map(w -> w.getPozycjaBiblioteczna().getId()).toList();

            TypedQuery<PozycjaBiblioteczna> queryPozycjaBiblioteczna = entityManager.createQuery("SELECT pb FROM PozycjaBiblioteczna pb WHERE pb.id IN :idPozycjaBibliotecznaList", PozycjaBiblioteczna.class);
            queryPozycjaBiblioteczna.setParameter("idPozycjaBibliotecznaList", idPozycjaBibliotecznaList);

            return queryPozycjaBiblioteczna.getResultList();
        } catch (Exception e) {
            throw new Error("Blad przy pobieraniu wypozyczonych pozycji bibliotecznych");
        }
    }

    @Override
    public List<PozycjaBiblioteczna> zwrocPrzeterminowanePozycjaBiblioteczna(int idUzytkownik) {
        try {
            Date currentDate = Date.valueOf(LocalDate.now());

            TypedQuery<Wypozyczenie> queryWypozyczenie = entityManager.createQuery("SELECT w FROM Wypozyczenie w WHERE w.terminZwrotu > :currentDate", Wypozyczenie.class);
            queryWypozyczenie.setParameter("currentDate", currentDate);
            List<Wypozyczenie> wypozyczenieList = queryWypozyczenie.getResultList();

            List<Integer> idPozycjaBibliotecznaList = wypozyczenieList.stream().map(w -> w.getPozycjaBiblioteczna().getId()).toList();

            TypedQuery<PozycjaBiblioteczna> queryPozycjaBiblioteczna = entityManager.createQuery("SELECT pb FROM PozycjaBiblioteczna pb WHERE pb.id IN :idPozycjaBibliotecznaList", PozycjaBiblioteczna.class);
            queryPozycjaBiblioteczna.setParameter("idPozycjaBibliotecznaList", idPozycjaBibliotecznaList);

            return queryPozycjaBiblioteczna.getResultList();
        } catch (Exception e) {
            throw new Error("Blad przy pobieraniu przeterminowanych pozycji bibliotecznych");
        }
    }

    @Override
    public List<PozycjaBiblioteczna> zwrocOddanePozycjaBiblioteczna(int idUzytkownik) {
        try {
            TypedQuery<Wypozyczenie> queryWypozyczenie = entityManager.createQuery("SELECT w FROM Wypozyczenie w WHERE w.dataZwrotu IS NOT NULL AND w.uzytkownik.id = :idUzytkownik", Wypozyczenie.class);
            queryWypozyczenie.setParameter("idUzytkownik", idUzytkownik);
            List<Wypozyczenie> wypozyczenieList = queryWypozyczenie.getResultList();

            List<Integer> idPozycjaBibliotecznaList = wypozyczenieList.stream().map(w -> w.getPozycjaBiblioteczna().getId()).toList();

            TypedQuery<PozycjaBiblioteczna> queryPozycjaBiblioteczna = entityManager.createQuery("SELECT pb FROM PozycjaBiblioteczna pb WHERE pb.id IN :idPozycjaBibliotecznaList", PozycjaBiblioteczna.class);
            queryPozycjaBiblioteczna.setParameter("idPozycjaBibliotecznaList", idPozycjaBibliotecznaList);

            return queryPozycjaBiblioteczna.getResultList();
        } catch (Exception e) {
            throw new Error("Blad przy pobieraniu oddanych pozycji bibliotecznych");
        }
    }

    @Override
    public boolean zmienAres(int idUzytkownik, String adres1, String adres2) {
        try {
            Uzytkownik uzytkownik = entityManager.find(Uzytkownik.class, idUzytkownik);
            if (uzytkownik == null) {
                return false;
            }

            Adres adres = uzytkownik.getAdresKorespondencyjny();
            if (adres == null) {
                adres = new Adres();
            }

            adres.setLiniaAdresowa1(adres1);
            adres.setLiniaAdresowa2(adres2);

            if (adres.getId() == null) {
                entityManager.persist(adres);
            }

            uzytkownik.setAdresKorespondencyjny(adres);
            entityManager.merge(uzytkownik);

            return true;
        } catch (Exception e) {
            throw new Error("Blad przy zmianie adresu");
        }
    }

}
