package com.gasolimetro.medicionservice;

public class ConstantesNegocio {

	public static final Integer REGISTER_EXISTS = 1;
	
	public static final String MSG_SENSOR_EXISTS = "El nombre del sensor ya fué registrado previamente, por favor intenta con uno nuevo"; 
	public static final String MSG_SENSOR_CREATED = "El sensor has sido registrado con éxito, no olvides activarlo";
	public static final String MSG_SENSOR_DELETED = "El sensor ha sido eliminado con éxito";
	public static final String MSG_NO_SENSORES = "Lo sentimos, primero necesitas necesitas registrar un sensor";
	public static final String MSG_NO_ACTIVADO = "Para continuar, debes asociar un sensor a tu automóvil";
	
	public static final String MSG_MEDICION_CREATED = "La medición ha sido registrada con éxito, gracias por colaborar todos los Mexicanos :)";
	
	public static final String GENERIC_ERROR = "Ocurrio un error inesperado, contacté al administrador";
	public static final String NOT_FOUND = "No encontramos lo que estas buscando";
	public static final String MSG_ERROR_SERVER = "El sistema ha experimentado un error, porfavor contacte al administrador";
	
	public static final Long ESTADO_SENSOR_REGISTRADO = 1L;
}
