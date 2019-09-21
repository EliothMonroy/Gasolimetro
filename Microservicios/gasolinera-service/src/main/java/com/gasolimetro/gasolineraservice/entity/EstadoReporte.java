package com.gasolimetro.gasolineraservice.entity;

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
@Table(name="tus06_estado_reporte")
public class EstadoReporte implements Serializable{

	private static final long serialVersionUID = 7730618929276793650L;

	@Id
	@SequenceGenerator(name = "tus06_estado_reporte_seq", sequenceName = "tus06_estado_reporte_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tus06_estado_reporte_seq")
	@Column(name="id_estado")
	private Long idEstado;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="codigo")
	private String codigo;
	
	@OneToMany(mappedBy = "estadoReporte")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Reporte> reportes;

	public EstadoReporte() {
		super();
	}

	public EstadoReporte(Long idEstado, String descripcion, String codigo, List<Reporte> reportes) {
		super();
		this.idEstado = idEstado;
		this.descripcion = descripcion;
		this.codigo = codigo;
		this.reportes = reportes;
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

	public List<Reporte> getReportes() {
		return reportes;
	}

	public void setReportes(List<Reporte> reportes) {
		this.reportes = reportes;
	}

	@Override
	public String toString() {
		return "EstadoReporte [idEstado=" + idEstado + ", descripcion=" + descripcion + ", codigo=" + codigo
				+ ", reportes=" + reportes + "]";
	}
	
}
