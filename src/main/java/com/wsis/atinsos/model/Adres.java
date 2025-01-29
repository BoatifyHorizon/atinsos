package com.wsis.atinsos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ADRES")
public class Adres {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LINIA_ADRESOWA_1", length = 255)
    private String liniaAdresowa1;

    @Column(name = "LINIA_ADRESOWA_2", length = 255)
    private String liniaAdresowa2;

    public Adres() {
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getLiniaAdresowa1() {
        return liniaAdresowa1;
    }
    public void setLiniaAdresowa1(String liniaAdresowa1) {
        this.liniaAdresowa1 = liniaAdresowa1;
    }

    public String getLiniaAdresowa2() {
        return liniaAdresowa2;
    }
    public void setLiniaAdresowa2(String liniaAdresowa2) {
        this.liniaAdresowa2 = liniaAdresowa2;
    }
}
