package com.example.galerija;

public class Pravac {
    private int idPravac;
    private String nazivPravac;

    public Pravac(int idPravac, String nazivPravac) {
        this.idPravac = idPravac;
        this.nazivPravac = nazivPravac;
    }

    public int getIdPravac() {
        return idPravac;
    }

    public void setIdPravac(int idPravac) {
        this.idPravac = idPravac;
    }

    public String getNazivPravac() {
        return nazivPravac;
    }

    public void setNazivPravac(String nazivPravac) {
        this.nazivPravac = nazivPravac;
    }
}