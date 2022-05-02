package com.weatherdata;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
public class City {

    @Id
    @Min(value = 1, message = "ID should be bigger than 1")
    @NotNull(message = "ID is mandatory")
    private Long id;

    @NotEmpty(message = "Name is mandatory")
    private String name;

    /** Country code (for example EE) */
    @NotEmpty(message = "Country is mandatory")
    private String country;

    @OneToMany(targetEntity= WeatherData.class, cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    private List<WeatherData> weatherData = new ArrayList<>();

    public City(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    /** Adds weather data to a city object. */
    public void addWeatherData(WeatherData data) {
        this.weatherData.add(data);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof City city))
            return false;
        return Objects.equals(this.id, city.id) && Objects.equals(this.name, city.name)
                && Objects.equals(this.country, city.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.country);
    }

    /** City info in string format. Does not include weather data. */
    @Override
    public String toString() {
        return "City{" + "id=" + this.id + ", name='" + this.name + '\'' + ", country code='" + this.country + '\'' + '}';
    }
}
