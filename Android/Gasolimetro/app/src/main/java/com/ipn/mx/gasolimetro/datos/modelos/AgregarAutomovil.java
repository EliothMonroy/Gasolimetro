package com.ipn.mx.gasolimetro.datos.modelos;

import com.google.gson.annotations.SerializedName;

public class AgregarAutomovil {
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("marca")
    private String marca;
    @SerializedName("modelo")
    private String modelo;
    @SerializedName("capacidadLitros")
    private Double capacidadLitros;
    @SerializedName("idUsuario")
    private Long idUsuario;

    /**
     * No args constructor for use in serialization
     *
     */
    public AgregarAutomovil() {
    }

    /**
     *
     * @param nombre
     * @param idUsuario
     * @param capacidadLitros
     * @param marca
     * @param modelo
     */
    public AgregarAutomovil(String nombre, String marca, String modelo, Double capacidadLitros, Long idUsuario) {
        super();
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.capacidadLitros = capacidadLitros;
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getCapacidadLitros() {
        return capacidadLitros;
    }

    public void setCapacidadLitros(Double capacidadLitros) {
        this.capacidadLitros = capacidadLitros;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

}