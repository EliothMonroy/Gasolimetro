package com.gasolimetro.medicionservice.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable
public class AutomovilSensorPk implements Serializable{
	
	private static final long serialVersionUID = 7692683078728875264L;
	
	@JsonIgnore
	@ManyToOne
	@LazyCollection(LazyCollectionOption.TRUE)
    @JoinColumn(name = "id_automovil",referencedColumnName="id_automovil")
    private Automovil automovil;

	@JsonIgnore
    @ManyToOne
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinColumn(name = "id_sensor", referencedColumnName="id_sensor")
    private Sensor sensor;

	public AutomovilSensorPk() {
		super();
	}

	public AutomovilSensorPk(Automovil automovil, Sensor sensor) {
		super();
		this.automovil = automovil;
		this.sensor = sensor;
	}

	@JsonIgnore
	public Automovil getAutomovil() {
		return automovil;
	}

	public void setAutomovil(Automovil automovil) {
		this.automovil = automovil;
	}

	@JsonIgnore
	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	@Override
	public String toString() {
		return "AutomovilSensorPk [automovil=" + automovil + ", sensor=" + sensor + "]";
	}
    
}
