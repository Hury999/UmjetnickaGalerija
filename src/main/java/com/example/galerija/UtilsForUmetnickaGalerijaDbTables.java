package com.example.galerija;

import javafx.collections.ObservableList;

public class UtilsForUmetnickaGalerijaDbTables {

    public static ActiveDatabaseTable getMappedUmjetnickaGalerijaDbTablesToActiveDatabaseTable(Class<?> clazz) {
        return switch (clazz.getSimpleName()) {
            case "Djelo" -> ActiveDatabaseTable.Djelo;
            case "Dvorana" -> ActiveDatabaseTable.Dvorana;
            case "Izlozba" -> ActiveDatabaseTable.Izlozba;
            case "Lokacija" -> ActiveDatabaseTable.Lokacija;
            case "Pravac" -> ActiveDatabaseTable.Pravac;
            case "Tehnika" -> ActiveDatabaseTable.Tehnika;
            case "Umjetnik" -> ActiveDatabaseTable.Umjetnik;
            default -> throw new IllegalArgumentException("Unsupported class: " + clazz);
        };
    }


}
