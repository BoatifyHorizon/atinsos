package com.wsis.atinsos;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.wsis.atinsos.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import com.wsis.atinsos.dao.impl.UzytkownikDaoImpl;
import com.wsis.atinsos.dao.impl.PrzedmiotDaoImpl;

@SpringBootTest
@Transactional
@Rollback(true)
public class UzytkownikDaoImplTest {

    @Autowired
    private UzytkownikDaoImpl uzytkownikDao;

    @Autowired
    private PrzedmiotDaoImpl przedmiotDao;

    @PersistenceContext
    private EntityManager em;

    private Uzytkownik uzytkownik;
    private Przedmiot przedmiot;

    @BeforeEach
    public void setUp() {
        // Tworzymy użytkownika i przedmiot
        uzytkownik = new Uzytkownik();
        uzytkownik.setId(100);
        uzytkownik.setEmail("test@student.com");
        uzytkownikDao.save(uzytkownik);

        przedmiot = new Przedmiot();
        przedmiot.setId(100);
        przedmiot.setTytul("Testowy przedmiot");
        przedmiotDao.save(przedmiot);
    }

    @Test
    public void testZapiszNaPrzedmiot() {
        // Zapis użytkownika na przedmiot
        boolean result = uzytkownikDao.zapiszNaPrzedmiot(przedmiot.getId(), uzytkownik.getId());
        assertTrue(result, "User should be enrolled successfully");

        // Ponowny zapis – powinien zwrócić false
        boolean resultDuplicate = uzytkownikDao.zapiszNaPrzedmiot(przedmiot.getId(), uzytkownik.getId());
        assertFalse(resultDuplicate, "User already enrolled, should return false");

        // Próba zapisu na nieistniejący przedmiot
        boolean resultInvalid = uzytkownikDao.zapiszNaPrzedmiot(9999, uzytkownik.getId());
        assertFalse(resultInvalid, "Non-existent course, should return false");
    }

    @Test
    public void testWypiszZPrzedmiot() {
        // Najpierw zapisujemy użytkownika na przedmiot
        uzytkownikDao.zapiszNaPrzedmiot(przedmiot.getId(), uzytkownik.getId());
        // Następnie wypisujemy
        boolean result = uzytkownikDao.wypiszZPrzedmiot(przedmiot.getId(), uzytkownik.getId());
        assertTrue(result, "User should be unenrolled successfully");

        // Ponowna próba wypisu – użytkownik nie jest już zapisany
        boolean resultNotEnrolled = uzytkownikDao.wypiszZPrzedmiot(przedmiot.getId(), uzytkownik.getId());
        assertFalse(resultNotEnrolled, "User is not enrolled, should return false");
    }

    @Test
    public void testZwrocOcenaZPrzedmiot() {
        // Przygotowanie: zapisujemy użytkownika na przedmiot
        uzytkownikDao.zapiszNaPrzedmiot(przedmiot.getId(), uzytkownik.getId());
        // Pobieramy powiązanie PrzedmiotStudenta
        List<PrzedmiotStudenta> psList = em.createQuery("SELECT ps FROM PrzedmiotStudenta ps WHERE ps.student.id = :uid AND ps.przedmiot.id = :pid", PrzedmiotStudenta.class).setParameter("uid", uzytkownik.getId()).setParameter("pid", przedmiot.getId()).getResultList();
        assertFalse(psList.isEmpty(), "PrzedmiotStudenta should exist");
        PrzedmiotStudenta ps = psList.get(0);

        // Tworzymy ocenę z przypisaniem do powiązania
        Ocena ocena = new Ocena();
        ocena.setId(200);
        ocena.setWartosc(new BigDecimal("4.5"));
        ocena.setPrzedmiotStudenta(ps);
        em.persist(ocena);
        em.flush();

        // Testujemy metodę – oczekujemy, że zwróci 4.5
        double grade = uzytkownikDao.zwrocOcenaZPrzedmiot(przedmiot.getId(), uzytkownik.getId());
        assertEquals(4.5, grade, 0.01, "Returned grade should be 4.5");
    }

    @Test
    public void testZwrocListePrzypisanychPrzedmitow() {
        // Zapis użytkownika na przedmiot
        uzytkownikDao.zapiszNaPrzedmiot(przedmiot.getId(), uzytkownik.getId());
        List<Przedmiot> przedmioty = uzytkownikDao.zwrocListePrzypisanychPrzedmitow(uzytkownik.getId());
        assertNotNull(przedmioty, "List should not be null");
        assertFalse(przedmioty.isEmpty(), "List should contain enrolled course");
        assertEquals(przedmiot.getTytul(), przedmioty.get(0).getTytul(), "Course title should match");
    }

    @Test
    public void testZwrocPlanZajec() {
        // Metoda powinna rzucać Error z komunikatem "TO nie ma sensu"
        Error error = assertThrows(Error.class, () -> {
            uzytkownikDao.zwrocPlanZajec(uzytkownik.getId());
        });
        assertTrue(error.getMessage().contains("TO nie ma sensu"), "Error message should indicate method is not implemented");
    }

