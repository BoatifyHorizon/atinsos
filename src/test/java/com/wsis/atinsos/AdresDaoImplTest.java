package com.wsis.atinsos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.wsis.atinsos.dao.impl.AdresDaoImpl;
import com.wsis.atinsos.model.Adres;

@SpringBootTest
@Transactional
@Rollback(true)
public class AdresDaoImplTest {

    @Autowired
    private AdresDaoImpl adresDao;

    private Adres adres;

    @BeforeEach
    public void setUp() {
        adres = new Adres();
        adres.setId(1);
        adres.setLiniaAdresowa1("ul. Bambikowa 12");
        adres.setLiniaAdresowa2("12");

        adresDao.save(adres);
    }

    @Test
    public void testSave() {
        String liniaAdresowa1 = "ul. Bambikowa 12";
        String liniaAdresowa2 = "12";

        assertNotNull(adres.getId());
        assertEquals(liniaAdresowa1, adres.getLiniaAdresowa1());
        assertEquals(liniaAdresowa2, adres.getLiniaAdresowa2());
    }
    
    @Test
    public void testFindById() {
        int id = 1;
        Adres result = adresDao.findById(id);

        assertEquals(adres, result);
    }

    @Test
    public void testUpdate() {
        String updatedLiniaAdresowa1 = "ul. Bambikowa 13";
        adres.setLiniaAdresowa1(updatedLiniaAdresowa1);    
        adresDao.update(adres);
    
        assertNotNull(adres.getId());
        assertEquals(updatedLiniaAdresowa1, adres.getLiniaAdresowa1());
    }

    @Test
    public void testDelete() {
        int adresId = adres.getId();
        adresDao.delete(adres);

        Adres result = adresDao.findById(adresId);
        
        assertEquals(null, result);
    }
    
}
