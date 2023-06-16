package com.example.galerija;

import javafx.beans.property.*;

public class Umjetnik {
    private final IntegerProperty idUmjetnik;
    private final StringProperty ime;
    private final StringProperty prezime;
    private final StringProperty biografija;

    public Umjetnik(int idUmjetnik, String ime, String prezime, String biografija) {
        this.idUmjetnik = new SimpleIntegerProperty(idUmjetnik);
        this.ime = new SimpleStringProperty(ime);
        this.prezime = new SimpleStringProperty(prezime);
        this.biografija = new SimpleStringProperty(biografija);
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

    public String getIme() {
        return ime.get();
    }

    public void setIme(String ime) {
        this.ime.set(ime);
    }

    public StringProperty imeProperty() {
        return ime;
    }

    public String getPrezime() {
        return prezime.get();
    }

    public void setPrezime(String prezime) {
        this.prezime.set(prezime);
    }

    public StringProperty prezimeProperty() {
        return prezime;
    }

    public String getBiografija() {
        return biografija.get();
    }

    public void setBiografija(String biografija) {
        this.biografija.set(biografija);
    }

    public StringProperty biografijaProperty() {
        return biografija;
    }
}
