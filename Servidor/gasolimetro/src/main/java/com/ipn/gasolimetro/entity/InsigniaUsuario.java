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
@Table(name="tus05_insignia_usuario")
public class InsigniaUsuario implements Serializable{

	private static final long serialVersionUID = 228834383748825021L;

	@Id
	@SequenceGenerator(name = "tus05_insignia_usuario_seq", sequenceName = "tus05_insignia_usuario_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tus05_insignia_usuario_seq")
	@Column(name="id_insignia_usuario")
	private Long idInsigniaUsuario;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="url_imagen")
	private String urlImagen;
	
	@Column(name="criterio")
	private String criterio;

	@ManyToMany
	@JoinTable(name = "tus01_usuario_tus05_insignia",
		joinColumns = @JoinColumn(name = "id_insignia_usuario"),
	    inverseJoinColumns = @JoinColumn(name = "id_usuario"))
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Usuario> usuarios;

	public InsigniaUsuario() {
		super();
	}

	public InsigniaUsuario(Long idInsigniaUsuario, String descripcion, String urlImagen, String criterio,
			List<Usuario> usuarios) {
		super();
		this.idInsigniaUsuario = idInsigniaUsuario;
		this.descripcion = descripcion;
		this.urlImagen = urlImagen;
		this.criterio = criterio;
		this.usuarios = usuarios;
	}

	public Long getIdInsigniaUsuario() {
		return idInsigniaUsuario;
	}

	public void setIdInsigniaUsuario(Long idInsigniaUsuario) {
		this.idInsigniaUsuario = idInsigniaUsuario;
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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String toString() {
		return "InsigniaUsuario [idInsigniaUsuario=" + idInsigniaUsuario + ", descripcion=" + descripcion
				+ ", urlImagen=" + urlImagen + ", criterio=" + criterio + ", usuarios=" + usuarios + "]";
	}
	
}
