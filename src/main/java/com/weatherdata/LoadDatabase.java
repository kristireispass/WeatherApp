package com.weatherdata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(CityRepository cityRepository) {

        return args -> {
            log.info("Preloading " + cityRepository.save(new City(588335L, "Tartu", "EE")));
            log.info("Preloading " + cityRepository.save(new City(588348L, "Tapa", "EE")));
        };
    }
}