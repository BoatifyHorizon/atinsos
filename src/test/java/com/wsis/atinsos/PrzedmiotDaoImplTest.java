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

import com.wsis.atinsos.dao.impl.PrzedmiotDaoImpl;
import com.wsis.atinsos.model.Przedmiot;

@SpringBootTest
@Transactional
@Rollback(true)
public class PrzedmiotDaoImplTest {

    @Autowired
    private PrzedmiotDaoImpl przedmiotDao;

    private Przedmiot przedmiot;

    @BeforeEach
    public void setUp() {
        przedmiot = new Przedmiot();
        przedmiot.setId(1);
        przedmiot.setTytul("Wychowanie do zycia w zespole");

        przedmiotDao.save(przedmiot);
    }

    @Test
    public void testSave() {
        String tytul = "Wychowanie do zycia w zespole";

        assertNotNull(przedmiot.getId());
        assertEquals(tytul, przedmiot.getTytul());
    }

    @Test
    public void testFindById() {
        int id = 1;
        Przedmiot result = przedmiotDao.findById(id);

        assertEquals(przedmiot, result);
    }

    @Test
    public void testUpdate() {
        String updatedNazwa = "Wychowanie do zycia z PO";
        przedmiot.setTytul(updatedNazwa);
        przedmiotDao.update(przedmiot);

        Przedmiot updatedPrzedmiot = przedmiotDao.findById(przedmiot.getId());
        assertEquals(updatedNazwa, updatedPrzedmiot.getTytul());
    }

    @Test
    public void testDelete() {
        przedmiotDao.delete(przedmiot);
        Przedmiot deletedPrzedmiot = przedmiotDao.findById(przedmiot.getId());

        assertNull(deletedPrzedmiot);
    }
}
