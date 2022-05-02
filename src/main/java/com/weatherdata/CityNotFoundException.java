package com.weatherdata;

/** Exception class for error handling. */
public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(Long id) {
        super("Could not find city " + id);
    }
}
