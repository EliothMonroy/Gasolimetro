package com.gasolimetro.usuarioservice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;


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
	
	@NotNull
	@Column(name="tipo_usuario")
	private Integer tipoUsuario;
	
	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name="id_estado")
	private EstadoUsuario estadoUsuario;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.usuario")
	private List<InsigniaUsuario> insigniasUsuarios;
	
	
	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL,
             fetch = FetchType.LAZY, optional = false)
	private Login login;
	
	private Integer puerto;

	public Usuario() {
		super();
	}

	public Usuario(Long idUsuario, @NotNull @Size(min = 1, max = 50) String nombre,
			@NotNull @Size(min = 1, max = 50) String apellidoPaterno,
			@NotNull @Size(min = 1, max = 50) String apellidoMaterno, @NotNull Boolean genero,
			@NotNull Integer tipoUsuario, EstadoUsuario estadoUsuario, List<InsigniaUsuario> insigniasUsuarios,
			Login login, Integer puerto) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.genero = genero;
		this.tipoUsuario = tipoUsuario;
		this.estadoUsuario = estadoUsuario;
		this.insigniasUsuarios = insigniasUsuarios;
		this.login = login;
		this.puerto = puerto;
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

	public EstadoUsuario getEstadoUsuario() {
		return estadoUsuario;
	}

	public void setEstadoUsuario(EstadoUsuario estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}
	
	public Integer getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(Integer tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	@OneToOne(mappedBy = "post", cascade = CascadeType.ALL,
             fetch = FetchType.LAZY, optional = false)
	public Integer getPuerto() {
		return puerto;
	}

	public void setPuerto(Integer puerto) {
		this.puerto = puerto;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
	
	public List<InsigniaUsuario> getInsigniasUsuarios() {
		return insigniasUsuarios;
	}

	public void setInsigniasUsuarios(List<InsigniaUsuario> insigniasUsuarios) {
		this.insigniasUsuarios = insigniasUsuarios;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno
				+ ", apellidoMaterno=" + apellidoMaterno + ", genero=" + genero + ", estadoUsuario=" + estadoUsuario
				+ ", login=" + login + ", puerto=" + puerto + "]";
	}

}
