package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;



@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryModel {
    private  String name;
    private String capital;
    private List<String> borders;

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public List<String> getBorders() {
        return borders;
    }





}
