
package com.ipn.mx.gasolimetro.datos.modelos;

import java.util.List;

public class InfoGasolineraServer {

    private GasolineraServer gasolinera;
    private Long numeroMediciones;
    private List<InsigniasGasolinera> insigniasGasolinera = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public InfoGasolineraServer() {
    }

    /**
     * 
     * @param numeroMediciones
     * @param gasolinera
     * @param insigniasGasolinera
     */
    public InfoGasolineraServer(GasolineraServer gasolinera, Long numeroMediciones, List<InsigniasGasolinera> insigniasGasolinera) {
        super();
        this.gasolinera = gasolinera;
        this.numeroMediciones = numeroMediciones;
        this.insigniasGasolinera = insigniasGasolinera;
    }

    public GasolineraServer getGasolinera() {
        return gasolinera;
    }

    public void setGasolinera(GasolineraServer gasolinera) {
        this.gasolinera = gasolinera;
    }

    public Long getNumeroMediciones() {
        return numeroMediciones;
    }

    public void setNumeroMediciones(Long numeroMediciones) {
        this.numeroMediciones = numeroMediciones;
    }

    public List<InsigniasGasolinera> getInsigniasGasolinera() {
        return insigniasGasolinera;
    }

    public void setInsigniasGasolinera(List<InsigniasGasolinera> insigniasGasolinera) {
        this.insigniasGasolinera = insigniasGasolinera;
    }

}
