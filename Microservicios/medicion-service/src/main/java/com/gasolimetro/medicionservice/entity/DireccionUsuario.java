package com.gasolimetro.medicionservice.entity;

import java.io.Serializable;

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
@Table(name="tus02_direccion_usuario")
public class DireccionUsuario implements Serializable{

	private static final long serialVersionUID = 5462861246478992101L;
	
	@Id
	@SequenceGenerator(name = "tus02_direccion_usuario_seq", sequenceName = "tus02_direccion_usuario_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tus02_direccion_usuario_seq")
	@Column(name="id_direccion_usuario")
	private Long idDireccionUsuario;
	
	@Column(name="codigo_postal")
	private String codigoPostal;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="municipio")
	private String municipio;
	
	@Column(name="calle")
	private String calle;
	
	@Column(name="colonia")
	private String colonia;
	
	@Column(name="numero")
	private Integer numero;
	
	@Column(name="referencia")
	private String referencia;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Usuario usuario;

	public DireccionUsuario() {
		super();
	}

	public DireccionUsuario(Long idDireccionUsuario, String codigoPostal, String estado, String municipio, String calle,
			String colonia, Integer numero, String referencia, Usuario usuario) {
		super();
		this.idDireccionUsuario = idDireccionUsuario;
		this.codigoPostal = codigoPostal;
		this.estado = estado;
		this.municipio = municipio;
		this.calle = calle;
		this.colonia = colonia;
		this.numero = numero;
		this.referencia = referencia;
		this.usuario = usuario;
	}

	public Long getIdDireccionUsuario() {
		return idDireccionUsuario;
	}

	public void setIdDireccionUsuario(Long idDireccionUsuario) {
		this.idDireccionUsuario = idDireccionUsuario;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "DireccionUsuario [idDireccionUsuario=" + idDireccionUsuario + ", codigoPostal=" + codigoPostal
				+ ", estado=" + estado + ", municipio=" + municipio + ", calle=" + calle + ", colonia=" + colonia
				+ ", numero=" + numero + ", referencia=" + referencia + ", usuario=" + usuario + "]";
	}
	
}
