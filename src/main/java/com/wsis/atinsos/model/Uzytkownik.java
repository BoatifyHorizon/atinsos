package com.wsis.atinsos.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "UZYTKOWNIK")
public class Uzytkownik {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NUMER_ALBUMU", length = 50)
    private String numerAlbumu;

    @Column(name = "AKTYWNY")
    private Boolean aktywny;

    @Column(name = "EMAIL", length = 255)
    private String email;

    @Column(name = "HASLO", length = 255)
    private String haslo;

    @Column(name = "TELEFON_2FA", length = 50)
    private String telefon2fa;

    @Column(name = "IMIE", length = 100)
    private String imie;

    @Column(name = "NAZWISKO", length = 100)
    private String nazwisko;

    @ManyToOne
    @JoinColumn(name = "ADRES_ZAMELDOWANIA")  // FK to ADRES(ID)
    private Adres adresZameldowania;

    @ManyToOne
    @JoinColumn(name = "ADRES_KORESPONDENCYJNY")  // FK to ADRES(ID)
    private Adres adresKorespondencyjny;

    @Column(name = "BLOKADA_KONTA")
    private Boolean blokadaKonta;

    @Column(name = "CZAS_BLOKADY")
    private Timestamp czasBlokady;

    /**
     * Many-to-many with Rola via UZYTKOWNIK_ROLA bridging table.
     * If you want to load the roles from DB in an object graph, you can map it like this:
     */
    @ManyToMany
    @JoinTable(name = "UZYTKOWNIK_ROLA",
            joinColumns = {@JoinColumn(name = "UZYTKOWNIK_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLA_ID")})
    private Set<Rola> role = new HashSet<>();

    public Uzytkownik() {
    }

    // Getters & setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumerAlbumu() {
        return numerAlbumu;
    }
    public void setNumerAlbumu(String numerAlbumu) {
        this.numerAlbumu = numerAlbumu;
    }

    public Boolean getAktywny() {
        return aktywny;
    }
    public void setAktywny(Boolean aktywny) {
        this.aktywny = aktywny;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getHaslo() {
        return haslo;
    }
    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getTelefon2fa() {
        return telefon2fa;
    }
    public void setTelefon2fa(String telefon2fa) {
        this.telefon2fa = telefon2fa;
    }

    public String getImie() {
        return imie;
    }
    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Adres getAdresZameldowania() {
        return adresZameldowania;
    }
    public void setAdresZameldowania(Adres adresZameldowania) {
        this.adresZameldowania = adresZameldowania;
    }

    public Adres getAdresKorespondencyjny() {
        return adresKorespondencyjny;
    }
    public void setAdresKorespondencyjny(Adres adresKorespondencyjny) {
        this.adresKorespondencyjny = adresKorespondencyjny;
    }

    public Boolean getBlokadaKonta() {
        return blokadaKonta;
    }
    public void setBlokadaKonta(Boolean blokadaKonta) {
        this.blokadaKonta = blokadaKonta;
    }

    public Timestamp getCzasBlokady() {
        return czasBlokady;
    }
    public void setCzasBlokady(Timestamp czasBlokady) {
        this.czasBlokady = czasBlokady;
    }

    public Set<Rola> getRole() {
        return role;
    }
    public void setRole(Set<Rola> role) {
        this.role = role;
    }
}

