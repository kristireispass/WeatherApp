package com.weatherdata;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class CityDao {

    private final CityRepository cityRepository;

    CityDao(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    List<City> getAllData() {
        return cityRepository.findAll();
    }

    List<Weatherdata> getDataForCity(Long cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        return city.getWeatherdata();
    }

    boolean addCity(City newCity) {
        for (City city : cityRepository.findAll()) {
            if (Objects.equals(newCity.getId(), city.getId())) {
                return false;
            }
        }
        cityRepository.save(newCity);
        return true;
    }

    void deleteCity(Long cityId) {
        cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        cityRepository.deleteById(cityId);
    }

    HashMap<Long, String> allCityNames() {
        HashMap<Long, String> cityNames = new HashMap<>();
        for (City city : cityRepository.findAll()) {
            cityNames.put(city.getId(), city.getName());
        }
        return cityNames;
    }

    City getCityInfo(Long cityId) {
        return cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
    }
}
