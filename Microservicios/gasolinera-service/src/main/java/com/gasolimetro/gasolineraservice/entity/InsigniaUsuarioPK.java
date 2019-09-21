package com.gasolimetro.gasolineraservice.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class InsigniaUsuarioPK implements Serializable{

	private static final long serialVersionUID = -2913276868927265981L;
	
	@ManyToOne
	private Usuario usuario;
	
	@ManyToOne
	private InsigniaU insignia;

	public InsigniaUsuarioPK() {
		super();
	}

	public InsigniaUsuarioPK(Usuario usuario, InsigniaU insignia) {
		super();
		this.usuario = usuario;
		this.insignia = insignia;
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
		return "InsigniaUsuarioPK [usuario=" + usuario + ", insignia=" + insignia + "]";
	}
	
}
