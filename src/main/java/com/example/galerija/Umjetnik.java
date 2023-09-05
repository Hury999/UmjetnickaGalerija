package com.example.galerija;

import javafx.beans.property.*;

public class Umjetnik {
    private IntegerProperty ID_Umjetnik;
    private StringProperty Ime;
    private StringProperty Prezime;
    private StringProperty Biografija;

    public Umjetnik()
    {
        this.ID_Umjetnik = new SimpleIntegerProperty();
        this.Ime = new SimpleStringProperty();
        this.Prezime = new SimpleStringProperty();
        this.Biografija = new SimpleStringProperty();
    }
    public Umjetnik(int ID_Umjetnik, String ime, String prezime, String biografija) {
        this.ID_Umjetnik = new SimpleIntegerProperty(ID_Umjetnik);
        this.Ime = new SimpleStringProperty(ime);
        this.Prezime = new SimpleStringProperty(prezime);
        this.Biografija = new SimpleStringProperty(biografija);
    }

    public IntegerProperty IdUmjetnik()
    {
        return ID_Umjetnik;
    }

    public StringProperty Ime()
    {
        return Ime;
    }

    public StringProperty Prezime()
    {
        return Prezime;
    }
    public StringProperty Biografija()
    {
        return Biografija;
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

    public String getIme() {
        return Ime.get();
    }

    public void setIme(String ime) {
        this.Ime.set(ime);
    }

    public StringProperty ImeProperty() {
        return Ime;
    }

    public String getPrezime() {
        return Prezime.get();
    }

    public void setPrezime(String prezime) {
        this.Prezime.set(prezime);
    }

    public StringProperty PrezimeProperty() {
        return Prezime;
    }

    public String getBiografija() {
        return Biografija.get();
    }

    public void setBiografija(String biografija) {
        this.Biografija.set(biografija);
    }

    public StringProperty BiografijaProperty() {
        return Biografija;
    }
}
