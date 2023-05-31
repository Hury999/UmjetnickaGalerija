package com.example.galerija;

public class Umjetnik {
    private int idUmjetnik;
    private String ime;
    private String prezime;
    private String biografija;

    public Umjetnik(int idUmjetnik, String ime, String prezime, String biografija) {
        this.idUmjetnik = idUmjetnik;
        this.ime = ime;
        this.prezime = prezime;
        this.biografija = biografija;
    }

    public int getIdUmjetnik() {
        return idUmjetnik;
    }

    public void setIdUmjetnik(int idUmjetnik) {
        this.idUmjetnik = idUmjetnik;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getBiografija() {
        return biografija;
    }

    public void setBiografija(String biografija) {
        this.biografija = biografija;
    }
}
