package com.gasolimetro.medicionservice.entity;

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
@Table(name="tus08_estado_usuario")
public class EstadoUsuario implements Serializable{

	private static final long serialVersionUID = 6819166710271086101L;

	@Id
	@SequenceGenerator(name = "tus08_estado_usuario_seq", sequenceName = "tus08_estado_usuario_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tus08_estado_usuario_seq")
	@Column(name="id_estado")
	private Long idEstado;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="codigo")
	private String codigo;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "estadoUsuario")
	@JsonIgnore
	private List<Usuario> usuarios;

	public EstadoUsuario() {
		super();
	}

	public EstadoUsuario(Long idEstado, String descripcion, String codigo, List<Usuario> usuarios) {
		super();
		this.idEstado = idEstado;
		this.descripcion = descripcion;
		this.codigo = codigo;
		this.usuarios = usuarios;
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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String toString() {
		return "EstadoUsuario [idEstado=" + idEstado + ", descripcion=" + descripcion + ", codigo=" + codigo
				+ ", usuarios=" + usuarios + "]";
	}


}
