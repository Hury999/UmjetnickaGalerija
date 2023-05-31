package com.example.galerija;

public class Tehnika {
    private int idTehnika;
    private String nazivTehnika;

    public Tehnika(int idTehnika, String nazivTehnika) {
        this.idTehnika = idTehnika;
        this.nazivTehnika = nazivTehnika;
    }

    public int getIdTehnika() {
        return idTehnika;
    }

    public void setIdTehnika(int idTehnika) {
        this.idTehnika = idTehnika;
    }

    public String getNazivTehnika() {
        return nazivTehnika;
    }

    public void setNazivTehnika(String nazivTehnika) {
        this.nazivTehnika = nazivTehnika;
    }
}