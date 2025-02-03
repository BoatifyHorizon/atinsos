package com.wsis.atinsos.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "PLATNOSC")
public class Platnosc {

    @Id
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private Uzytkownik student;

    @ManyToOne
    @JoinColumn(name = "TYP_OPLATY_ID")
    private TypOplaty typOplaty;

    @Column(name = "KWOTA", precision = 10, scale = 2)
    private BigDecimal kwota;

    @Column(name = "DATA_PLATNOSCI")
    private Date dataPlatnosci;

    public Platnosc() {
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

    public TypOplaty getTypOplaty() {
        return typOplaty;
    }
    public void setTypOplaty(TypOplaty typOplaty) {
        this.typOplaty = typOplaty;
    }

    public BigDecimal getKwota() {
        return kwota;
    }
    public void setKwota(BigDecimal kwota) {
        this.kwota = kwota;
    }

    public Date getDataPlatnosci() {
        return dataPlatnosci;
    }
    public void setDataPlatnosci(Date dataPlatnosci) {
        this.dataPlatnosci = dataPlatnosci;
    }
}
