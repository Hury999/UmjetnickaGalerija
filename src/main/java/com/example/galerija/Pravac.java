package com.example.galerija;

import javafx.beans.property.*;

public class Pravac {
    private IntegerProperty ID_Pravac;
    private StringProperty NazivPravac;

    public Pravac()
    {
        this.ID_Pravac = new SimpleIntegerProperty();
        this.NazivPravac = new SimpleStringProperty();
    }
    public Pravac(int ID_Pravac, String nazivPravac) {
        this.ID_Pravac = new SimpleIntegerProperty(ID_Pravac);
        this.NazivPravac = new SimpleStringProperty(nazivPravac);
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

    public String getNazivPravac() {
        return NazivPravac.get();
    }

    public void setNazivPravac(String nazivPravac) {
        this.NazivPravac.set(nazivPravac);
    }

    public StringProperty NazivPravacProperty() {
        return NazivPravac;
    }
}
