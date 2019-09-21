package com.gasolimetro.medicionservice.entity;

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
@Table(name="tus05_insignia_usuario")
public class InsigniaU implements Serializable{

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
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.insignia")
	private List<InsigniaUsuario> insigniasUsuarioJoin;
	

	public InsigniaU() {
		super();
	}

	public InsigniaU(Long idInsigniaUsuario, String descripcion, String urlImagen, String criterio,
			List<InsigniaUsuario> insigniasUsuarioJoin) {
		super();
		this.idInsigniaUsuario = idInsigniaUsuario;
		this.descripcion = descripcion;
		this.urlImagen = urlImagen;
		this.criterio = criterio;
		this.insigniasUsuarioJoin = insigniasUsuarioJoin;
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

	public List<InsigniaUsuario> getInsigniasUsuarioJoin() {
		return insigniasUsuarioJoin;
	}

	public void setInsigniasUsuarioJoin(List<InsigniaUsuario> insigniasUsuarioJoin) {
		this.insigniasUsuarioJoin = insigniasUsuarioJoin;
	}

	@Override
	public String toString() {
		return "InsigniaUsuario [idInsigniaUsuario=" + idInsigniaUsuario + ", descripcion=" + descripcion
				+ ", urlImagen=" + urlImagen + ", criterio=" + criterio + "]";
	}

}
