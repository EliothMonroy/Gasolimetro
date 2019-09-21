package com.ipn.gasolimetro.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="tus09_automovil_sensor")
public class AutomovilSensor implements Serializable{

	private static final long serialVersionUID = -7753087294091779923L;

	@EmbeddedId
	AutomovilSensorPk id;
	
	@OneToMany(mappedBy = "automovilSensor")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Medicion> mediciones;

	public AutomovilSensor() {
		super();
	}

	public AutomovilSensor(AutomovilSensorPk id, List<Medicion> mediciones) {
		super();
		this.id = id;
		this.mediciones = mediciones;
	}

	public AutomovilSensorPk getId() {
		return id;
	}

	public void setId(AutomovilSensorPk id) {
		this.id = id;
	}

	public List<Medicion> getMediciones() {
		return mediciones;
	}

	public void setMediciones(List<Medicion> mediciones) {
		this.mediciones = mediciones;
	}

	@Override
	public String toString() {
		return "AutomovilSensor [id=" + id + ", mediciones=" + mediciones + "]";
	}
    
}
