package com.example.galerija;

import javafx.beans.property.*;

public class Tehnika {
    private IntegerProperty ID_Tehnika;
    private StringProperty NazivTehnika;

    public Tehnika() {
        this.ID_Tehnika = new SimpleIntegerProperty();
        this.NazivTehnika = new SimpleStringProperty();
    }

    public Tehnika(int ID_Tehnika, String nazivTehnika) {
        this.ID_Tehnika = new SimpleIntegerProperty(ID_Tehnika);
        this.NazivTehnika = new SimpleStringProperty(nazivTehnika);
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

    public String getNazivTehnika() {
        return NazivTehnika.get();
    }

    public void setNazivTehnika(String nazivTehnika) {
        this.NazivTehnika.set(nazivTehnika);
    }

    public StringProperty NazivTehnikaProperty() {
        return NazivTehnika;
    }
}
