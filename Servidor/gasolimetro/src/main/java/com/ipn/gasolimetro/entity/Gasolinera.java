package com.ipn.gasolimetro.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="tcl01_gasolinera")
public class Gasolinera implements Serializable{

	private static final long serialVersionUID = 8304686201084169252L;

	@Id
	@SequenceGenerator(name = "tcl01_gasolinera_seq", sequenceName = "tcl01_gasolinera_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tcl01_gasolinera_seq")
	@Column(name="id_gasolinera")
	private Long idGasolinera;
	
	@Column(name="tx_gasolinera")
	private String txGasolinera;
	
	@Column(name="calificacion")
	private Double calificacion;
	
	@OneToOne(mappedBy = "gasolinera")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Ubicacion ubicacion;
	
	@OneToMany(mappedBy = "gasolinera")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Medicion> mediciones;
	
	@ManyToMany
	@JoinTable(name = "tus01_usuario_tcl01_gasolinera",
		joinColumns = @JoinColumn(name = "id_gasolinera"),
	    inverseJoinColumns = @JoinColumn(name = "id_usuario"))
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Usuario> usuarios;
	
	@ManyToMany(mappedBy = "gasolineras")
    private List<InsigniaGasolinera> insignias;
	
	public Gasolinera() {
		super();
	}

	public Gasolinera(Long idGasolinera, String txGasolinera, Double calificacion, Ubicacion ubicacion,
			List<Medicion> mediciones, List<Usuario> usuarios, List<InsigniaGasolinera> insignias) {
		super();
		this.idGasolinera = idGasolinera;
		this.txGasolinera = txGasolinera;
		this.calificacion = calificacion;
		this.ubicacion = ubicacion;
		this.mediciones = mediciones;
		this.usuarios = usuarios;
		this.insignias = insignias;
	}

	public Long getIdGasolinera() {
		return idGasolinera;
	}

	public void setIdGasolinera(Long idGasolinera) {
		this.idGasolinera = idGasolinera;
	}

	public String getTxGasolinera() {
		return txGasolinera;
	}

	public void setTxGasolinera(String txGasolinera) {
		this.txGasolinera = txGasolinera;
	}

	public Double getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Double calificacion) {
		this.calificacion = calificacion;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public List<Medicion> getMediciones() {
		return mediciones;
	}

	public void setMediciones(List<Medicion> mediciones) {
		this.mediciones = mediciones;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<InsigniaGasolinera> getInsignias() {
		return insignias;
	}

	public void setInsignias(List<InsigniaGasolinera> insignias) {
		this.insignias = insignias;
	}

	@Override
	public String toString() {
		return "Gasolinera [idGasolinera=" + idGasolinera + ", txGasolinera=" + txGasolinera + ", calificacion="
				+ calificacion + ", ubicacion=" + ubicacion + ", mediciones=" + mediciones + ", usuarios=" + usuarios
				+ ", insignias=" + insignias + "]";
	}
	
}
