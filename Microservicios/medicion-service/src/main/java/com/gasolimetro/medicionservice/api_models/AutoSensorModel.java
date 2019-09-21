package com.gasolimetro.medicionservice.api_models;

import com.gasolimetro.medicionservice.entity.Automovil;

public class AutoSensorModel {
	
	private Automovil automovil;
	private Long idSensor;
	
	public AutoSensorModel() {
		super();
	}

	public AutoSensorModel(Automovil automovil, Long idSensor) {
		super();
		this.automovil = automovil;
		this.idSensor = idSensor;
	}

	public Automovil getAutomovil() {
		return automovil;
	}

	public void setAutomovil(Automovil automovil) {
		this.automovil = automovil;
	}

	public Long getIdSensor() {
		return idSensor;
	}

	public void setIdSensor(Long idSensor) {
		this.idSensor = idSensor;
	}

	@Override
	public String toString() {
		return "AutoSensorModel [automovil=" + automovil + ", idSensor=" + idSensor + "]";
	}
	

}
