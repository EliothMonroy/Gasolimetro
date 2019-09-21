
package com.ipn.mx.gasolimetro.datos.modelos;


import com.google.gson.annotations.SerializedName;

public class MedicionFeed {


    @SerializedName("idGasolinera")
    private Long idGasolinera;
    @SerializedName("idSensor")
    private Long idSensor;
    @SerializedName("idAutomovil")
    private Long idAutomovil;
    @SerializedName("medicion")
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
     * @param idAutomovil
     * @param idSensor
     */
    public MedicionFeed(Long idGasolinera, Long idSensor, Long idAutomovil, Medicion medicion) {
        super();
        this.idGasolinera = idGasolinera;
        this.idSensor = idSensor;
        this.idAutomovil = idAutomovil;
        this.medicion = medicion;
    }

    public Long getIdGasolinera() {
        return idGasolinera;
    }

    public void setIdGasolinera(Long idGasolinera) {
        this.idGasolinera = idGasolinera;
    }

    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
    }

    public Long getIdAutomovil() {
        return idAutomovil;
    }

    public void setIdAutomovil(Long idAutomovil) {
        this.idAutomovil = idAutomovil;
    }

    public Medicion getMedicion() {
        return medicion;
    }

    public void setMedicion(Medicion medicion) {
        this.medicion = medicion;
    }

}
