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

import com.wsis.atinsos.dao.impl.PlatnoscDaoImpl;
import com.wsis.atinsos.model.Platnosc;

@SpringBootTest
@Transactional
@Rollback(true)
public class PlatnoscDaoImplTest {

    @Autowired
    private PlatnoscDaoImpl platnoscDao;

    private Platnosc platnosc;

    @BeforeEach
    public void setUp() {
        platnosc = new Platnosc();
        platnosc.setId(1);
        platnosc.setKwota(new BigDecimal(100.0));

        platnoscDao.save(platnosc);
    }

    @Test
    public void testSave() {
        BigDecimal kwota = new BigDecimal(100.0);

        assertNotNull(platnosc.getId());
        assertEquals(kwota, platnosc.getKwota());
    }

    @Test
    public void testFindById() {
        int id = 1;
        Platnosc result = platnoscDao.findById(id);

        assertEquals(platnosc, result);
    }

    @Test
    public void testUpdate() {
        BigDecimal updatedKwota = new BigDecimal(150.0);
        platnosc.setKwota(updatedKwota);
        platnoscDao.update(platnosc);

        Platnosc updatedPlatnosc = platnoscDao.findById(platnosc.getId());
        assertEquals(updatedKwota, updatedPlatnosc.getKwota());
    }

    @Test
    public void testDelete() {
        platnoscDao.delete(platnosc);
        Platnosc deletedPlatnosc = platnoscDao.findById(platnosc.getId());

        assertNull(deletedPlatnosc);
    }
}
