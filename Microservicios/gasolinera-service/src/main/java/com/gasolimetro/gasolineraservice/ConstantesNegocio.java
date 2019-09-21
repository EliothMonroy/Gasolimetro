package com.gasolimetro.gasolineraservice;

public class ConstantesNegocio {

	public static final Integer ZERO = 0;
	public static final Integer REGISTER_EXISTS = 1;
	public static final Boolean ENV_DEVELOPMENT = Boolean.TRUE;
	
	public static final String MSG_MEDICIONES_PRUEBA = "Las mediciones de prueba han sido registradas";
	public static final String MSG_PROMEDIO_PRUEBA = "El promedio ha sido calculado";
	
	public static final String MSG_SENSOR_EXISTS = "El nombre del sensor ya fué registrado previamente, por favor intenta con uno nuevo"; 
	public static final String MSG_SENSOR_CREATED = "El sensor has sido registrado con éxito, no olvides activarlo";
	public static final String MSG_SENSOR_DELETED = "El sensor ha sido eliminado con éxito";
	
	public static final String MSG_GASOLINERA_EXISTS = "Ya existe una gasolinera registrada en esta ubicación";
	public static final String MSG_GASOLINERA_CREATED = "La gasolinera ha sido registrada con éxito";
	public static final String MSG_GASOLINERA_DELETED = "La gasolinera ha sido eliminada con éxito";
	
	public static final String MSG_CREATE_GASOLINERAS = "Las gasolineras han sido guardadas";
	public static final Double CALIFICACION_INICIAL = 0.0;
	
	public static final String GENERIC_ERROR = "Ocurrio un error inesperado, contacté al administrador";
	public static final String NOT_FOUND = "No encontramos lo que estas buscando";
	public static final String MSG_ERROR_SERVER = "El sistema ha experimentado un error, porfavor contacte al administrador";
}
