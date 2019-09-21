package com.ipn.mx.gasolimetro.datos.modelos;

public class Gasolinera {
    private String place_id;
    private Double lat;
    private Double lon;

    public Gasolinera(String place_id, Double lat, Double lon) {
        this.place_id = place_id;
        this.lat = lat;
        this.lon = lon;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
