# Weather Data API

### Info

Simple weather data application that imports data every 15 min for the cities in the list.
Two cities are already preloaded.

#### API key

To create your API key, create a user at https://openweathermap.org/ and you can find it under https://home.openweathermap.org/api_keys .

Then replace "INSERT_API_KEY_HERE" with your own API key in the application.properties file in src/main/resources

#### Swagger UI

Swagger is located at http://localhost:8080/swagger-ui/

#### Sample for adding a new city:
{
"id": 588409,
"name": "Tallinn",
"country": "EE"
}

Full list of cities here (city.list.json.gz): http://bulk.openweathermap.org/sample/
