package com.weatherdata;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/** Base class for holding weather data. */
@Data
@Entity
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double temp;

    private int humidity;

    private double windSpeed;

    private long unixTime;

    public WeatherData() {}

    /** Constructor for WeatherData objects.
     * @param temp - Temperature in Celsius.
     * @param humidity - Humidity %.
     * @param windSpeed - Wind speed in m/s.
     * @param unixTime - Time of data download in unix time.
     */
    public WeatherData(double temp, int humidity, double windSpeed, long unixTime) {
        this.temp = temp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.unixTime = unixTime;
    }
}
