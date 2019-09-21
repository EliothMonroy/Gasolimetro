
package com.example;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MedicionFeed {

    private String idGasolinera;
    private String idSensor;
    private Medicion medicion;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MedicionFeed() {
    }

    /**
     * 
     * @param medicion
     * @param idGasolinera
     * @param idSensor
     */
    public MedicionFeed(String idGasolinera, String idSensor, Medicion medicion) {
        super();
        this.idGasolinera = idGasolinera;
        this.idSensor = idSensor;
        this.medicion = medicion;
    }

    public String getIdGasolinera() {
        return idGasolinera;
    }

    public void setIdGasolinera(String idGasolinera) {
        this.idGasolinera = idGasolinera;
    }

    public String getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(String idSensor) {
        this.idSensor = idSensor;
    }

    public Medicion getMedicion() {
        return medicion;
    }

    public void setMedicion(Medicion medicion) {
        this.medicion = medicion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("idGasolinera", idGasolinera).append("idSensor", idSensor).append("medicion", medicion).toString();
    }

}
