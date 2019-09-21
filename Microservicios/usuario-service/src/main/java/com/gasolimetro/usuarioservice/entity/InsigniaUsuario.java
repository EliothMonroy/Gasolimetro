package com.gasolimetro.usuarioservice.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tus01_usuario_tus05_insignia_usuario")
public class InsigniaUsuario implements Serializable{

	private static final long serialVersionUID = 2563191168706547373L;

	@EmbeddedId
	private InsigniaUsuarioPK pk;
	
	@JsonIgnore
	@Transient
	Usuario usuario;
	
	@JsonIgnore
	@Transient
	InsigniaU insignia;

	public InsigniaUsuario() {
		super();
	}

	public InsigniaUsuario(InsigniaUsuarioPK pk, Usuario usuario, InsigniaU insignia) {
		super();
		this.pk = pk;
		this.usuario = usuario;
		this.insignia = insignia;
	}

	public InsigniaUsuarioPK getPk() {
		return pk;
	}

	public void setPk(InsigniaUsuarioPK pk) {
		this.pk = pk;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public InsigniaU getInsignia() {
		return insignia;
	}

	public void setInsignia(InsigniaU insignia) {
		this.insignia = insignia;
	}

	@Override
	public String toString() {
		return "InsigniaUsuario [pk=" + pk + ", usuario=" + usuario + ", insignia=" + insignia + "]";
	}

}
