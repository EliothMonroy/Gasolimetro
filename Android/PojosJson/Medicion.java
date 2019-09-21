
package com.example;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Medicion {

    private String finMedicion;
    private Integer litrosSolicitados;
    private Integer litrosIngresados;
    private Integer precioSolicitado;
    private Float precioPagado;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Medicion() {
    }

    /**
     * 
     * @param litrosSolicitados
     * @param precioSolicitado
     * @param precioPagado
     * @param finMedicion
     * @param litrosIngresados
     */
    public Medicion(String finMedicion, Integer litrosSolicitados, Integer litrosIngresados, Integer precioSolicitado, Float precioPagado) {
        super();
        this.finMedicion = finMedicion;
        this.litrosSolicitados = litrosSolicitados;
        this.litrosIngresados = litrosIngresados;
        this.precioSolicitado = precioSolicitado;
        this.precioPagado = precioPagado;
    }

    public String getFinMedicion() {
        return finMedicion;
    }

    public void setFinMedicion(String finMedicion) {
        this.finMedicion = finMedicion;
    }

    public Integer getLitrosSolicitados() {
        return litrosSolicitados;
    }

    public void setLitrosSolicitados(Integer litrosSolicitados) {
        this.litrosSolicitados = litrosSolicitados;
    }

    public Integer getLitrosIngresados() {
        return litrosIngresados;
    }

    public void setLitrosIngresados(Integer litrosIngresados) {
        this.litrosIngresados = litrosIngresados;
    }

    public Integer getPrecioSolicitado() {
        return precioSolicitado;
    }

    public void setPrecioSolicitado(Integer precioSolicitado) {
        this.precioSolicitado = precioSolicitado;
    }

    public Float getPrecioPagado() {
        return precioPagado;
    }

    public void setPrecioPagado(Float precioPagado) {
        this.precioPagado = precioPagado;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("finMedicion", finMedicion).append("litrosSolicitados", litrosSolicitados).append("litrosIngresados", litrosIngresados).append("precioSolicitado", precioSolicitado).append("precioPagado", precioPagado).toString();
    }

}
