package com.wsis.atinsos.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ZAJECIE")
public class Zajecie {

    @Id
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "PRZEDMIOT_ID")  // references PRZEDMIOT(ID)
    private Przedmiot przedmiot;

    @Column(name = "ROZPOCZECIE")
    private Timestamp rozpoczecie;

    @Column(name = "ZAKONCZENIE")
    private Timestamp zakonczenie;

    public Zajecie() {
    }

    // Getters & setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Przedmiot getPrzedmiot() {
        return przedmiot;
    }
    public void setPrzedmiot(Przedmiot przedmiot) {
        this.przedmiot = przedmiot;
    }

    public Timestamp getRozpoczecie() {
        return rozpoczecie;
    }
    public void setRozpoczecie(Timestamp rozpoczecie) {
        this.rozpoczecie = rozpoczecie;
    }

    public Timestamp getZakonczenie() {
        return zakonczenie;
    }
    public void setZakonczenie(Timestamp zakonczenie) {
        this.zakonczenie = zakonczenie;
    }
}
