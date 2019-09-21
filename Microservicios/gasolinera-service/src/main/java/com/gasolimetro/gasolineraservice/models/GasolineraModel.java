package com.gasolimetro.gasolineraservice.models;

import java.util.List;

import com.gasolimetro.gasolineraservice.entity.Gasolinera;
import com.gasolimetro.gasolineraservice.entity.Insignia;

public class GasolineraModel {
	private Gasolinera gasolinera;
	private Integer  numeroMediciones;
	private List<Insignia> insigniasGasolinera;
	
	public GasolineraModel() {
		super();
	}

	public GasolineraModel(Gasolinera gasolinera, Integer numeroMediciones, List<Insignia> insigniasGasolinera) {
		super();
		this.gasolinera = gasolinera;
		this.numeroMediciones = numeroMediciones;
		this.insigniasGasolinera = insigniasGasolinera;
	}

	public Gasolinera getGasolinera() {
		return gasolinera;
	}

	public void setGasolinera(Gasolinera gasolinera) {
		this.gasolinera = gasolinera;
	}

	public Integer getNumeroMediciones() {
		return numeroMediciones;
	}

	public void setNumeroMediciones(Integer numeroMediciones) {
		this.numeroMediciones = numeroMediciones;
	}

	public List<Insignia> getInsigniasGasolinera() {
		return insigniasGasolinera;
	}

	public void setInsigniasGasolinera(List<Insignia> insigniasGasolinera) {
		this.insigniasGasolinera = insigniasGasolinera;
	}

	@Override
	public String toString() {
		return "GasolineraModel [gasolinera=" + gasolinera + ", numeroMediciones=" + numeroMediciones + "]";
	}

}
