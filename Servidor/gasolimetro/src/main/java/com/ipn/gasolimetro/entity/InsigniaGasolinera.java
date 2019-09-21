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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="tcl03_insignia_gasolinera")
public class InsigniaGasolinera implements Serializable{

	private static final long serialVersionUID = 362191287427567200L;
	
	@Id
	@SequenceGenerator(name = "tcl03_insignia_gasolinera_seq", sequenceName = "tcl03_insignia_gasolinera_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tcl03_insignia_gasolinera_seq")
	@Column(name="id_insignia_gasolinera")
	private Long idInsigniaGasolinera;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="url_imagen")
	private String urlImagen;
	
	@Column(name="criterio")
	private String criterio;

	@ManyToMany
	@JoinTable(name = "tcl03_insignia_tcl01_gasolina",
		joinColumns = @JoinColumn(name = "id_insignia_gasolinera"),
	    inverseJoinColumns = @JoinColumn(name = "id_gasolinera"))
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Gasolinera> gasolineras;

	public InsigniaGasolinera() {
		super();
	}

	public InsigniaGasolinera(Long idInsigniaGasolinera, String descripcion, String urlImagen, String criterio,
			List<Gasolinera> gasolineras) {
		super();
		this.idInsigniaGasolinera = idInsigniaGasolinera;
		this.descripcion = descripcion;
		this.urlImagen = urlImagen;
		this.criterio = criterio;
		this.gasolineras = gasolineras;
	}

	public Long getIdInsigniaGasolinera() {
		return idInsigniaGasolinera;
	}

	public void setIdInsigniaGasolinera(Long idInsigniaGasolinera) {
		this.idInsigniaGasolinera = idInsigniaGasolinera;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public List<Gasolinera> getGasolineras() {
		return gasolineras;
	}

	public void setGasolineras(List<Gasolinera> gasolineras) {
		this.gasolineras = gasolineras;
	}

	@Override
	public String toString() {
		return "InsigniaGasolinera [idInsigniaGasolinera=" + idInsigniaGasolinera + ", descripcion=" + descripcion
				+ ", urlImagen=" + urlImagen + ", criterio=" + criterio + ", gasolineras=" + gasolineras + "]";
	}
	
}
