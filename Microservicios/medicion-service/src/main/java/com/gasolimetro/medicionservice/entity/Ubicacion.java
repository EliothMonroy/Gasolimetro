package com.gasolimetro.medicionservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tcl02_ubicacion")
public class Ubicacion implements Serializable{

	private static final long serialVersionUID = 1634464039098183107L;
	
	@Id
	@SequenceGenerator(name = "tcl02_ubicacion_seq", sequenceName = "tcl02_ubicacion_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tcl02_ubicacion_seq")
	@Column(name="id_ubicacion")
	private Long idUbicacion;
	
	@Column(name="latitud")
	private String latitud;
	
	@Column(name="longitud", precision = 5)
	private String longitud;
	
	@JsonIgnore
	@OneToOne
    @JoinColumn(name = "id_gasolinera")
	@LazyCollection(LazyCollectionOption.FALSE)
    private Gasolinera gasolinera;
	
	public Ubicacion() {
		super();
	}

	public Ubicacion(Long idUbicacion, String latitud, String longitud, Gasolinera gasolinera) {
		super();
		this.idUbicacion = idUbicacion;
		this.latitud = latitud;
		this.longitud = longitud;
		this.gasolinera = gasolinera;
	}

	public Long getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}
	
	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public Gasolinera getGasolinera() {
		return gasolinera;
	}

	public void setGasolinera(Gasolinera gasolinera) {
		this.gasolinera = gasolinera;
	}

	@Override
	public String toString() {
		return "Ubicacion [idUbicacion=" + idUbicacion + ", latitud=" + latitud + ", longitud=" + longitud
				+ ", gasolinera= ]";
	}
	
}