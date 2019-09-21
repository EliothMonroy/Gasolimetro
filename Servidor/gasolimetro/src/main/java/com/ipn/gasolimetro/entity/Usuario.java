package com.ipn.gasolimetro.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name="tus01_usuario")
public class Usuario implements Serializable{

	private static final long serialVersionUID = -4991924667156512897L;
	
	@Id
	@SequenceGenerator(name = "tus01_usuario_seq", sequenceName = "tus01_usuario_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tus01_usuario_seq")
	@Column(name="id_usuario")
	private Long idUsuario;
	
	@NotNull
	@Size(min=1, max=50)
	@Column(name="nombre")
	private String nombre;
	
	@NotNull
	@Size(min=1, max=50)
	@Column(name="apellido_paterno")
	private String apellidoPaterno;
	
	@NotNull 
	@Size(min=1, max=50)
	@Column(name="apellido_materno")
	private String apellidoMaterno;
	
	@NotNull
	@Column(name="genero")
	private Boolean genero;
	
	@OneToMany(mappedBy = "usuario")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DireccionUsuario> direcciones;
	
	@OneToMany(mappedBy = "usuario")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Automovil> automoviles;
	
	@OneToMany(mappedBy = "usuario")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Sensor> sensores;
	
	@OneToMany(mappedBy = "usuario")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Reporte> reportes;
	
	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name="id_estado")
	private EstadoUsuario estadoUsuario;
	
	@ManyToMany(mappedBy = "usuarios")
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<Gasolinera> gasolineras;
	
	@ManyToMany(mappedBy = "usuarios")
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<InsigniaUsuario> insignias;

	public Usuario() {
		super();
	}

	public Usuario(Long idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, Boolean genero, List<DireccionUsuario> direcciones, List<Automovil> automoviles, List<Sensor> sensores,
			List<Reporte> reportes, EstadoUsuario estadoUsuario, List<Gasolinera> gasolineras,
			List<InsigniaUsuario> insignias) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.genero = genero;
		this.direcciones = direcciones;
		this.automoviles = automoviles;
		this.sensores = sensores;
		this.reportes = reportes;
		this.estadoUsuario = estadoUsuario;
		this.gasolineras = gasolineras;
		this.insignias = insignias;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public Boolean getGenero() {
		return genero;
	}

	public void setGenero(Boolean genero) {
		this.genero = genero;
	}

	public List<DireccionUsuario> getDirecciones() {
		return direcciones;
	}

	public void setDirecciones(List<DireccionUsuario> direcciones) {
		this.direcciones = direcciones;
	}

	public List<Automovil> getAutomoviles() {
		return automoviles;
	}

	public void setAutomoviles(List<Automovil> automoviles) {
		this.automoviles = automoviles;
	}

	public List<Sensor> getSensores() {
		return sensores;
	}

	public void setSensores(List<Sensor> sensores) {
		this.sensores = sensores;
	}

	public List<Reporte> getReportes() {
		return reportes;
	}

	public void setReportes(List<Reporte> reportes) {
		this.reportes = reportes;
	}

	public EstadoUsuario getEstadoUsuario() {
		return estadoUsuario;
	}

	public void setEstadoUsuario(EstadoUsuario estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}

	public List<Gasolinera> getGasolineras() {
		return gasolineras;
	}

	public void setGasolineras(List<Gasolinera> gasolineras) {
		this.gasolineras = gasolineras;
	}

	public List<InsigniaUsuario> getInsignias() {
		return insignias;
	}

	public void setInsignias(List<InsigniaUsuario> insignias) {
		this.insignias = insignias;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno
				+ ", apellidoMaterno=" + apellidoMaterno + ", genero=" + genero + ", direcciones="
				+ direcciones + ", automoviles=" + automoviles + ", sensores=" + sensores + ", reportes=" + reportes
				+ ", estadoUsuario=" + estadoUsuario + ", gasolineras=" + gasolineras + ", insignias=" + insignias
				+ "]";
	}
	
}
