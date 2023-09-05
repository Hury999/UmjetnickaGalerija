package com.example.galerija;

import javafx.beans.property.*;

public class Lokacija {
    private IntegerProperty ID_Lokacija;
    private StringProperty NazivLokacija;
    private IntegerProperty BrojProstorije;
    private IntegerProperty ID_Dvorana;

    public Lokacija() {
        this.ID_Lokacija = new SimpleIntegerProperty(0); // Set default value for ID_Lokacija
        this.NazivLokacija = new SimpleStringProperty(""); // Set default value for NazivLokacija
        this.BrojProstorije = new SimpleIntegerProperty(0); // Set default value for BrojProstorije
        this.ID_Dvorana = new SimpleIntegerProperty(0); // Set default value for ID_Dvorana
    }
    public Lokacija(int ID_Lokacija, String nazivLokacija, int brojProstorije, int ID_Dvorana) {
        this.ID_Lokacija = new SimpleIntegerProperty(ID_Lokacija);
        this.NazivLokacija = new SimpleStringProperty(nazivLokacija);
        this.BrojProstorije = new SimpleIntegerProperty(brojProstorije);
        this.ID_Dvorana = new SimpleIntegerProperty(ID_Dvorana);
    }

    public int getID_Lokacija() {
        return ID_Lokacija.get();
    }

    public void setID_Lokacija(int ID_Lokacija) {
        this.ID_Lokacija.set(ID_Lokacija);
    }

    public IntegerProperty ID_LokacijaProperty() {
        return ID_Lokacija;
    }

    public String getNazivLokacija() {
        return NazivLokacija.get();
    }

    public void setNazivLokacija(String nazivLokacija) {
        this.NazivLokacija.set(nazivLokacija);
    }

    public StringProperty NazivLokacijaProperty() {
        return NazivLokacija;
    }

    public int getBrojProstorije() {
        return BrojProstorije.get();
    }

    public void setBrojProstorije(int brojProstorije) {
        this.BrojProstorije.set(brojProstorije);
    }

    public IntegerProperty BrojProstorijeProperty() {
        return BrojProstorije;
    }

    public int getID_Dvorana() {
        return ID_Dvorana.get();
    }

    public void setID_Dvorana(int ID_Dvorana) {
        this.ID_Dvorana.set(ID_Dvorana);
    }

    public IntegerProperty ID_DvoranaProperty() {
        return ID_Dvorana;
    }
}

