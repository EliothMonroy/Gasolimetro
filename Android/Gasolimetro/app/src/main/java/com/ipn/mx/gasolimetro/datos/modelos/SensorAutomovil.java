package com.ipn.mx.gasolimetro.datos.modelos;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class SensorAutomovil {
    @SerializedName("automovil")
    private Automovil automovil;
    @SerializedName("idSensor")
    private Long idSensor;

    public SensorAutomovil(Automovil automovil, Long idSensor) {
        this.automovil = automovil;
        this.idSensor = idSensor;
    }

    public SensorAutomovil() {
    }

    public Automovil getAutomovil() {
        return automovil;
    }

    public void setAutomovil(Automovil automovil) {
        this.automovil = automovil;
    }

    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
    }
}
