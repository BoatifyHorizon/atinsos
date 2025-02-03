package com.wsis.atinsos.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "PRZEDMIOT")
public class Przedmiot {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "OPIS", length = 500)
    private String opis;

    @Column(name = "TYTUL", length = 255)
    private String tytul;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "SEMESTR")
    private Integer semestr;

    @Column(name = "ROK_AKADEMICKI", length = 20)
    private String rokAkademicki;

    @Column(name = "ZAPISY_OD")
    private Timestamp zapisyOd;

    @Column(name = "ZAPISY_DO")
    private Timestamp zapisyDo;

    @Column(name = "NUMER_SALI", length = 50)
    private String numerSali;

    @ManyToOne
    @JoinColumn(name = "PROWADZACY_ID")
    private Uzytkownik prowadzacy;

    @ManyToOne
    @JoinColumn(name = "WYDZIAL_ID")
    private Wydzial wydzial;

    public Przedmiot() {
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }
    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getTytul() {
        return tytul;
    }
    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Integer getSemestr() {
        return semestr;
    }
    public void setSemestr(Integer semestr) {
        this.semestr = semestr;
    }

    public String getRokAkademicki() {
        return rokAkademicki;
    }
    public void setRokAkademicki(String rokAkademicki) {
        this.rokAkademicki = rokAkademicki;
    }

    public Timestamp getZapisyOd() {
        return zapisyOd;
    }
    public void setZapisyOd(Timestamp zapisyOd) {
        this.zapisyOd = zapisyOd;
    }

    public Timestamp getZapisyDo() {
        return zapisyDo;
    }
    public void setZapisyDo(Timestamp zapisyDo) {
        this.zapisyDo = zapisyDo;
    }

    public String getNumerSali() {
        return numerSali;
    }
    public void setNumerSali(String numerSali) {
        this.numerSali = numerSali;
    }

    public Uzytkownik getProwadzacy() {
        return prowadzacy;
    }
    public void setProwadzacy(Uzytkownik prowadzacy) {
        this.prowadzacy = prowadzacy;
    }

    public Wydzial getWydzial() {
        return wydzial;
    }
    public void setWydzial(Wydzial wydzial) {
        this.wydzial = wydzial;
    }
}
