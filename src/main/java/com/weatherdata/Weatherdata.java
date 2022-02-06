package com.weatherdata;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Weatherdata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double temp;

    private long humidity;

    private double windSpeed;

    private long unixTime;

    public Weatherdata() {}

    public Weatherdata(double temp, long humidity, double windSpeed, long unixTime) {
        this.temp = temp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.unixTime = unixTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public long getHumidity() {
        return humidity;
    }

    public void setHumidity(long humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public long getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(long unixTime) {
        this.unixTime = unixTime;
    }
}
