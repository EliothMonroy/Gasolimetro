
package com.example;


public class Sensor {

    private Long idSensor;
    private String txDevice;
    private EstadoSensor estadoSensor;
    private Long idUsuario;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Sensor() {
    }

    /**
     * 
     * @param txDevice
     * @param idUsuario
     * @param idSensor
     * @param estadoSensor
     */
    public Sensor(Long idSensor, String txDevice, EstadoSensor estadoSensor, Long idUsuario) {
        super();
        this.idSensor = idSensor;
        this.txDevice = txDevice;
        this.estadoSensor = estadoSensor;
        this.idUsuario = idUsuario;
    }

    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
    }

    public String getTxDevice() {
        return txDevice;
    }

    public void setTxDevice(String txDevice) {
        this.txDevice = txDevice;
    }

    public EstadoSensor getEstadoSensor() {
        return estadoSensor;
    }

    public void setEstadoSensor(EstadoSensor estadoSensor) {
        this.estadoSensor = estadoSensor;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

}
