package com.gasolimetro.gasolineraservice.models;

import com.gasolimetro.gasolineraservice.entity.Insignia;

public class InsigniaModel {
	private Insignia insigniaGasolinera;
	private Integer numeroInsignias;
	
	public InsigniaModel() {
		super();
	}

	public InsigniaModel(Insignia insigniaGasolinera, Integer numeroInsignias) {
		super();
		this.insigniaGasolinera = insigniaGasolinera;
		this.numeroInsignias = numeroInsignias;
	}

	public Insignia getInsigniaGasolinera() {
		return insigniaGasolinera;
	}

	public void setInsigniaGasolinera(Insignia insigniaGasolinera) {
		this.insigniaGasolinera = insigniaGasolinera;
	}

	public Integer getNumeroInsignias() {
		return numeroInsignias;
	}

	public void setNumeroInsignias(Integer numeroInsignias) {
		this.numeroInsignias = numeroInsignias;
	}

	@Override
	public String toString() {
		return "InsigniaModel [insigniaGasolinera=" + insigniaGasolinera + ", numeroInsignias=" + numeroInsignias + "]";
	}
	
}
