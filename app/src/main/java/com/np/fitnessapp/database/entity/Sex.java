package com.np.fitnessapp.database.entity;

public enum Sex {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String value;

    private Sex(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
