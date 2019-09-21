package com.gasolimetro.gasolineraservice.models;

public class GasolineraConsultaModel {
	private String place_id;
    private String lat;
    private String lon;
    
    private Long gasolineraId;
    private String nombreGasolinera;
    private Double calificacion;
    
    private Boolean exists;
    
    public GasolineraConsultaModel() {
		super();
	}

	public GasolineraConsultaModel(String place_id, String lat, String lon, Long gasolineraId, String nombreGasolinera,
			Double calificacion, Boolean exists) {
		super();
		this.place_id = place_id;
		this.lat = lat;
		this.lon = lon;
		this.gasolineraId = gasolineraId;
		this.nombreGasolinera = nombreGasolinera;
		this.calificacion = calificacion;
		this.exists = exists;
	}

	public Long getGasolineraId() {
		return gasolineraId;
	}

	public void setGasolineraId(Long gasolineraId) {
		this.gasolineraId = gasolineraId;
	}

	public String getPlace_id() {
		return place_id;
	}

	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}

	public String getLat() {
		return lat;
	}


	public void setLat(String lat) {
		this.lat = lat;
	}


	public String getLon() {
		return lon;
	}


	public void setLon(String lon) {
		this.lon = lon;
	}


	public String getNombreGasolinera() {
		return nombreGasolinera;
	}

	public void setNombreGasolinera(String nombreGasolinera) {
		this.nombreGasolinera = nombreGasolinera;
	}

	public Double getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Double calificacion) {
		this.calificacion = calificacion;
	}

	public Boolean getExists() {
		return exists;
	}

	public void setExists(Boolean exists) {
		this.exists = exists;
	}

	@Override
	public String toString() {
		return "GasolineraConsultaModel [gasolineraId=" + gasolineraId + ", place_id=" + place_id + ", lat=" + lat
				+ ", lon=" + lon + ", nombreGasolinera=" + nombreGasolinera + ", calificacion=" + calificacion + "]";
	}
	
	
	
}
