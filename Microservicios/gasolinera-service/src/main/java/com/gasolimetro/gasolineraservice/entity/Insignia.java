package com.gasolimetro.gasolineraservice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tcl03_insignia_gasolinera")
public class Insignia implements Serializable{

	private static final long serialVersionUID = 362191287427567200L;
	
	@Id
	@SequenceGenerator(name = "tcl03_insignia_gasolinera_seq", sequenceName = "tcl03_insignia_gasolinera_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tcl03_insignia_gasolinera_seq")
	@Column(name="id_insignia_gasolinera")
	private Long idInsigniaGasolinera;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="url")
	private String url;
	
	@Column(name="criterio")
	private String criterio;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.insignia")
	private List<InsigniaGasolinera> insigniasGasolinerasJoin;
	

	public Insignia() {
		super();
	}

	public Insignia(Long idInsigniaGasolinera, List<InsigniaGasolinera> insigniasGasolinerasJoin,
			String descripcion, String url, String criterio) {
		super();
		this.idInsigniaGasolinera = idInsigniaGasolinera;
		this.insigniasGasolinerasJoin = insigniasGasolinerasJoin;
		this.descripcion = descripcion;
		this.url = url;
		this.criterio = criterio;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public List<InsigniaGasolinera> getInsigniasGasolinerasJoin() {
		return insigniasGasolinerasJoin;
	}

	public void setInsigniasGasolinerasJoin(List<InsigniaGasolinera> insigniasGasolinerasJoin) {
		this.insigniasGasolinerasJoin = insigniasGasolinerasJoin;
	}

	@Override
	public String toString() {
		return "InsigniaGasolinera [idInsigniaGasolinera=" + idInsigniaGasolinera + ", insigniasGasolinerasJoin="
				+ insigniasGasolinerasJoin + ", descripcion=" + descripcion + ", url=" + url + ", criterio=" + criterio
				+ "]";
	}

}
