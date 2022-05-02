package com.weatherdata;

import org.springframework.data.jpa.repository.JpaRepository;

interface WeatherDataRepository extends JpaRepository<City, Long> {

}
