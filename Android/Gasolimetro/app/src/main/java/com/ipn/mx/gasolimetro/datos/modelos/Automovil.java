package com.ipn.mx.gasolimetro.datos.modelos;

import com.google.gson.annotations.SerializedName;

public class Automovil {
    @SerializedName("idAutomovil")
    private Integer idAutomovil;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("marca")
    private String marca;
    @SerializedName("modelo")
    private String modelo;
    @SerializedName("capacidadLitros")
    private Double capacidadLitros;

    public Automovil() {
    }

    public Automovil(Integer idAutomovil, String nombre, String marca, String modelo, Double capacidadLitros) {
        this.idAutomovil = idAutomovil;
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.capacidadLitros = capacidadLitros;
    }

    public Integer getIdAutomovil() {
        return idAutomovil;
    }

    public void setIdAutomovil(Integer idAutomovil) {
        this.idAutomovil = idAutomovil;
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

    @Override
    public String toString() {
        return  nombre  +
                ", " + marca +
                ", " + modelo;
    }
}
