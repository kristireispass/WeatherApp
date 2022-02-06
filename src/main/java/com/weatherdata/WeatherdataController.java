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
public class WeatherdataController {

    private final CityDao dao;

    WeatherdataController(CityDao dao) {
        this.dao = dao;
    }

    // Get weather data for all cities
    @GetMapping("/weatherdata/")
    List<City> getAllData() { return dao.getAllData(); }

    // Get weather data for a specific city
    @GetMapping("/weatherdata/{cityId}")
    List<Weatherdata> getDataForCity(@PathVariable Long cityId) { return dao.getDataForCity(cityId); }

    // Add a city to the list
    @PostMapping("/weatherdata/add-city")
    ResponseEntity<String> addCity(@Valid @RequestBody City newCity) {
        if (dao.addCity(newCity)) {
            return ResponseEntity.ok("City added to list!");
        }
        return ResponseEntity.ok("City already in list!");
    }

    // Delete city from list
    @DeleteMapping("/weatherdata/{cityId}")
    ResponseEntity<String> deleteCity(@PathVariable Long cityId) {
        dao.deleteCity(cityId);
        return ResponseEntity.ok("City removed from list!");
    }

    // Get all the id-s and names of cities in our list
    @GetMapping("/weatherdata/cities")
    HashMap<Long, String> allCityNames() { return dao.allCityNames(); }

    // Get all the info about a specific city
    @GetMapping("/weatherdata/cities/{cityId}")
    City getCityInfo(@PathVariable Long cityId) { return dao.getCityInfo(cityId);}

    // Exception handling for City object validation errors
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
