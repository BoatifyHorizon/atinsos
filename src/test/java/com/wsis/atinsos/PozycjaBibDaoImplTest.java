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

import com.wsis.atinsos.dao.impl.PozycjaBibDaoImpl;
import com.wsis.atinsos.model.PozycjaBiblioteczna;

@SpringBootTest
@Transactional
@Rollback(true)
public class PozycjaBibDaoImplTest {

    @Autowired
    private PozycjaBibDaoImpl pozycjaBibDao;

    private PozycjaBiblioteczna pozycjaBiblioteczna;

    @BeforeEach
    public void setUp() {
        pozycjaBiblioteczna = new PozycjaBiblioteczna();
        pozycjaBiblioteczna.setId(1);
        pozycjaBiblioteczna.setLiczbaKopii(200);

        pozycjaBibDao.save(pozycjaBiblioteczna);
    }

    @Test
    public void testSave() {
        int wartosc = 200;

        assertNotNull(pozycjaBiblioteczna.getId());
        assertEquals(wartosc, pozycjaBiblioteczna.getLiczbaKopii());
    }

    @Test
    public void testFindById() {
        int id = 1;
        PozycjaBiblioteczna result = pozycjaBibDao.findById(id);

        assertEquals(pozycjaBiblioteczna, result);
    }

    @Test
    public void testUpdate() {
        int updatedWartosc = 500;
        pozycjaBiblioteczna.setLiczbaKopii(updatedWartosc);
        pozycjaBibDao.update(pozycjaBiblioteczna);

        PozycjaBiblioteczna updatedPozycjaBiblioteczna = pozycjaBibDao.findById(pozycjaBiblioteczna.getId());
        assertEquals(updatedWartosc, updatedPozycjaBiblioteczna.getLiczbaKopii());
    }

    @Test
    public void testDelete() {
        pozycjaBibDao.delete(pozycjaBiblioteczna);
        PozycjaBiblioteczna deletedPozycjaBiblioteczna = pozycjaBibDao.findById(pozycjaBiblioteczna.getId());

        assertNull(deletedPozycjaBiblioteczna);
    }
}
