package com.example.b7sport;


import java.util.Map;


public class Arena {

    private int id;
    private String name;
    private String type;
    private String street;
    private Double housenumber;
    private String neighbor;
    private String activity;
    private Map<String,Object> participants;
    private String lighing;
    private String sport_type;
    private double lon;
    private double lat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
/*
    private String lighing;
    private String sport_type;
    private double lon;
    private double lat;
*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public double getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(double housenumber) {
        this.housenumber = housenumber;
    }

    public String getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(String neighbor) {
        this.neighbor = neighbor;
    }

    public String getLighing() {
        return lighing;
    }

    public void setLighing(String lighing) {
        this.lighing = lighing;
    }

    public String getSport_type() {
        return sport_type;
    }

    public void setSport_type(String sport_type) {
        this.sport_type = sport_type;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Arena(String name, String type, String street, double housenumber, String neighbor, String lighing, String sport_type, float lon, float lat) {
        this.name = name;
        this.type = type;
        this.street = street;
        this.housenumber = housenumber;
        this.neighbor = neighbor;
        this.lighing = lighing;
        this.sport_type = sport_type;
        this.lon = lon;
        this.lat = lat;
    }
    public Arena(int id)
    {
        this.id =id;
    }
}
