package com.wsis.atinsos.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "PRZEDMIOT_STUDENTA")
public class PrzedmiotStudenta {

    @Id
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private Uzytkownik student;

    @ManyToOne
    @JoinColumn(name = "PRZEDMIOT_ID")
    private Przedmiot przedmiot;

    @Column(name = "CZAS_ZAPISANIA_SIE")
    private Timestamp czasZapisaniaSie;

    public PrzedmiotStudenta() {
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Uzytkownik getStudent() {
        return student;
    }
    public void setStudent(Uzytkownik student) {
        this.student = student;
    }

    public Przedmiot getPrzedmiot() {
        return przedmiot;
    }
    public void setPrzedmiot(Przedmiot przedmiot) {
        this.przedmiot = przedmiot;
    }

    public Timestamp getCzasZapisaniaSie() {
        return czasZapisaniaSie;
    }
    public void setCzasZapisaniaSie(Timestamp czasZapisaniaSie) {
        this.czasZapisaniaSie = czasZapisaniaSie;
    }
}
