package com.example.galerija;

public class ActiveDatabaseTableSingleton {
    private static ActiveDatabaseTableSingleton instance;
    private ActiveDatabaseTable activeTable;

    private ActiveDatabaseTableSingleton() {
        // Private constructor to prevent instantiation outside the class
    }

    public static ActiveDatabaseTableSingleton getInstance() {
        if (instance == null) {
            synchronized (ActiveDatabaseTableSingleton.class) {
                if (instance == null) {
                    instance = new ActiveDatabaseTableSingleton();
                }
            }
        }
        return instance;
    }

    public ActiveDatabaseTable getActiveTable() {
        return activeTable;
    }

    public void setActiveTable(ActiveDatabaseTable activeTable) {
        this.activeTable = activeTable;
    }
}