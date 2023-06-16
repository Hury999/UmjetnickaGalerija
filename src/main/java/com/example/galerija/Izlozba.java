package com.example.galerija;
import java.util.Date;

import javafx.beans.property.*;

import javafx.beans.property.*;

public class Izlozba {
    private final IntegerProperty idIzlozba;
    private final ObjectProperty<Date> pocetakDatum;
    private final ObjectProperty<Date> zavrsetakDatum;
    private final StringProperty tip;
    private final IntegerProperty idDvorana;

    public Izlozba(int idIzlozba, Date pocetakDatum, Date zavrsetakDatum,
                   String tip, int idDvorana) {
        this.idIzlozba = new SimpleIntegerProperty(idIzlozba);
        this.pocetakDatum = new SimpleObjectProperty<>(pocetakDatum);
        this.zavrsetakDatum = new SimpleObjectProperty<>(zavrsetakDatum);
        this.tip = new SimpleStringProperty(tip);
        this.idDvorana = new SimpleIntegerProperty(idDvorana);
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

    public Date getPocetakDatum() {
        return pocetakDatum.get();
    }

    public void setPocetakDatum(Date pocetakDatum) {
        this.pocetakDatum.set(pocetakDatum);
    }

    public ObjectProperty<Date> pocetakDatumProperty() {
        return pocetakDatum;
    }

    public Date getZavrsetakDatum() {
        return zavrsetakDatum.get();
    }

    public void setZavrsetakDatum(Date zavrsetakDatum) {
        this.zavrsetakDatum.set(zavrsetakDatum);
    }

    public ObjectProperty<Date> zavrsetakDatumProperty() {
        return zavrsetakDatum;
    }

    public String getTip() {
        return tip.get();
    }

    public void setTip(String tip) {
        this.tip.set(tip);
    }

    public StringProperty tipProperty() {
        return tip;
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
}
