package com.example.galerija;

import javafx.beans.property.*;

public class Lokacija {
    private final IntegerProperty idLokacija;
    private final StringProperty nazivLokacija;
    private final IntegerProperty brojProstorije;
    private final IntegerProperty idDvorana;

    public Lokacija(int idLokacija, String nazivLokacija, int brojProstorije, int idDvorana) {
        this.idLokacija = new SimpleIntegerProperty(idLokacija);
        this.nazivLokacija = new SimpleStringProperty(nazivLokacija);
        this.brojProstorije = new SimpleIntegerProperty(brojProstorije);
        this.idDvorana = new SimpleIntegerProperty(idDvorana);
    }

    public int getIdLokacija() {
        return idLokacija.get();
    }

    public void setIdLokacija(int idLokacija) {
        this.idLokacija.set(idLokacija);
    }

    public IntegerProperty idLokacijaProperty() {
        return idLokacija;
    }

    public String getNazivLokacija() {
        return nazivLokacija.get();
    }

    public void setNazivLokacija(String nazivLokacija) {
        this.nazivLokacija.set(nazivLokacija);
    }

    public StringProperty nazivLokacijaProperty() {
        return nazivLokacija;
    }

    public int getBrojProstorije() {
        return brojProstorije.get();
    }

    public void setBrojProstorije(int brojProstorije) {
        this.brojProstorije.set(brojProstorije);
    }

    public IntegerProperty brojProstorijeProperty() {
        return brojProstorije;
    }

    public int getIdDvorana() {
        return idDvorana.get();
    }

    public void setIdDvorana(int idDvorana) {
        this.idDvorana.set(idDvorana);
    }

    public IntegerProperty idDvoranaProperty() {
        return idDvorana;
    }
}

