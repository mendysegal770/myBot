package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherModel {
    private int temp;
    private int feels_like;
    private String description;
    private HashMap main;
    private List<HashMap> weather;

    public List<HashMap> getWeather() {
       return weather;
    }


    public HashMap getMain() {
        return main;
    }




}
