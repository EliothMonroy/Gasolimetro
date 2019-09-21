package com.ipn.gasolimetro.entity;

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

@Entity
@Table(name="tcl02_ubicacion")
public class Ubicacion implements Serializable{

	private static final long serialVersionUID = 1634464039098183107L;
	
	@Id
	@SequenceGenerator(name = "tcl02_ubicacion_seq", sequenceName = "tcl02_ubicacion_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tcl02_ubicacion_seq")
	@Column(name="id_ubicacion")
	private Long idUbicacion;
	
	@Column(name="codigo_postal")
	private String codigoPostal;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="municipio")
	private String municipio;
	
	@Column(name="calle")
	private String calle;
	
	@Column(name="colonia")
	private String colonia;
	
	@Column(name="numero")
	private Integer numero;
	
	@Column(name="referencia")
	private String referencia;
	
	@OneToOne
    @JoinColumn(name = "id_gasolinera")
	@LazyCollection(LazyCollectionOption.FALSE)
    private Gasolinera gasolinera;
	
	public Ubicacion() {
		super();
	}

	public Ubicacion(Long idUbicacion, String codigoPostal, String estado, String municipio, String calle,
			String colonia, Integer numero, String referencia, Gasolinera gasolinera) {
		super();
		this.idUbicacion = idUbicacion;
		this.codigoPostal = codigoPostal;
		this.estado = estado;
		this.municipio = municipio;
		this.calle = calle;
		this.colonia = colonia;
		this.numero = numero;
		this.referencia = referencia;
		this.gasolinera = gasolinera;
	}

	public Long getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Gasolinera getGasolinera() {
		return gasolinera;
	}

	public void setGasolinera(Gasolinera gasolinera) {
		this.gasolinera = gasolinera;
	}

	@Override
	public String toString() {
		return "Ubicacion [idUbicacion=" + idUbicacion + ", codigoPostal=" + codigoPostal + ", estado=" + estado
				+ ", municipio=" + municipio + ", calle=" + calle + ", colonia=" + colonia + ", numero=" + numero
				+ ", referencia=" + referencia + ", gasolinera=" + gasolinera + "]";
	}
	
}
