package com.wsis.atinsos;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.wsis.atinsos.dao.impl.OcenaDaoImpl;
import com.wsis.atinsos.model.Ocena;

@SpringBootTest
@Transactional
@Rollback(true)
public class OcenaDaoImplTest {

    @Autowired
    private OcenaDaoImpl ocenaDao;

    private Ocena ocena;

    @BeforeEach
    public void setUp() {
        ocena = new Ocena();
        ocena.setId(1);
        ocena.setWartosc(new BigDecimal(5.0));

        ocenaDao.save(ocena);
    }

    @Test
    public void testSave() {
        BigDecimal wartosc = new BigDecimal(5.0);

        assertNotNull(ocena.getId());
        assertEquals(wartosc, ocena.getWartosc());
    }

    @Test
    public void testFindById() {
        int id = 1;
        Ocena result = ocenaDao.findById(id);

        assertEquals(ocena, result);
    }

    @Test
    public void testUpdate() {
        BigDecimal updatedWartosc = new BigDecimal(4.0);
        ocena.setWartosc(updatedWartosc);
        ocenaDao.update(ocena);

        Ocena updatedOcena = ocenaDao.findById(ocena.getId());
        assertEquals(updatedWartosc, updatedOcena.getWartosc());
    }

    @Test
    public void testDelete() {
        ocenaDao.delete(ocena);
        Ocena deletedOcena = ocenaDao.findById(ocena.getId());

        assertNull(deletedOcena);
    }
}