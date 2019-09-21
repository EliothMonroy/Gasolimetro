package com.gasolimetro.medicionservice.api_models;

import java.util.List;

import com.gasolimetro.medicionservice.entity.Medicion;
import com.gasolimetro.medicionservice.entity.Sensor;


public class SensorInfoModel {
	
	private Sensor sensor;
	private List<Medicion> mediciones;
	
	public SensorInfoModel() {
		super();
	}

	public SensorInfoModel(Sensor sensor, List<Medicion> mediciones) {
		super();
		this.sensor = sensor;
		this.mediciones = mediciones;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public List<Medicion> getMediciones() {
		return mediciones;
	}

	public void setMediciones(List<Medicion> mediciones) {
		this.mediciones = mediciones;
	}

	@Override
	public String toString() {
		return "SensorInfoModel [sensor=" + sensor + ", mediciones=" + mediciones + "]";
	}
	
}
