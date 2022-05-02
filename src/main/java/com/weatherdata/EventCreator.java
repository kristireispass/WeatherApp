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

    private final WeatherDataRepository weatherDataRepository;

    public EventCreator(final WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    @Value( "${api-key}" )
    private String apiKey;

    /** Imports weather data every 15 minutes for every city in the repository.
     * Logs info about importing.
     * "&units=metric" in the end of the uri gets temperature in Celsius, default is in Kelvins.
     */
    @Scheduled(fixedRate = 15, timeUnit = TimeUnit.MINUTES)
    public void importData() throws ParseException {
        List<City> cities = weatherDataRepository.findAll();
        RestTemplate template = new RestTemplate();
        if (cities.isEmpty()) {
            log.info("No cities in list to import data to!");
        }
        else {
            log.info("Importing data...");
            for (City city : cities) {
                String uri = "https://api.openweathermap.org/data/2.5/weather?id=" + city.getId() + "&appid=" + apiKey +
                        "&units=metric";
                String rawJson = template.getForObject(uri, String.class);
                if (rawJson != null) {
                    JSONObject weatherDataJson = (JSONObject) new JSONParser().parse(rawJson);
                    JSONObject main = ((JSONObject) weatherDataJson.get("main"));
                    double temp = Double.parseDouble(main.get("temp").toString());
                    int humidity = Long.valueOf((long) main.get("humidity")).intValue();
                    double windSpeed = Double.parseDouble(((JSONObject) weatherDataJson.get("wind")).get("speed").toString());
                    long unixTime = (long) weatherDataJson.get("dt");
                    WeatherData data = new WeatherData(temp, humidity, windSpeed, unixTime);
                    city.addWeatherData(data);
                    weatherDataRepository.save(city);
                }
            }
            log.info("Import done!");
        }
    }
}
