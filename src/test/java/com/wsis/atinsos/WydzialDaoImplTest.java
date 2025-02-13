package com.wsis.atinsos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.wsis.atinsos.dao.impl.WydzialDaoImpl;
import com.wsis.atinsos.model.Wydzial;

@SpringBootTest
@Transactional
@Rollback(true)
public class WydzialDaoImplTest {

    @Autowired
    private WydzialDaoImpl wydzialDao;

    private Wydzial wydzial;

    @BeforeEach
    public void setUp() {
        wydzial = new Wydzial();
        wydzial.setId(1);
        wydzial.setNazwa("Wydzial A");

        wydzialDao.save(wydzial);
    }

    @Test
    public void testSave() {
        String name = "Wydzial A";

        assertNotNull(wydzial.getId());
        assertEquals(name, wydzial.getNazwa());
    }

    @Test
    public void testFindById() {
        int id = 1;
        Wydzial result = wydzialDao.findById(id);

        assertEquals(wydzial, result);    
    }

    @Test
    public void testUpdate() {
        String updatedName = "Updated Wydzial";
        wydzial.setNazwa(updatedName);    
        wydzialDao.update(wydzial);
    
        assertNotNull(wydzial.getId());
        assertEquals(updatedName, wydzial.getNazwa());
    }

    @Test
    public void testDelete() {     
        int wydzialId = wydzial.getId();   
        wydzialDao.delete(wydzial);

        Wydzial result = wydzialDao.findById(wydzialId);
        
        assertEquals(null, result);
    }

}
