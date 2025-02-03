package com.wsis.atinsos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "NUMER_ALBUMU")
public class NumerAlbumu {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NUMER_ALBUMU", length = 50)
    private String numerAlbumu;

    @Column(name = "WAZNY")
    private Boolean wazny;

    @ManyToOne
    @JoinColumn(name = "UZYTKOWNIK_ID")
    private Uzytkownik uzytkownik;

    public NumerAlbumu() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumerAlbumu() {
        return numerAlbumu;
    }

    public void setNumerAlbumu(String numerAlbumu) {
        this.numerAlbumu = numerAlbumu;
    }

    public Boolean getWazny() {
        return wazny;
    }

    public void setWazny(Boolean wazny) {
        this.wazny = wazny;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }
}
