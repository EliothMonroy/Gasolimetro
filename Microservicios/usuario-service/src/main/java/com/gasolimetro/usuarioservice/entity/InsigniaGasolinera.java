package com.gasolimetro.usuarioservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tcl03_insignia_gasolinera_tcl01_gasolinera")
public class InsigniaGasolinera implements Serializable{
	
	private static final long serialVersionUID = 9211649626106317022L;

	@EmbeddedId
	private InsigniaGasolineraPk pk;
	
	@JsonIgnore
	@Transient
	Gasolinera gasolinera;
	
	@JsonIgnore
	@Transient
	Insignia insignia;
	
	@Column(name="numero_insignias")
	private Integer numeroInsignias;
	
	public InsigniaGasolinera() {
		super();
	}

	public InsigniaGasolinera(InsigniaGasolineraPk pk, Gasolinera gasolinera, Insignia insignia,
			Integer numeroInsignias) {
		super();
		this.pk = pk;
		this.gasolinera = gasolinera;
		this.insignia = insignia;
		this.numeroInsignias = numeroInsignias;
	}

	public InsigniaGasolineraPk getPk() {
		return pk;
	}

	public void setPk(InsigniaGasolineraPk pk) {
		this.pk = pk;
	}

	public Gasolinera getGasolinera() {
		return gasolinera;
	}

	public void setGasolinera(Gasolinera gasolinera) {
		this.gasolinera = gasolinera;
	}

	public Insignia getInsignia() {
		return insignia;
	}

	public void setInsignia(Insignia insignia) {
		this.insignia = insignia;
	}

	public Integer getNumeroInsignias() {
		return numeroInsignias;
	}

	public void setNumeroInsignias(Integer numeroInsignias) {
		this.numeroInsignias = numeroInsignias;
	}

	@Override
	public String toString() {
		return "InsigniaGasolinera [pk=" + pk + ", gasolinera=" + gasolinera + ", insignia=" + insignia
				+ ", numeroInsignias=" + numeroInsignias + "]";
	}
}
