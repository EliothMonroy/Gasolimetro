
package com.ipn.mx.gasolimetro.datos.modelos;


public class InsigniasGasolinera {

    private Long idInsigniaGasolinera;
    private String descripcion;
    private String url;
    private String criterio;

    /**
     * No args constructor for use in serialization
     * 
     */
    public InsigniasGasolinera() {
    }

    /**
     * 
     * @param criterio
     * @param descripcion
     * @param idInsigniaGasolinera
     * @param url
     */
    public InsigniasGasolinera(Long idInsigniaGasolinera, String descripcion, String url, String criterio) {
        super();
        this.idInsigniaGasolinera = idInsigniaGasolinera;
        this.descripcion = descripcion;
        this.url = url;
        this.criterio = criterio;
    }

    public Long getIdInsigniaGasolinera() {
        return idInsigniaGasolinera;
    }

    public void setIdInsigniaGasolinera(Long idInsigniaGasolinera) {
        this.idInsigniaGasolinera = idInsigniaGasolinera;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

}
