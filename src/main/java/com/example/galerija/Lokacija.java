package com.example.galerija;

public class Lokacija {
    private int idLokacija;
    private String nazivLokacija;
    private int brojProstorije;
    private int idDvorana;

    public Lokacija(int idLokacija, String nazivLokacija, int brojProstorije, int idDvorana) {
        this.idLokacija = idLokacija;
        this.nazivLokacija = nazivLokacija;
        this.brojProstorije = brojProstorije;
        this.idDvorana = idDvorana;
    }

    public int getIdLokacija() {
        return idLokacija;
    }

    public void setIdLokacija(int idLokacija) {
        this.idLokacija = idLokacija;
    }

    public String getNazivLokacija() {
        return nazivLokacija;
    }

    public void setNazivLokacija(String nazivLokacija) {
        this.nazivLokacija = nazivLokacija;
    }

    public int getBrojProstorije() {
        return brojProstorije;
    }

    public void setBrojProstorije(int brojProstorije) {
        this.brojProstorije = brojProstorije;
    }

    public int getIdDvorana() {
        return idDvorana;
    }

    public void setIdDvorana(int idDvorana) {
        this.idDvorana = idDvorana;
    }
}
