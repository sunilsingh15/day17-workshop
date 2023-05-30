package sg.edu.nus.iss.day17workshop.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Weather implements Serializable {

    private String city;
    private String temperature;
    private List<Conditions> conditions = new LinkedList<>();
    private Long weatherTimestamp;
    private Long sunsetTimestamp;
    private Long sunriseTimestamp;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public List<Conditions> getConditions() {
        return conditions;
    }

    public void setConditions(List<Conditions> conditions) {
        this.conditions = conditions;
    }

    public Long getWeatherTimestamp() {
        return weatherTimestamp;
    }

    public void setWeatherTimestamp(Long weatherTimestamp) {
        this.weatherTimestamp = weatherTimestamp;
    }

    public Long getSunsetTimestamp() {
        return sunsetTimestamp;
    }

    public void setSunsetTimestamp(Long sunsetTimestamp) {
        this.sunsetTimestamp = sunsetTimestamp;
    }

    public Long getSunriseTimestamp() {
        return sunriseTimestamp;
    }

    public void setSunriseTimestamp(Long sunriseTimestamp) {
        this.sunriseTimestamp = sunriseTimestamp;
    }

    public static Weather create(String json) throws Exception {
        Weather w = new Weather();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            w.setCity(o.getString("name"));
            JsonNumber jDt = o.getJsonNumber("dt");
            w.setWeatherTimestamp(jDt.longValue());
            JsonObject mainObj = o.getJsonObject("main");
            w.setTemperature(mainObj.getJsonNumber("temp").toString());
            JsonObject sysObj = o.getJsonObject("sys");
            w.setSunriseTimestamp(sysObj.getJsonNumber("sunrise").longValue());
            w.setSunsetTimestamp(sysObj.getJsonNumber("sunset").longValue());

            w.conditions = o.getJsonArray("weather").stream()
                    .map(v -> (JsonObject) v)
                    .map(v -> Conditions.createJson(v))
                    .toList();
        }

        return w;
    }

}
