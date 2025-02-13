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

import com.wsis.atinsos.dao.impl.RezerwacjaDaoImpl;
import com.wsis.atinsos.model.Rezerwacja;

@SpringBootTest
@Transactional
@Rollback(true)
public class RezerwacjaDaoImplTest {

    @Autowired
    private RezerwacjaDaoImpl rezerwacjaDao;

    private Rezerwacja rezerwacja;

    @BeforeEach
    public void setUp() {
        rezerwacja = new Rezerwacja();
        rezerwacja.setId(1);
        rezerwacja.setStatusRezerwacji("Okupowana");

        rezerwacjaDao.save(rezerwacja);
    }

    @Test
    public void testSave() {
        String status = "Okupowana";

        assertNotNull(rezerwacja.getId());
        assertEquals(status, rezerwacja.getStatusRezerwacji());
    }

    @Test
    public void testFindById() {
        int id = 1;
        Rezerwacja result = rezerwacjaDao.findById(id);

        assertEquals(rezerwacja, result);
    }

    @Test
    public void testUpdate() {
        String updatedStatus = "Wolna";
        rezerwacja.setStatusRezerwacji(updatedStatus);
        rezerwacjaDao.update(rezerwacja);

        Rezerwacja updatedRezerwacja = rezerwacjaDao.findById(rezerwacja.getId());
        assertEquals(updatedStatus, updatedRezerwacja.getStatusRezerwacji());
    }

    @Test
    public void testDelete() {
        rezerwacjaDao.delete(rezerwacja);
        Rezerwacja deletedRezerwacja = rezerwacjaDao.findById(rezerwacja.getId());

        assertNull(deletedRezerwacja);
    }
}
