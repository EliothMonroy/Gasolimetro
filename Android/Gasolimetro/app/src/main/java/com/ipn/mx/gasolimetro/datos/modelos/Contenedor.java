package com.ipn.mx.gasolimetro.datos.modelos;

import java.util.List;

public class Contenedor {
    private List<SensorAutomovil> objeto;

    public Contenedor() {
    }

    public Contenedor(List<SensorAutomovil> objeto) {
        this.objeto = objeto;
    }

    public List<SensorAutomovil> getObjeto() {
        return objeto;
    }

    public void setObjeto(List<SensorAutomovil> objeto) {
        this.objeto = objeto;
    }
}
