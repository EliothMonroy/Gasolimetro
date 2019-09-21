package com.ipn.mx.gasolimetro.datos.modelos;

import java.util.List;

public class ResultModel {

	private List<GasolineraConsultaModel> gasolinerasCercanas;

	
	public ResultModel() {
		super();
	}

	public ResultModel(List<GasolineraConsultaModel> gasolinerasCercanas) {
		super();
		this.gasolinerasCercanas = gasolinerasCercanas;
	}

	public List<GasolineraConsultaModel> getGasolinerasCercanas() {
		return gasolinerasCercanas;
	}

	public void setGasolinerasCercanas(List<GasolineraConsultaModel> gasolinerasCercanas) {
		this.gasolinerasCercanas = gasolinerasCercanas;
	}

	@Override
	public String toString() {
		return "ResultModel [gasolinerasCercanas=" + gasolinerasCercanas + "]";
	}
	
}
