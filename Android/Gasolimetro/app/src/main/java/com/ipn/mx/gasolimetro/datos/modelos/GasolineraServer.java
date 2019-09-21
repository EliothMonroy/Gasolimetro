
package com.ipn.mx.gasolimetro.datos.modelos;


public class GasolineraServer {

    private Long idGasolinera;
    private String txGasolinera;
    private Float calificacion;
    private Ubicacion ubicacion;
    private Object puerto;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GasolineraServer() {
    }

    /**
     * 
     * @param idGasolinera
     * @param calificacion
     * @param puerto
     * @param txGasolinera
     * @param ubicacion
     */
    public GasolineraServer(Long idGasolinera, String txGasolinera, Float calificacion, Ubicacion ubicacion, Object puerto) {
        super();
        this.idGasolinera = idGasolinera;
        this.txGasolinera = txGasolinera;
        this.calificacion = calificacion;
        this.ubicacion = ubicacion;
        this.puerto = puerto;
    }

    public Long getIdGasolinera() {
        return idGasolinera;
    }

    public void setIdGasolinera(Long idGasolinera) {
        this.idGasolinera = idGasolinera;
    }

    public String getTxGasolinera() {
        return txGasolinera;
    }

    public void setTxGasolinera(String txGasolinera) {
        this.txGasolinera = txGasolinera;
    }

    public Float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Float calificacion) {
        this.calificacion = calificacion;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Object getPuerto() {
        return puerto;
    }

    public void setPuerto(Object puerto) {
        this.puerto = puerto;
    }

}
