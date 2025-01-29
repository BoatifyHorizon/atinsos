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
    @JoinColumn(name = "PRZEDMIOT_STUDENTA_ID")  // references PRZEDMIOT_STUDENTA(ID)
    private PrzedmiotStudenta przedmiotStudenta;

    // e.g. 2.0, 3.0, 3.5, 4.0, 4.5, 5.0
    @Column(name = "WARTOSC", precision = 10, scale = 2)
    private BigDecimal wartosc;

    @Column(name = "RODZAJ", length = 50)
    private String rodzaj;

    @Column(name = "DATA_WYSTAWIENIA_OCENY")
    private Date dataWystawieniaOceny;

    public Ocena() {
    }

    // Getters & setters
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
