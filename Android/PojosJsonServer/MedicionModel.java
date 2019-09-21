package com.gasolimetro.medicionservice.api_models;

import com.gasolimetro.medicionservice.entity.Medicion;

public class MedicionModel {
	private Long idGasolinera;
	private Long idSensor;
	private Long idAutomovil;
	private Medicion medicion;

	public MedicionModel() {
		super();
	}

	public MedicionModel(Long idGasolinera, Long idSensor, Long idAutomovil, Medicion medicion) {
		super();
		this.idGasolinera = idGasolinera;
		this.idSensor = idSensor;
		this.idAutomovil = idAutomovil;
		this.medicion = medicion;
	}

	public Long getIdGasolinera() {
		return idGasolinera;
	}

	public void setIdGasolinera(Long idGasolinera) {
		this.idGasolinera = idGasolinera;
	}

	public Long getIdSensor() {
		return idSensor;
	}

	public void setIdSensor(Long idSensor) {
		this.idSensor = idSensor;
	}

	public Long getIdAutomovil() {
		return idAutomovil;
	}

	public void setIdAutomovil(Long idAutomovil) {
		this.idAutomovil = idAutomovil;
	}

	public Medicion getMedicion() {
		return medicion;
	}

	public void setMedicion(Medicion medicion) {
		this.medicion = medicion;
	}

	@Override
	public String toString() {
		return "MedicionModel [idGasolinera=" + idGasolinera + ", idSensor=" + idSensor + ", idAutomovil=" + idAutomovil
				+ ", medicion=" + medicion + "]";
	}
	
}
