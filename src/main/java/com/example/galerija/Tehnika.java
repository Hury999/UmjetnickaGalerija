package com.example.galerija;

import javafx.beans.property.*;

public class Tehnika {
    private final IntegerProperty idTehnika;
    private final StringProperty nazivTehnika;

    public Tehnika(int idTehnika, String nazivTehnika) {
        this.idTehnika = new SimpleIntegerProperty(idTehnika);
        this.nazivTehnika = new SimpleStringProperty(nazivTehnika);
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

    public String getNazivTehnika() {
        return nazivTehnika.get();
    }

    public void setNazivTehnika(String nazivTehnika) {
        this.nazivTehnika.set(nazivTehnika);
    }

    public StringProperty nazivTehnikaProperty() {
        return nazivTehnika;
    }
}
