package com.gasolimetro.medicionservice.api_models;

import com.gasolimetro.medicionservice.entity.EstadoSensor;
import com.gasolimetro.medicionservice.entity.Sensor;
import com.gasolimetro.medicionservice.entity.Usuario;

public class SensorModel {
	private Long idSensor;
	private String txDevice;
	private EstadoSensor estadoSensor;
	private Long idUsuario;
	
	public SensorModel() {
		super();
	}

	public SensorModel(Long idSensor, String txDevice, EstadoSensor estadoSensor, Long idUsuario) {
		super();
		this.idSensor = idSensor;
		this.txDevice = txDevice;
		this.estadoSensor = estadoSensor;
		this.idUsuario = idUsuario;
	}

	public Sensor createSensor() {
		Sensor sensor = new Sensor();
		sensor.setTxDevice(this.txDevice);
		sensor.setEstadoSensor(this.estadoSensor);
		
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(this.idUsuario);
		sensor.setUsuario(usuario);
		return sensor;
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

	@Override
	public String toString() {
		return "SensorModel [idSensor=" + idSensor + ", txDevice=" + txDevice + ", estadoSensor=" + estadoSensor
				+ ", idUsuario=" + idUsuario + "]";
	}
	
}
