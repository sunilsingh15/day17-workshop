package sg.edu.nus.iss.day17workshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.day17workshop.model.Weather;
import sg.edu.nus.iss.day17workshop.service.WeatherService;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService service;

    @GetMapping(path = "/")
    public String homePage(Model model) {
        return "index";
    }

    @GetMapping(path = "/weather")
    public String getWeather(@RequestParam(required = true) String city,
            @RequestParam(defaultValue = "metric") String units, Model model) throws Exception {

        Optional<Weather> w = service.getWeather(city, units);
        model.addAttribute("weather", w.get());

        return "weather";
    }

}
