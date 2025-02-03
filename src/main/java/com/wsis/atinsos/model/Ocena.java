package com.wsis.atinsos.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "OCENA")
public class Ocena {

    @Id
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "PRZEDMIOT_STUDENTA_ID")
    private PrzedmiotStudenta przedmiotStudenta;

    @Column(name = "WARTOSC", precision = 10, scale = 2)
    private BigDecimal wartosc;

    @Column(name = "RODZAJ", length = 50)
    private String rodzaj;

    @Column(name = "DATA_WYSTAWIENIA_OCENY")
    private Date dataWystawieniaOceny;

    public Ocena() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PrzedmiotStudenta getPrzedmiotStudenta() {
        return przedmiotStudenta;
    }

    public void setPrzedmiotStudenta(PrzedmiotStudenta przedmiotStudenta) {
        this.przedmiotStudenta = przedmiotStudenta;
    }

    public BigDecimal getWartosc() {
        return wartosc;
    }

    public void setWartosc(BigDecimal wartosc) {
        this.wartosc = wartosc;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }

    public Date getDataWystawieniaOceny() {
        return dataWystawieniaOceny;
    }

    public void setDataWystawieniaOceny(Date dataWystawieniaOceny) {
        this.dataWystawieniaOceny = dataWystawieniaOceny;
    }
}
