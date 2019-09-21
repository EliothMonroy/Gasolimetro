
package com.ipn.mx.gasolimetro.datos.modelos;


public class Ubicacion {

    private Long idUbicacion;
    private String latitud;
    private String longitud;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Ubicacion() {
    }

    /**
     * 
     * @param idUbicacion
     * @param latitud
     * @param longitud
     */
    public Ubicacion(Long idUbicacion, String latitud, String longitud) {
        super();
        this.idUbicacion = idUbicacion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Long getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Long idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

}
