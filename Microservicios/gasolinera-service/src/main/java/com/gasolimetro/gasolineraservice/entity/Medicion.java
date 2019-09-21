package com.gasolimetro.gasolineraservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tme02_medicion")
public class Medicion {
	
	@Id
	@SequenceGenerator(name = "tme02_medicion_seq", sequenceName = "tme02_medicion_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tme02_medicion_seq")
	@Column(name="id_medicion")
	private Long idMedicion;
	
	@Column(name="tm_fin")
	private Date finMedicion;
	
	@Column(name="litros_solicitados")
	private Double litrosSolicitados;
	
	@Column(name="litros_ingresados")
	private Double litrosIngresados;
	
	@Column(name="precio_solicitado")
	private Double precioSolicitado;
	
	@Column(name="precio_total")
	private Double precioPagado;
	
	@Column(name="bomba")
	private Integer bomba;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumns({
	  @JoinColumn(name = "id_automovil", insertable = false, updatable = false),
	  @JoinColumn(name = "id_sensor", insertable = false, updatable = false)
	})
	@LazyCollection(LazyCollectionOption.FALSE)
	private AutomovilSensor automovilSensor;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_gasolinera")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Gasolinera gasolinera;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_estado")
	@LazyCollection(LazyCollectionOption.FALSE)
	private EstadoMedicion estadoMedicion;

	public Medicion() {
		super();
	}

	public Medicion(Long idMedicion, Date finMedicion, Double litrosSolicitados, Double litrosIngresados,
			Double precioSolicitado, Double precioPagado, Integer bomba, AutomovilSensor automovilSensor,
			Gasolinera gasolinera, EstadoMedicion estadoMedicion) {
		super();
		this.idMedicion = idMedicion;
		this.finMedicion = finMedicion;
		this.litrosSolicitados = litrosSolicitados;
		this.litrosIngresados = litrosIngresados;
		this.precioSolicitado = precioSolicitado;
		this.precioPagado = precioPagado;
		this.bomba = bomba;
		this.automovilSensor = automovilSensor;
		this.gasolinera = gasolinera;
		this.estadoMedicion = estadoMedicion;
	}

	public Long getIdMedicion() {
		return idMedicion;
	}

	public void setIdMedicion(Long idMedicion) {
		this.idMedicion = idMedicion;
	}
	
	public Date getFinMedicion() {
		return finMedicion;
	}

	public void setFinMedicion(Date finMedicion) {
		this.finMedicion = finMedicion;
	}

	public Double getLitrosSolicitados() {
		return litrosSolicitados;
	}

	public void setLitrosSolicitados(Double litrosSolicitados) {
		this.litrosSolicitados = litrosSolicitados;
	}

	public Double getLitrosIngresados() {
		return litrosIngresados;
	}

	public void setLitrosIngresados(Double litrosIngresados) {
		this.litrosIngresados = litrosIngresados;
	}

	public Double getPrecioSolicitado() {
		return precioSolicitado;
	}

	public void setPrecioSolicitado(Double precioSolicitado) {
		this.precioSolicitado = precioSolicitado;
	}

	public Double getPrecioPagado() {
		return precioPagado;
	}

	public void setPrecioPagado(Double precioPagado) {
		this.precioPagado = precioPagado;
	}

	public AutomovilSensor getAutomovilSensor() {
		return automovilSensor;
	}

	public void setAutomovilSensor(AutomovilSensor automovilSensor) {
		this.automovilSensor = automovilSensor;
	}

	public Gasolinera getGasolinera() {
		return gasolinera;
	}

	public void setGasolinera(Gasolinera gasolinera) {
		this.gasolinera = gasolinera;
	}

	public EstadoMedicion getEstadoMedicion() {
		return estadoMedicion;
	}

	public void setEstadoMedicion(EstadoMedicion estadoMedicion) {
		this.estadoMedicion = estadoMedicion;
	}

	@Override
	public String toString() {
		return "Medicion [idMedicion=" + idMedicion + ", finMedicion=" + finMedicion + ", litrosSolicitados="
				+ litrosSolicitados + ", litrosIngresados=" + litrosIngresados + ", precioSolicitado="
				+ precioSolicitado + ", precioPagado=" + precioPagado + ", automovilSensor=" + automovilSensor
				+ ", gasolinera=" + gasolinera + ", estadoMedicion=" + estadoMedicion + "]";
	}

}
