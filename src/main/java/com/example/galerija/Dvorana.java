package com.example.galerija;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Dvorana {
    private final IntegerProperty idDvorana;
    private final StringProperty nazivDvorana;

    public Dvorana(int idDvorana, String nazivDvorana) {
        this.idDvorana = new SimpleIntegerProperty(idDvorana);
        this.nazivDvorana = new SimpleStringProperty(nazivDvorana);
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

    public String getNazivDvorana() {
        return nazivDvorana.get();
    }

    public void setNazivDvorana(String nazivDvorana) {
        this.nazivDvorana.set(nazivDvorana);
    }

    public StringProperty nazivDvoranaProperty() {
        return nazivDvorana;
    }
}