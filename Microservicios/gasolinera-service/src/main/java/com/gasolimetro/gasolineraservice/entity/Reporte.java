package com.gasolimetro.gasolineraservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="tus04_reporte")
public class Reporte {

	@Id
	@SequenceGenerator(name = "tus04_reporte_seq", sequenceName = "tus04_reporte_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tus04_reporte_seq")
	@Column(name="id_reporte")
	private Long idReporte;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="fecha")
	private Date fecha;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="id_estado")
	@LazyCollection(LazyCollectionOption.FALSE)
	private EstadoReporte estadoReporte;

	public Reporte() {
		super();
	}

	public Reporte(Long idReporte, String descripcion, Date fecha, Usuario usuario, EstadoReporte estadoReporte) {
		super();
		this.idReporte = idReporte;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.usuario = usuario;
		this.estadoReporte = estadoReporte;
	}

	public Long getIdReporte() {
		return idReporte;
	}

	public void setIdReporte(Long idReporte) {
		this.idReporte = idReporte;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public EstadoReporte getEstadoReporte() {
		return estadoReporte;
	}

	public void setEstadoReporte(EstadoReporte estadoReporte) {
		this.estadoReporte = estadoReporte;
	}

	@Override
	public String toString() {
		return "Reporte [idReporte=" + idReporte + ", descripcion=" + descripcion + ", fecha=" + fecha + ", usuario="
				+ usuario + ", estadoReporte=" + estadoReporte + "]";
	}
	
}
