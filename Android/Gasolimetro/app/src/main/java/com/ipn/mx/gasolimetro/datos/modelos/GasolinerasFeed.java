package com.ipn.mx.gasolimetro.datos.modelos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GasolinerasFeed {
	@SerializedName("gasolinerasCercanas")
    private List<Gasolinera> results=null;

    public List<Gasolinera> getResults() {
        return results;
    }

    public void setResults(List<Gasolinera> results) {
        this.results = results;
    }

}
