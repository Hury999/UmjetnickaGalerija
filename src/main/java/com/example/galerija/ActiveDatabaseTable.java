package com.example.galerija;

public enum ActiveDatabaseTable {
    Djelo("Djelo"),
    Dvorana("Dvorana"),
    Izlozba("Izlozba"),
    Lokacija("Lokacija"),
    Pravac("Pravac"),
    Tehnika("Tehnika"),
    Umjetnik("Umjetnik");

    private String description;

    private ActiveDatabaseTable(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}