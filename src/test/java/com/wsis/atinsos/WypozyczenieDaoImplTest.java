package com.wsis.atinsos;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.wsis.atinsos.dao.impl.WypozyczenieDaoImpl;
import com.wsis.atinsos.model.Wypozyczenie;

@SpringBootTest
@Transactional
@Rollback(true)
public class WypozyczenieDaoImplTest {

    @Autowired
    private WypozyczenieDaoImpl wypozyczenieDao;

    private Wypozyczenie wypozyczenie;

    @BeforeEach
    public void setUp() {
        wypozyczenie = new Wypozyczenie();
        wypozyczenie.setId(1);
        wypozyczenie.setDataZwrotu(new Date(1739477637375L));

        wypozyczenieDao.save(wypozyczenie);
    }

    @Test
    public void testSave() {
        Date wartosc = new Date(1739477637375L);

        assertNotNull(wypozyczenie.getId());
        assertEquals(wartosc, wypozyczenie.getDataZwrotu());
    }

    @Test
    public void testFindById() {
        int id = 1;
        Wypozyczenie result = wypozyczenieDao.findById(id);

        assertEquals(wypozyczenie, result);
    }

    @Test
    public void testUpdate() {
        Date updatedData = new Date(1739477711073L);
        wypozyczenie.setDataZwrotu(updatedData);
        wypozyczenieDao.update(wypozyczenie);

        Wypozyczenie updatedWypozyczenie = wypozyczenieDao.findById(wypozyczenie.getId());
        assertEquals(updatedData, updatedWypozyczenie.getDataZwrotu());
    }

    @Test
    public void testDelete() {
        wypozyczenieDao.delete(wypozyczenie);
        Wypozyczenie deletedWypozyczenie = wypozyczenieDao.findById(wypozyczenie.getId());

        assertNull(deletedWypozyczenie);
    }
}
