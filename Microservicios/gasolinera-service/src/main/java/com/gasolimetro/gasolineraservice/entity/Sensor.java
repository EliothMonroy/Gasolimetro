package com.gasolimetro.gasolineraservice.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tme01_sensor")
public class Sensor {
	@Id
	@SequenceGenerator(name = "tme01_sensor_seq", sequenceName = "tme01_sensor_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tme01_sensor_seq")
	@Column(name="id_sensor")
	private Long idSensor;
	
	@Column(name="tx_device")
	private String txDevice;
	
	@JsonIgnore
	@OneToMany(mappedBy = "id.sensor")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<AutomovilSensor> automovilesSensor;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_usuario")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="id_estado")
	@LazyCollection(LazyCollectionOption.FALSE)
	private EstadoSensor estadoSensor;

	public Sensor() {
		super();
	}

	public Sensor(Long idSensor, String txDevice, List<AutomovilSensor> automovilesSensor, Usuario usuario,
			EstadoSensor estadoSensor) {
		super();
		this.idSensor = idSensor;
		this.txDevice = txDevice;
		this.automovilesSensor = automovilesSensor;
		this.usuario = usuario;
		this.estadoSensor = estadoSensor;
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

	public List<AutomovilSensor> getAutomovilesSensor() {
		return automovilesSensor;
	}

	public void setAutomovilesSensor(List<AutomovilSensor> automovilesSensor) {
		this.automovilesSensor = automovilesSensor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public EstadoSensor getEstadoSensor() {
		return estadoSensor;
	}

	public void setEstadoSensor(EstadoSensor estadoSensor) {
		this.estadoSensor = estadoSensor;
	}

	@Override
	public String toString() {
		return "Sensor [idSensor=" + idSensor + ", txDevice=" + txDevice + ", automovilesSensor=" + automovilesSensor
				+ ", usuario=" + usuario + ", estadoSensor=" + estadoSensor + "]";
	}
	 
}
