package com.np.fitnessapp.database.entity;

public enum Sex {
    MALE("male"),
    FEMALE("female");

    private final String value;

    private Sex(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
