package sg.edu.nus.iss.day17workshop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.iss.day17workshop.model.Weather;

@Service
public class WeatherService {
    
    @Value("${workshop17.openweather.api.url}")
    private String openWeatherApiURL;

    @Value("${workshop17.openweather.api.key}")
    private String openWeatherApiKey;

    public Optional<Weather> getWeather(String city, String unitMeasurement) throws Exception {
        String weatherURL = UriComponentsBuilder.fromUriString(openWeatherApiURL)
            .queryParam("q", city.replaceAll(" ", "+"))
            .queryParam("units", unitMeasurement)
            .queryParam("appId", openWeatherApiKey)
            .toUriString();

            System.out.println("Weather URL > " + weatherURL);

            RestTemplate template = new RestTemplate();
            ResponseEntity<String> res = template.getForEntity(weatherURL, String.class);

            Weather w = Weather.create(res.getBody());
            if (w != null) {
                return Optional.of(w);
            }

            return Optional.empty();
    }
}
