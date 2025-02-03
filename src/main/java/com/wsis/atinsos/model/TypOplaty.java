package com.wsis.atinsos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TYP_OPLATY")
public class TypOplaty {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAZWA", length = 100)
    private String nazwa;

    public TypOplaty() {
    }

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