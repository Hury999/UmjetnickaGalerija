package com.example.galerija;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Djelo {
    private  IntegerProperty ID_Djelo;
    private  StringProperty Naslov;
    private  IntegerProperty GodinaNastanka;
    private  IntegerProperty ID_Umjetnik;
    private  IntegerProperty ID_Pravac;
    private  IntegerProperty ID_Tehnika;
    private  IntegerProperty ID_Izlozba;
    private  IntegerProperty ID_Lokacija;

    public Djelo() {
        // Parameterless constructor
        this.ID_Djelo = new SimpleIntegerProperty();
        this.Naslov = new SimpleStringProperty();
        this.GodinaNastanka = new SimpleIntegerProperty();
        this.ID_Umjetnik = new SimpleIntegerProperty();
        this.ID_Pravac = new SimpleIntegerProperty();
        this.ID_Tehnika = new SimpleIntegerProperty();
        this.ID_Izlozba = new SimpleIntegerProperty();
        this.ID_Lokacija = new SimpleIntegerProperty();
    }
    public Djelo(int ID_Djelo, String naslov, int godinaNastanka, int IDUmjetnik, int IDPravac, int ID_Tehnika, int ID_Izlozba, int ID_Lokacija) {
        this.ID_Djelo = new SimpleIntegerProperty(ID_Djelo);
        this.Naslov = new SimpleStringProperty(naslov);
        this.GodinaNastanka = new SimpleIntegerProperty(godinaNastanka);
        this.ID_Umjetnik = new SimpleIntegerProperty(IDUmjetnik);
        this.ID_Pravac = new SimpleIntegerProperty(IDPravac);
        this.ID_Tehnika = new SimpleIntegerProperty(ID_Tehnika);
        this.ID_Izlozba = new SimpleIntegerProperty(ID_Izlozba);
        this.ID_Lokacija = new SimpleIntegerProperty(ID_Lokacija);
    }

    public int getID_Djelo() {
        return ID_Djelo.get();
    }

    public void setID_Djelo(int ID_Djelo) {
        this.ID_Djelo.set(ID_Djelo);
    }

    public IntegerProperty ID_DjeloProperty() {
        return ID_Djelo;
    }

    public String getNaslov() {
        return Naslov.get();
    }

    public void setNaslov(String naslov) {
        this.Naslov.set(naslov);
    }

    public StringProperty NaslovProperty() {
        return Naslov;
    }

    public int getGodinaNastanka() {
        return GodinaNastanka.get();
    }

    public void setGodinaNastanka(int godinaNastanka) {
        this.GodinaNastanka.set(godinaNastanka);
    }

    public IntegerProperty GodinaNastankaProperty() {
        return GodinaNastanka;
    }

    public int getID_Umjetnik() {
        return ID_Umjetnik.get();
    }

    public void setID_Umjetnik(int ID_Umjetnik) {
        this.ID_Umjetnik.set(ID_Umjetnik);
    }

    public IntegerProperty ID_UmjetnikProperty() {
        return ID_Umjetnik;
    }

    public int getID_Pravac() {
        return ID_Pravac.get();
    }

    public void setID_Pravac(int ID_Pravac) {
        this.ID_Pravac.set(ID_Pravac);
    }

    public IntegerProperty ID_PravacProperty() {
        return ID_Pravac;
    }

    public int getID_Tehnika() {
        return ID_Tehnika.get();
    }

    public void setID_Tehnika(int ID_Tehnika) {
        this.ID_Tehnika.set(ID_Tehnika);
    }

    public IntegerProperty ID_TehnikaProperty() {
        return ID_Tehnika;
    }

    public int getID_Izlozba() {
        return ID_Izlozba.get();
    }

    public void setID_Izlozba(int ID_Izlozba) {
        this.ID_Izlozba.set(ID_Izlozba);
    }

    public IntegerProperty ID_IzlozbaProperty() {
        return ID_Izlozba;
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
}