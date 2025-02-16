package com.wsis.atinsos;

import static org.junit.jupiter.api.Assertions.*;


import com.wsis.atinsos.dao.PrzedmiotStudentaDao;
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
public class PrzedmiotStudentaDaoImplTest {
    @Autowired
    private PrzedmiotStudentaDao przedmiotStudentaDao;

    @Autowired
    private UzytkownikDaoImpl uzytkownikDao;

    @Autowired
    private PrzedmiotDaoImpl przedmiotDao;

    @PersistenceContext
    private EntityManager em;

    private Uzytkownik uzytkownik;
    private Przedmiot przedmiot;
    private PrzedmiotStudenta ps;

    @BeforeEach
    public void setUp() {
        // Utwórz użytkownika
        uzytkownik = new Uzytkownik();
        uzytkownik.setId(1);
        uzytkownik.setEmail("test@student.com");
        uzytkownikDao.save(uzytkownik);

        // Utwórz przedmiot
        przedmiot = new Przedmiot();
        przedmiot.setId(1);
        przedmiot.setTytul("Testowy Przedmiot");
        przedmiotDao.save(przedmiot);

        // Utwórz wpis PrzedmiotStudenta
        ps = new PrzedmiotStudenta();
        ps.setId(1);
        ps.setStudent(uzytkownik);
        ps.setPrzedmiot(przedmiot);
        em.persist(ps);
        em.flush();
    }

    @Test
    public void testZnajdzPrzedmiotPoStudentId_Znaleziono() {
        // Powinien zwrócić wpis dla istniejącego użytkownika
        PrzedmiotStudenta result = przedmiotStudentaDao.znajdzPrzedmiotPoStudentId(uzytkownik.getId());
        assertNotNull(result, "Oczekiwano niepustego wyniku dla istniejącego zapisu");
        assertEquals(uzytkownik.getId(), result.getStudent().getId(), "Identyfikator użytkownika powinien pasować");
        assertEquals(przedmiot.getId(), result.getPrzedmiot().getId(), "Identyfikator przedmiotu powinien pasować");
    }

    @Test
    public void testZnajdzPrzedmiotPoStudentId_NieZnaleziono() {
        // Utwórz nowego użytkownika bez wpisu w PrzedmiotStudenta
        Uzytkownik newUser = new Uzytkownik();
        newUser.setId(2);
        newUser.setEmail("nowy@student.com");
        uzytkownikDao.save(newUser);

        PrzedmiotStudenta result = przedmiotStudentaDao.znajdzPrzedmiotPoStudentId(newUser.getId());
        assertNull(result, "Oczekiwano pustego wyniku dla użytkownika bez zapisu");
    }
}