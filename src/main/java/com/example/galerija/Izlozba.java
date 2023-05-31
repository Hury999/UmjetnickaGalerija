package com.example.galerija;
import java.util.Date;

public class Izlozba {
    private int idIzlozba;
    private Date pocetakDatum;
    private Date zavrsetakDatum;
    private String tip;
    private int idDvorana;

    public Izlozba(int idIzlozba, Date pocetakDatum, Date zavrsetakDatum,
                   String tip, int idDvorana) {
        this.idIzlozba = idIzlozba;
        this.pocetakDatum = pocetakDatum;
        this.zavrsetakDatum = zavrsetakDatum;
        this.tip = tip;
        this.idDvorana = idDvorana;
    }

    public int getIdIzlozba() {
        return idIzlozba;
    }

    public void setIdIzlozba(int idIzlozba) {
        this.idIzlozba = idIzlozba;
    }

    public Date getPocetakDatum() {
        return pocetakDatum;
    }

    public void setPocetakDatum(Date pocetakDatum) {
        this.pocetakDatum = pocetakDatum;
    }

    public Date getZavrsetakDatum() {
        return zavrsetakDatum;
    }

    public void setZavrsetakDatum(Date zavrsetakDatum) {
        this.zavrsetakDatum = zavrsetakDatum;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getIdDvorana() {
        return idDvorana;
    }

    public void setIdDvorana(int idDvorana) {
        this.idDvorana = idDvorana;
    }
}