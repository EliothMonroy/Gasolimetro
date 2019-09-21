package com.ipn.gasolimetro.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tme03_estado_sensor")
public class EstadoSensor implements Serializable{

	private static final long serialVersionUID = -223114860197041068L;

	@Id
	@SequenceGenerator(name = "tme03_estado_sensor_seq", sequenceName = "tme03_estado_sensor_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tme03_estado_sensor_seq")
	@Column(name="id_estado")
	private Long idEstado;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="codigo")
	private Integer codigo;
	
	@OneToMany(mappedBy = "estadoSensor")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Sensor> sensores;

	public EstadoSensor() {
		super();
	}

	public EstadoSensor(Long idEstado, String descripcion, Integer codigo, List<Sensor> sensores) {
		super();
		this.idEstado = idEstado;
		this.descripcion = descripcion;
		this.codigo = codigo;
		this.sensores = sensores;
	}

	public Long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public List<Sensor> getSensores() {
		return sensores;
	}

	public void setSensores(List<Sensor> sensores) {
		this.sensores = sensores;
	}

	@Override
	public String toString() {
		return "EstadoSensor [idEstado=" + idEstado + ", descripcion=" + descripcion + ", codigo=" + codigo
				+ ", sensores=" + sensores + "]";
	}
	
}
