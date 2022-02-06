package com.weatherdata;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@PropertySource("classpath:application.properties")
public class EventCreator {
    private static final Logger log = LoggerFactory.getLogger(EventCreator.class);

    private final CityRepository cityRepository;

    public EventCreator(final CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Value( "${api-key}" )
    private String apiKey;

    @Scheduled(fixedRate = 15, timeUnit = TimeUnit.MINUTES)
    public void importData() throws ParseException {
        List<City> cities = cityRepository.findAll();
        RestTemplate template = new RestTemplate();
        if (cities.isEmpty()) {
            log.info("No cities in list to import data to!");
        }
        else {
            log.info("Importing data...");
            for (City city : cities) {
                String uri = "https://api.openweathermap.org/data/2.5/weather?id=" + city.getId() + "&appid=" + apiKey;
                String rawJson = template.getForObject(uri, String.class);
                if (rawJson != null) {
                    JSONObject weatherdataJson = (JSONObject) new JSONParser().parse(rawJson);
                    JSONObject main = ((JSONObject) weatherdataJson.get("main"));
                    double temp = Double.parseDouble(main.get("temp").toString());
                    long humidity = (long) main.get("humidity");
                    double windSpeed = Double.parseDouble(((JSONObject) weatherdataJson.get("wind")).get("speed").toString());
                    long unixTime = (long) weatherdataJson.get("dt");
                    Weatherdata data = new Weatherdata(temp, humidity, windSpeed, unixTime);
                    city.addToWeatherdata(data);
                    cityRepository.save(city);
                }
            }
            log.info("Import done!");
        }
    }
}
