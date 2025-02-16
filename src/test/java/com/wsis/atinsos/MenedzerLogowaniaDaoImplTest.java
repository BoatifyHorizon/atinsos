package com.wsis.atinsos;

import static org.junit.jupiter.api.Assertions.*;

import com.wsis.atinsos.dao.impl.MenedzerLogowaniaDaoImpl;
import com.wsis.atinsos.dao.impl.UzytkownikDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.wsis.atinsos.model.StatusKonta;
import com.wsis.atinsos.model.Uzytkownik;

@SpringBootTest
@Transactional
@Rollback(true)
public class MenedzerLogowaniaDaoImplTest {

    @Autowired
    private MenedzerLogowaniaDaoImpl menedzerLogowaniaDao;

    @Autowired
    private UzytkownikDaoImpl uzytkownikDao;

    private Uzytkownik testUser;

    @BeforeEach
    public void setUp() {
        testUser = new Uzytkownik();
        testUser.setId(200);
        testUser.setEmail("login@test.pl");
        testUser.setHaslo("haslo123");

        uzytkownikDao.save(testUser);
    }

    @Test
    public void testZaloguj_PoprawneDane() {
        boolean result = menedzerLogowaniaDao.zaloguj("login@test.pl", "haslo123");
        assertTrue(result, "Powinno się udać zalogować z poprawnymi danymi");
    }

    @Test
    public void testZaloguj_NieprawidloweDane() {
        boolean result = menedzerLogowaniaDao.zaloguj("login@test.pl", "zleHaslo");
        assertFalse(result, "Nie powinno się udać zalogować z błędnym hasłem");
    }

    @Test
    public void testWyloguj() {
        // Zwykle nic nie sprawdzamy, bo wylogowanie może być tylko operacją w pamięci
        menedzerLogowaniaDao.wyloguj(testUser.getId());
        // ewentualnie można sprawdzić jakiś "ostatnioWylogowany" – zależnie od Twojej implementacji
    }

    @Test
    public void testSprawdzStatusKonta() {
        StatusKonta status = menedzerLogowaniaDao.sprawdzStatusKonta(testUser.getId());
        assertEquals(StatusKonta.AKTYWNE, status);
    }

    @Test
    public void testZweryfikujDaneLogowania() {
        boolean weryfikacja = menedzerLogowaniaDao.zweryfikujDaneLogowania("login@test.pl", "haslo123");
        assertTrue(weryfikacja, "Dane logowania powinny się zgadzać");
    }

    @Test
    public void testZapiszProbeLogowania_i_sprawdzLiczbeNieudanychProb() {
        menedzerLogowaniaDao.zapiszProbeLogowania(testUser.getId(), false);
        menedzerLogowaniaDao.zapiszProbeLogowania(testUser.getId(), false);
        menedzerLogowaniaDao.zapiszProbeLogowania(testUser.getId(), true);

        int nieudane = menedzerLogowaniaDao.sprawdzLiczbeNieudanychProb(testUser.getId());
        assertEquals(2, nieudane, "Powinno być 2 nieudanych prób logowania");
    }
}
