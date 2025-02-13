package com.wsis.atinsos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.wsis.atinsos.dao.impl.UzytkownikDaoImpl;
import com.wsis.atinsos.model.Uzytkownik;

@SpringBootTest
@Transactional
@Rollback(true)
public class UzytkownikDaoImplTest {

    @Autowired
    private UzytkownikDaoImpl uzytkownikDao;

    private Uzytkownik uzytkownik;

    @BeforeEach
    public void setUp() {
        uzytkownik = new Uzytkownik();
        uzytkownik.setId(1);
        uzytkownik.setEmail("ala.makota@atins.pl");

        uzytkownikDao.save(uzytkownik);
    }

    @Test
    public void testSave() {
        String email = "ala.makota@atins.pl";

        assertNotNull(uzytkownik.getId());
        assertEquals(email, uzytkownik.getEmail());
    }

    @Test
    public void testFindById() {
        int id = 1;
        Uzytkownik result = uzytkownikDao.findById(id);

        assertEquals(uzytkownik, result);
    }

    @Test
    public void testUpdate() {
        String updatedEmail = "ala.niemakota@atins.pl";
        uzytkownik.setEmail(updatedEmail);
        uzytkownikDao.update(uzytkownik);

        Uzytkownik updatedUzytkownik = uzytkownikDao.findById(uzytkownik.getId());
        assertEquals(updatedEmail, updatedUzytkownik.getEmail());
    }

    @Test
    public void testDelete() {
        uzytkownikDao.delete(uzytkownik);
        Uzytkownik deletedUzytkownik = uzytkownikDao.findById(uzytkownik.getId());

        assertNull(deletedUzytkownik);
    }
}
