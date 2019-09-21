package com.gasolimetro.gasolineraservice.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class InsigniaGasolineraPk implements Serializable{

	private static final long serialVersionUID = -6226410660735298945L;
	
	@ManyToOne
	private Gasolinera gasolinera;
	
	@ManyToOne
	private Insignia insignia;
	
	public InsigniaGasolineraPk() {
		super();
	}

	public InsigniaGasolineraPk(Gasolinera gasolinera, Insignia insignia) {
		super();
		this.gasolinera = gasolinera;
		this.insignia = insignia;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "InsigniaGasolineraPk [gasolinera=" + gasolinera + ", insignia=" + insignia + "]";
	}
	
}
