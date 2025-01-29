package com.wsis.atinsos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "WYDZIAL")
public class Wydzial {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) // Only if you want automatic PK generation
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAZWA", nullable = false, length = 100)
    private String nazwa;

    // Constructors
    public Wydzial() {
    }

    // Getters & setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}
