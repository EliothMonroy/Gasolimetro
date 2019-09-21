package com.gasolimetro.gasolineraservice.entity;

import java.util.List;

public class PanelControl {
	private List<InsigniaU> insignias;
	private List<Medicion> mediciones;
	
	public PanelControl() {
		super();
	}

	public PanelControl(List<InsigniaU> insignias, List<Medicion> mediciones) {
		super();
		this.insignias = insignias;
		this.mediciones = mediciones;
	}

	public List<InsigniaU> getInsignias() {
		return insignias;
	}

	public void setInsignias(List<InsigniaU> insignias) {
		this.insignias = insignias;
	}

	public List<Medicion> getMediciones() {
		return mediciones;
	}

	public void setMediciones(List<Medicion> mediciones) {
		this.mediciones = mediciones;
	}

	@Override
	public String toString() {
		return "PanelControl [insignias=" + insignias + ", mediciones=" + mediciones + "]";
	}
	
}