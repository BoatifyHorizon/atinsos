package com.wsis.atinsos.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "PROBA_LOGOWANIA")
public class ProbaLogowania {

    @Id
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "UZYTKOWNIK_ID")
    private Uzytkownik uzytkownik;

    @Column(name = "DATA")
    private Timestamp data;

    @Column(name = "CZY_UDANE")
    private Boolean czyUdane;

    public ProbaLogowania() {
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }
    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public Timestamp getData() {
        return data;
    }
    public void setData(Timestamp data) {
        this.data = data;
    }

    public Boolean getCzyUdane() {
        return czyUdane;
    }
    public void setCzyUdane(Boolean czyUdane) {
        this.czyUdane = czyUdane;
    }
}
