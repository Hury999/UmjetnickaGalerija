package com.example.galerija;

import javafx.beans.property.*;

public class Pravac {
    private final IntegerProperty idPravac;
    private final StringProperty nazivPravac;

    public Pravac(int idPravac, String nazivPravac) {
        this.idPravac = new SimpleIntegerProperty(idPravac);
        this.nazivPravac = new SimpleStringProperty(nazivPravac);
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

    public String getNazivPravac() {
        return nazivPravac.get();
    }

    public void setNazivPravac(String nazivPravac) {
        this.nazivPravac.set(nazivPravac);
    }

    public StringProperty nazivPravacProperty() {
        return nazivPravac;
    }
}
