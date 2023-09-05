package com.example.galerija;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Dvorana {
    private IntegerProperty ID_Dvorana;
    private StringProperty NazivDvorana;

    public Dvorana()
    {
        this.ID_Dvorana = new SimpleIntegerProperty();
        this.NazivDvorana = new SimpleStringProperty();
    }
    public Dvorana(int ID_Dvorana, String nazivDvorana) {
        this.ID_Dvorana = new SimpleIntegerProperty(ID_Dvorana);
        this.NazivDvorana = new SimpleStringProperty(nazivDvorana);
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

    public String getNazivDvorana() {
        return NazivDvorana.get();
    }

    public void setNazivDvorana(String nazivDvorana) {
        this.NazivDvorana.set(nazivDvorana);
    }

    public StringProperty NazivDvoranaProperty() {
        return NazivDvorana;
    }
}