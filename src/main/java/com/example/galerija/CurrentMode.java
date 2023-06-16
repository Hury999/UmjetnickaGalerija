package com.example.galerija;

public class CurrentMode {

    private static CurrentMode instance;

    public enum CurrentModeEnum  {Djelo,Dvorana,Izlozba,Lokacija,Pravac,Tehnika,Umjetnik};

    public CurrentModeEnum CurrentMode;

    public CurrentMode() {
        // Initialization code, if needed
    }

    // Static method to access the singleton instance
    public static synchronized CurrentMode getInstance() {
        if (instance == null) {
            instance = new CurrentMode();
        }
        return instance;
    }

    // Other methods and properties of the class

}
