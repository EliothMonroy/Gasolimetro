package com.gasolimetro.gasolineraservice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
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
	
	@Column(name="activo")
	private Integer activo;
	
	@OneToMany(mappedBy = "automovilSensor")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Medicion> mediciones;

	public AutomovilSensor() {
		super();
	}

	public AutomovilSensor(AutomovilSensorPk id, Integer activo, List<Medicion> mediciones) {
		super();
		this.id = id;
		this.activo = activo;
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
	
	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "AutomovilSensor [id=" + id + ", activo=" + activo + ", mediciones=" + mediciones + "]";
	}
	
}
