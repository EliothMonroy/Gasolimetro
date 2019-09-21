package com.ipn.mx.gasolimetro.datos.modelos;

import com.google.gson.annotations.SerializedName;

public class AgregarSensor {

    @SerializedName("txDevice")
    private String txDevice;
    @SerializedName("idUsuario")
    private Long idUsuario;
    @SerializedName("estadoSensor")
    private EstadoSensor estadoSensor;

    /**
     * No args constructor for use in serialization
     *
     */
    public AgregarSensor() {
    }

    /**
     *
     * @param txDevice
     * @param idUsuario
     * @param estadoSensor
     */
    public AgregarSensor(String txDevice, Long idUsuario, EstadoSensor estadoSensor) {
        super();
        this.txDevice = txDevice;
        this.idUsuario = idUsuario;
        this.estadoSensor = estadoSensor;
    }

    public String getTxDevice() {
        return txDevice;
    }

    public void setTxDevice(String txDevice) {
        this.txDevice = txDevice;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public EstadoSensor getEstadoSensor() {
        return estadoSensor;
    }

    public void setEstadoSensor(EstadoSensor estadoSensor) {
        this.estadoSensor = estadoSensor;
    }

}
