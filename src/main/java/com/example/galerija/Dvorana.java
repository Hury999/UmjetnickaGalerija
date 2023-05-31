package com.example.galerija;

public class Dvorana {
    private int idDvorana;
    private String nazivDvorana;

    public Dvorana(int idDvorana, String nazivDvorana) {
        this.idDvorana = idDvorana;
        this.nazivDvorana = nazivDvorana;
    }

    public int getIdDvorana() {
        return idDvorana;
    }

    public void setIdDvorana(int idDvorana) {
        this.idDvorana = idDvorana;
    }

    public String getNazivDvorana() {
        return nazivDvorana;
    }

    public void setNazivDvorana(String nazivDvorana) {
        this.nazivDvorana = nazivDvorana;
    }
}
