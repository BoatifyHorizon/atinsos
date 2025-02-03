package com.wsis.atinsos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ROLA")
public class Rola {

    @Id
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "WYDZIAL_ID")
    private Wydzial wydzial;

    @Column(name = "NAZWA", length = 50)
    private String nazwa;

    @Column(name = "IS_ADMIN")
    private Boolean isAdmin;

    public Rola() {
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Wydzial getWydzial() {
        return wydzial;
    }
    public void setWydzial(Wydzial wydzial) {
        this.wydzial = wydzial;
    }

    public String getNazwa() {
        return nazwa;
    }
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }
    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
