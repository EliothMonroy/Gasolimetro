package com.gasolimetro.usuarioservice.entity;

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
@Table(name="tme04_estado_medicion")
public class EstadoMedicion implements Serializable{

	private static final long serialVersionUID = 62575326004571924L;

	@Id
	@SequenceGenerator(name = "tme04_estado_medicion_seq", sequenceName = "tme04_estado_medicion_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tme04_estado_medicion_seq")
	@Column(name="id_estado")
	private Long idEstado;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="codigo")
	private String codigo;
	
	@OneToMany(mappedBy = "estadoMedicion")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Medicion> mediciones;

	public EstadoMedicion() {
		super();
	}

	public EstadoMedicion(Long idEstado, String descripcion, String codigo, List<Medicion> mediciones) {
		super();
		this.idEstado = idEstado;
		this.descripcion = descripcion;
		this.codigo = codigo;
		this.mediciones = mediciones;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<Medicion> getMediciones() {
		return mediciones;
	}

	public void setMediciones(List<Medicion> mediciones) {
		this.mediciones = mediciones;
	}

	@Override
	public String toString() {
		return "EstadoMedicion [idEstado=" + idEstado + ", descripcion=" + descripcion + ", codigo=" + codigo
				+ ", mediciones=" + mediciones + "]";
	}
}
