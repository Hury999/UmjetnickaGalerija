package com.example.galerija;
import java.util.Date;

import javafx.beans.property.*;

public class Izlozba {
    private IntegerProperty ID_Izlozba;
    private ObjectProperty<Date> PocetakDatum;
    private ObjectProperty<Date> ZavrsetakDatum;
    private StringProperty Tip;
    private IntegerProperty ID_Dvorana;

    public Izlozba() {
        this.ID_Izlozba = new SimpleIntegerProperty();
        this.PocetakDatum = new SimpleObjectProperty<>(new Date()); // Set default PocetakDatum to current date
        this.ZavrsetakDatum = new SimpleObjectProperty<>(new Date()); // Set default ZavrsetakDatum to current date
        this.Tip = new SimpleStringProperty();
        this.ID_Dvorana = new SimpleIntegerProperty();
    }

    public Izlozba(int ID_Izlozba, Date pocetakDatum, Date zavrsetakDatum,
                   String tip, int IDDvorana) {
        this.ID_Izlozba = new SimpleIntegerProperty(ID_Izlozba);
        this.PocetakDatum = new SimpleObjectProperty<>(pocetakDatum);
        this.ZavrsetakDatum = new SimpleObjectProperty<>(zavrsetakDatum);
        this.Tip = new SimpleStringProperty(tip);
        this.ID_Dvorana = new SimpleIntegerProperty(IDDvorana);
    }

    public int getID_Izlozba() {
        return ID_Izlozba.get();
    }

    public void setID_Izlozba(int ID_Izlozba) {
        this.ID_Izlozba.set(ID_Izlozba);
    }

    public IntegerProperty ID_IzlozbaProperty() {
        return ID_Izlozba;
    }

    public Date getPocetakDatum() {
        return PocetakDatum.get();
    }

    public void setPocetakDatum(Date pocetakDatum) {
        this.PocetakDatum.set(pocetakDatum);
    }

    public ObjectProperty<Date> PocetakDatumProperty() {
        return PocetakDatum;
    }

    public Date getZavrsetakDatum() {
        return ZavrsetakDatum.get();
    }

    public void setZavrsetakDatum(Date zavrsetakDatum) {
        this.ZavrsetakDatum.set(zavrsetakDatum);
    }

    public ObjectProperty<Date> ZavrsetakDatumProperty() {
        return ZavrsetakDatum;
    }

    public String getTip() {
        return Tip.get();
    }

    public void setTip(String tip) {
        this.Tip.set(tip);
    }

    public StringProperty TipProperty() {
        return Tip;
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
}