    @Test
    public void testZwrocListeWypozyczonychPozycjaBiblioteczna() {
        // Tworzymy PozycjaBiblioteczna i Wypozyczenie dla użytkownika
        PozycjaBiblioteczna pb = new PozycjaBiblioteczna();
        pb.setId(300);
        pb.setLiczbaKopii(10);
        em.persist(pb);

        Wypozyczenie wypozyczenie = new Wypozyczenie();
        wypozyczenie.setId(400);
        wypozyczenie.setDataWypozyczenia(Date.valueOf(LocalDate.now()));
        wypozyczenie.setUzytkownik(uzytkownik);
        wypozyczenie.setPozycjaBiblioteczna(pb);
        em.persist(wypozyczenie);
        em.flush();

        List<PozycjaBiblioteczna> lista = uzytkownikDao.zwrocListeWypozyczonychPozycjaBiblioteczna(uzytkownik.getId());
        assertNotNull(lista, "List should not be null");
        assertFalse(lista.isEmpty(), "List should contain at least one PozycjaBiblioteczna");
        assertEquals(pb.getId(), lista.get(0).getId(), "Returned PozycjaBiblioteczna should match");
    }

    @Test
    public void testZwrocPrzeterminowanePozycjaBiblioteczna() {
        // Tworzymy PozycjaBiblioteczna i Wypozyczenie z terminem zwrotu ustawionym na jutro
        PozycjaBiblioteczna pb = new PozycjaBiblioteczna();
        pb.setId(500);
        pb.setLiczbaKopii(5);
        em.persist(pb);

        Wypozyczenie wypozyczenie = new Wypozyczenie();
        wypozyczenie.setId(600);
        wypozyczenie.setTerminZwrotu(Date.valueOf(LocalDate.now().plusDays(1)));
        wypozyczenie.setUzytkownik(uzytkownik);
        wypozyczenie.setPozycjaBiblioteczna(pb);
        em.persist(wypozyczenie);
        em.flush();

        List<PozycjaBiblioteczna> lista = uzytkownikDao.zwrocPrzeterminowanePozycjaBiblioteczna(uzytkownik.getId());
        assertNotNull(lista, "List should not be null");
        assertFalse(lista.isEmpty(), "List should contain items");
        assertEquals(pb.getId(), lista.get(0).getId(), "Returned PozycjaBiblioteczna should match");
    }

    @Test
    public void testZwrocOddanePozycjaBiblioteczna() {
        // Tworzymy PozycjaBiblioteczna i Wypozyczenie z niepustą datą zwrotu
        PozycjaBiblioteczna pb = new PozycjaBiblioteczna();
        pb.setId(700);
        pb.setLiczbaKopii(3);
        em.persist(pb);

        Wypozyczenie wypozyczenie = new Wypozyczenie();
        wypozyczenie.setId(800);
        wypozyczenie.setDataZwrotu(Date.valueOf(LocalDate.now()));
        wypozyczenie.setUzytkownik(uzytkownik);
        wypozyczenie.setPozycjaBiblioteczna(pb);
        em.persist(wypozyczenie);
        em.flush();

        List<PozycjaBiblioteczna> lista = uzytkownikDao.zwrocOddanePozycjaBiblioteczna(uzytkownik.getId());
        assertNotNull(lista, "List should not be null");
        assertFalse(lista.isEmpty(), "List should contain items");
        assertEquals(pb.getId(), lista.get(0).getId(), "Returned PozycjaBiblioteczna should match");
    }

    @Test
    public void testZwrocPlatnosc() {
        // Metoda ma rzucać Error z komunikatem "Czy to ma sens?"
        Error error = assertThrows(Error.class, () -> {
            uzytkownikDao.zwrocPlatnosc(uzytkownik.getId());
        });
        assertTrue(error.getMessage().contains("Czy to ma sens"), "Error message should indicate method is not implemented");
    }

    @Test
    public void testZmienAres() {
        var adres = new Adres();
        adres.setId(900);
        adres.setLiniaAdresowa1("ul. Testowa");
        uzytkownik.setAdresKorespondencyjny(adres);

        boolean result = uzytkownikDao.zmienAres(uzytkownik.getId(), "ul. Testowa", "mieszkanie 1");
        assertTrue(result, "Address change should return true");
        Uzytkownik updatedUser = uzytkownikDao.findById(uzytkownik.getId());
        assertNotNull(updatedUser.getAdresKorespondencyjny(), "Address should not be null after update");
        assertEquals("ul. Testowa", updatedUser.getAdresKorespondencyjny().getLiniaAdresowa1(), "Address line 1 should match");
        assertEquals("mieszkanie 1", updatedUser.getAdresKorespondencyjny().getLiniaAdresowa2(), "Address line 2 should match");
    }
}
