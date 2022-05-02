package com.weatherdata;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WeatherDataController {

    private final WeatherDataDao dao;

    WeatherDataController(WeatherDataDao dao) {
        this.dao = dao;
    }

    /** Get a list of all cities and their weather data. */
    @GetMapping("/weather-data/")
    List<City> getAllWeatherData() { return dao.getWeatherDataFromAllCities(); }

    /** Get a list of weather data for a specific city.
     * @param cityId - ID number of the city requested. */
    @GetMapping("/weather-data/{cityId}")
    List<WeatherData> getWeatherDataForCity(@PathVariable Long cityId) { return dao.getWeatherDataForCity(cityId); }

    /** Get all the id-s and names of cities in the list. */
    @GetMapping("/weather-data/cities")
    HashMap<Long, String> getAllCityNames() { return dao.getAllCityNames(); }

    /** Get the info about a specific city including weather data.
     * @param cityId - ID number of the city requested. */
    @GetMapping("/weather-data/cities/{cityId}")
    City getCityInfo(@PathVariable Long cityId) { return dao.getCityInfo(cityId);}

    /** Add a new city to the list of cities to get weather data for.
     * @param newCity - City object to be added. Should be posted in JSON format.
     * Example:
     * {
     * "id": 588409,
     * "name": "Tallinn",
     * "country": "EE"
     * }
     */
    @PostMapping("/weather-data/add-city")
    ResponseEntity<String> addCity(@Valid @RequestBody City newCity) {
        if (dao.addCity(newCity)) {
            return ResponseEntity.ok("City added to list!");
        }
        return ResponseEntity.ok("City already in list!");
    }

    /** Delete city from list.
     * @param cityId - ID number of the city to be removed along with its weather data. */
    @DeleteMapping("/weather-data/{cityId}")
    ResponseEntity<String> deleteCity(@PathVariable Long cityId) {
        dao.deleteCity(cityId);
        return ResponseEntity.ok("City removed from list!");
    }

    /** Exception handling for City object validation errors. */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
