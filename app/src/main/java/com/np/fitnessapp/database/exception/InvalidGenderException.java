package com.np.fitnessapp.database.exception;

public class InvalidGenderException extends Exception {
    public InvalidGenderException() {
        super("Used invalid gender string value");
    }
}
