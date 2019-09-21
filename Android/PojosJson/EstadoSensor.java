
package com.example;


public class EstadoSensor {

    private Long idEstado;
    private String descripcion;
    private String codigo;

    /**
     * No args constructor for use in serialization
     * 
     */
    public EstadoSensor() {
    }

    /**
     * 
     * @param codigo
     * @param descripcion
     * @param idEstado
     */
    public EstadoSensor(Long idEstado, String descripcion, String codigo) {
        super();
        this.idEstado = idEstado;
        this.descripcion = descripcion;
        this.codigo = codigo;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
