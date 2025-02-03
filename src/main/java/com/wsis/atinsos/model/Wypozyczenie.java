package com.wsis.atinsos.model;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "WYPOZYCZENIE")
public class Wypozyczenie {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DATA_WYPOZYCZENIA")
    private Date dataWypozyczenia;

    @Column(name = "TERMIN_ZWROTU")
    private Date terminZwrotu;

    @Column(name = "DATA_ZWROTU")
    private Date dataZwrotu;

    @ManyToOne
    @JoinColumn(name = "UZYTKOWNIK_ID")
    private Uzytkownik uzytkownik;

    @ManyToOne
    @JoinColumn(name = "POZYCJA_BIBLIOTECZNA_ID")
    private PozycjaBiblioteczna pozycjaBiblioteczna;

    public Wypozyczenie() {
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataWypozyczenia() {
        return dataWypozyczenia;
    }
    public void setDataWypozyczenia(Date dataWypozyczenia) {
        this.dataWypozyczenia = dataWypozyczenia;
    }

    public Date getTerminZwrotu() {
        return terminZwrotu;
    }
    public void setTerminZwrotu(Date terminZwrotu) {
        this.terminZwrotu = terminZwrotu;
    }

    public Date getDataZwrotu() {
        return dataZwrotu;
    }
    public void setDataZwrotu(Date dataZwrotu) {
        this.dataZwrotu = dataZwrotu;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }
    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public PozycjaBiblioteczna getPozycjaBiblioteczna() {
        return pozycjaBiblioteczna;
    }
    public void setPozycjaBiblioteczna(PozycjaBiblioteczna pozycjaBiblioteczna) {
        this.pozycjaBiblioteczna = pozycjaBiblioteczna;
    }
}
