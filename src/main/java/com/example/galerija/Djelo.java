package com.example.galerija;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Djelo {
    private final IntegerProperty idDjelo;
    private final StringProperty naslov;
    private final IntegerProperty godinaNastanka;
    private final IntegerProperty idUmjetnik;
    private final IntegerProperty idPravac;
    private final IntegerProperty idTehnika;
    private final IntegerProperty idIzlozba;
    private final IntegerProperty idLokacija;

    public Djelo(int idDjelo, String naslov, int godinaNastanka, int idUmjetnik, int idPravac, int idTehnika, int idIzlozba, int idLokacija) {
        this.idDjelo = new SimpleIntegerProperty(idDjelo);
        this.naslov = new SimpleStringProperty(naslov);
        this.godinaNastanka = new SimpleIntegerProperty(godinaNastanka);
        this.idUmjetnik = new SimpleIntegerProperty(idUmjetnik);
        this.idPravac = new SimpleIntegerProperty(idPravac);
        this.idTehnika = new SimpleIntegerProperty(idTehnika);
        this.idIzlozba = new SimpleIntegerProperty(idIzlozba);
        this.idLokacija = new SimpleIntegerProperty(idLokacija);
    }

    public int getIdDjelo() {
        return idDjelo.get();
    }

    public void setIdDjelo(int idDjelo) {
        this.idDjelo.set(idDjelo);
    }

    public IntegerProperty idDjeloProperty() {
        return idDjelo;
    }

    public String getNaslov() {
        return naslov.get();
    }

    public void setNaslov(String naslov) {
        this.naslov.set(naslov);
    }

    public StringProperty naslovProperty() {
        return naslov;
    }

    public int getGodinaNastanka() {
        return godinaNastanka.get();
    }

    public void setGodinaNastanka(int godinaNastanka) {
        this.godinaNastanka.set(godinaNastanka);
    }

    public IntegerProperty godinaNastankaProperty() {
        return godinaNastanka;
    }

    public int getIdUmjetnik() {
        return idUmjetnik.get();
    }

    public void setIdUmjetnik(int idUmjetnik) {
        this.idUmjetnik.set(idUmjetnik);
    }

    public IntegerProperty idUmjetnikProperty() {
        return idUmjetnik;
    }

    public int getIdPravac() {
        return idPravac.get();
    }

    public void setIdPravac(int idPravac) {
        this.idPravac.set(idPravac);
    }

    public IntegerProperty idPravacProperty() {
        return idPravac;
    }

    public int getIdTehnika() {
        return idTehnika.get();
    }

    public void setIdTehnika(int idTehnika) {
        this.idTehnika.set(idTehnika);
    }

    public IntegerProperty idTehnikaProperty() {
        return idTehnika;
    }

    public int getIdIzlozba() {
        return idIzlozba.get();
    }

    public void setIdIzlozba(int idIzlozba) {
        this.idIzlozba.set(idIzlozba);
    }

    public IntegerProperty idIzlozbaProperty() {
        return idIzlozba;
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
}