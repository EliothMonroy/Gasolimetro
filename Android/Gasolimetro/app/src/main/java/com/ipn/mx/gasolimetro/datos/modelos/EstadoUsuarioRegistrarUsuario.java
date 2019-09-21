package com.ipn.mx.gasolimetro.datos.modelos;

import com.google.gson.annotations.SerializedName;

public class EstadoUsuarioRegistrarUsuario {

    @SerializedName("idEstado")
    private Long idEstado;

    /**
     * No args constructor for use in serialization
     *
     */
    public EstadoUsuarioRegistrarUsuario() {
    }

    /**
     *
     * @param idEstado
     */
    public EstadoUsuarioRegistrarUsuario(Long idEstado) {
        super();
        this.idEstado = idEstado;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

}
