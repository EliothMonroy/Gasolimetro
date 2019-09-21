package com.gasolimetro.gasolineraservice.controller;

import java.util.Comparator;

import com.gasolimetro.gasolineraservice.models.GasolineraConsultaModel;

public class GasolineraSort implements Comparator<GasolineraConsultaModel> {

	@Override
	public int compare(GasolineraConsultaModel o1, GasolineraConsultaModel o2) {
		Integer resultado;
		if(o1.getCalificacion() != null && o2.getCalificacion() != null) {
			resultado = (int) Math.round(o1.getCalificacion()-o2.getCalificacion());
		} else {
			resultado = -10;
		}
		
		return resultado;
	}

}
