package com.weatherdata;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/** Data access object for cities and weather data. */
@Repository
public class WeatherDataDao {

    private final WeatherDataRepository weatherDataRepository;

    WeatherDataDao(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    /** Get a list of all cities and their accumulated weather data. */
    List<City> getWeatherDataFromAllCities() {
        return weatherDataRepository.findAll();
    }

    /** Get a list of weather data for a specific city.
     * @param cityId - ID number of the city to get weather data from.
     */
    List<WeatherData> getWeatherDataForCity(Long cityId) {
        City city = weatherDataRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        return city.getWeatherData();
    }

    /** Get a hashmap of all city IDs and their names in the repository. */
    HashMap<Long, String> getAllCityNames() {
        HashMap<Long, String> cityNames = new HashMap<>();
        for (City city : weatherDataRepository.findAll()) {
            cityNames.put(city.getId(), city.getName());
        }
        return cityNames;
    }

    /** Get info about the City object in the repository. Does not include weather data.
     * @param cityId - ID number of the city to get info about. */
    City getCityInfo(Long cityId) {
        return weatherDataRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
    }

    /** Add city to repository to start collecting weather data.
     * @param newCity - city object to be added.
     * */
    boolean addCity(City newCity) {
        for (City city : weatherDataRepository.findAll()) {
            if (Objects.equals(newCity.getId(), city.getId())) {
                return false;
            }
        }
        weatherDataRepository.save(newCity);
        return true;
    }

    /** Remove city and the linked weather data from repository.
     * @param cityId - ID number of the city to remove.
     * */
    void deleteCity(Long cityId) {
        weatherDataRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        weatherDataRepository.deleteById(cityId);
    }
}
