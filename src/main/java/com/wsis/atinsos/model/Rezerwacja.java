package com.wsis.atinsos.model;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "REZERWACJA")
public class Rezerwacja {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DATA_REZERWACJI")
    private Date dataRezerwacji;

    @Column(name = "STATUS_REZERWACJI", length = 50)
    private String statusRezerwacji;

    @ManyToOne
    @JoinColumn(name = "UZYTKOWNIK_ID")
    private Uzytkownik uzytkownik;

    @ManyToOne
    @JoinColumn(name = "POZYCJA_BIBLIOTECZNA_ID")
    private PozycjaBiblioteczna pozycjaBiblioteczna;

    public Rezerwacja() {
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataRezerwacji() {
        return dataRezerwacji;
    }
    public void setDataRezerwacji(Date dataRezerwacji) {
        this.dataRezerwacji = dataRezerwacji;
    }

    public String getStatusRezerwacji() {
        return statusRezerwacji;
    }
    public void setStatusRezerwacji(String statusRezerwacji) {
        this.statusRezerwacji = statusRezerwacji;
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
