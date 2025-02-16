package com.wsis.atinsos;

import static org.junit.jupiter.api.Assertions.*;

import com.wsis.atinsos.dao.impl.UzytkownikDaoImpl;
import com.wsis.atinsos.dao.impl.ZarzadzanieKontemDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.wsis.atinsos.dao.ZarzadzanieKontemDao;
import com.wsis.atinsos.model.Uzytkownik;

@SpringBootTest
@Transactional
@Rollback(true)
public class ZarzadzanieKontemDaoImplTest {

    @Autowired
    private ZarzadzanieKontemDaoImpl zarzadzanieKontemDao;

    @Autowired
    private UzytkownikDaoImpl uzytkownikDao;

    private Uzytkownik testUser;

    @BeforeEach
    public void setUp() {
        testUser = new Uzytkownik();
        testUser.setId(100);
        testUser.setEmail("test@wsis.pl");
        testUser.setHaslo("oldPass");
        testUser.setBlokadaKonta(false);
        testUser.setCzasBlokady(null);

        uzytkownikDao.save(testUser);
        zarzadzanieKontemDao.aktualizujDaneKonta(testUser);
    }

    @Test
    public void testZmienHaslo_Success() {
        boolean result = zarzadzanieKontemDao.zmienHaslo(testUser.getId(), "oldPass", "newPass");
        assertTrue(result, "Powinno się udać zmienić hasło przy poprawnym starym haśle");
    }

    @Test
    public void testZmienHaslo_Fail() {
        boolean result = zarzadzanieKontemDao.zmienHaslo(testUser.getId(), "zleStareHaslo", "newPass");
        assertFalse(result, "Nie powinno się udać zmienić hasła przy niepoprawnym starym haśle");
    }

    @Test
    public void testZablokujKonto() {
        zarzadzanieKontemDao.zablokujKonto(testUser.getId());
        Uzytkownik updated = zarzadzanieKontemDaoFindById(testUser.getId());
        assertTrue(updated.getBlokadaKonta(), "Konto powinno być zablokowane");
        assertNotNull(updated.getCzasBlokady(), "Czas blokady powinien być ustawiony");
    }

    @Test
    public void testOdblokujKonto() {
        // Najpierw blokujemy
        zarzadzanieKontemDao.zablokujKonto(testUser.getId());
        // Następnie odblokowujemy
        zarzadzanieKontemDao.odblokujKonto(testUser.getId());

        Uzytkownik updated = zarzadzanieKontemDaoFindById(testUser.getId());
        assertFalse(updated.getBlokadaKonta(), "Konto powinno być odblokowane");
        assertNull(updated.getCzasBlokady(), "Czas blokady powinien być wyczyszczony");
    }

    @Test
    public void testResetujHaslo() {
        zarzadzanieKontemDao.resetujHaslo("test@wsis.pl");
        Uzytkownik updated = zarzadzanieKontemDaoFindById(testUser.getId());
        assertEquals("reset123", updated.getHaslo(), "Hasło powinno zostać zresetowane na 'reset123'");
    }

    @Test
    public void testAktualizujDaneKonta() {
        testUser.setEmail("nowyEmail@wsis.pl");
        zarzadzanieKontemDao.aktualizujDaneKonta(testUser);

        Uzytkownik updated = zarzadzanieKontemDaoFindById(testUser.getId());
        assertEquals("nowyEmail@wsis.pl", updated.getEmail());
    }

    @Test
    public void testUsunKonto() {
        zarzadzanieKontemDao.usunKonto(testUser.getId());
        Uzytkownik removed = zarzadzanieKontemDaoFindById(testUser.getId());
        assertNull(removed, "Konto powinno zostać usunięte");
    }

    // Pomocnicza metoda do pobierania użytkownika bezpośrednio z bazy
    private Uzytkownik zarzadzanieKontemDaoFindById(int id) {
        return uzytkownikDao.findById(id);
    }
}
