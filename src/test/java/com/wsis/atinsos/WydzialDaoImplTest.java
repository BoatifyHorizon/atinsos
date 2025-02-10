package com.wsis.atinsos;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.wsis.atinsos.model.Wydzial;
import com.wsis.atinsos.dao.impl.WydzialDaoImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class WydzialDaoImplTest {

    @InjectMocks
    private WydzialDaoImpl wydzialDao;

    @Mock
    private EntityManager entityManager;

    private Wydzial wydzial;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        wydzial = new Wydzial();
        wydzial.setId(1);
        wydzial.setNazwa("Wydzial A");
    }

    @Test
    void shouldPersistEntity_WhenValidWydzialIsSaved() {
        var name = "Wydzial A";

        doNothing().when(entityManager).persist(wydzial);

        wydzialDao.save(wydzial);

        verify(entityManager, times(1)).persist(wydzial);

        assertNotNull(wydzial.getId());
        assertEquals(name, wydzial.getNazwa());
    }

    @Test
    void shouldReturnWydzial_WhenExistingIdIsProvided() {
        var id = 1;
        when(entityManager.find(Wydzial.class, id)).thenReturn(wydzial);

        Wydzial result = wydzialDao.findById(id);

        assertEquals(wydzial, result);
        verify(entityManager, times(1)).find(Wydzial.class, id);
    }

    @Test
    void shouldMergeEntity_WhenValidWydzialIsUpdated() {
        var updatedName = "Updated Wydzial";
        wydzial.setNazwa(updatedName);
        when(entityManager.merge(wydzial)).thenReturn(wydzial);

        wydzialDao.update(wydzial);

        verify(entityManager, times(1)).merge(wydzial);

        assertNotNull(wydzial.getId());
        assertEquals(updatedName, wydzial.getNazwa());
    }

    @Test
    void shouldRemoveEntity_WhenWydzialExistsInPersistenceContext() {
        when(entityManager.contains(wydzial)).thenReturn(true);

        wydzialDao.delete(wydzial);

        verify(entityManager, times(1)).remove(wydzial);
    }

}
