package com.wsis.atinsos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "POZYCJA_BIBLIOTECZNA")
public class PozycjaBiblioteczna {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LICZBA_KOPII")
    private Integer liczbaKopii;

    public PozycjaBiblioteczna() {
    }

    // Getters & setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLiczbaKopii() {
        return liczbaKopii;
    }
    public void setLiczbaKopii(Integer liczbaKopii) {
        this.liczbaKopii = liczbaKopii;
    }
}
