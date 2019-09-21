package com.gasolimetro.usuarioservice;

public class ConstantesNegocio {

	public static final Integer REGISTER_EXISTS = 1;
	public static final String MSG_USUARIO_CREATE = "Usuario registrado con éxito, revisa tu email para verificar tu cuenta";
	public static final String MSG_USUARIO_EDIT = "Tus datos han sido actualizados con éxito";	
	public static final String MSG_USUARIO_EXISTS = "El email ingresado ya existe, intenta con uno nuevo";
	public static final String MSG_USUARIO_DELETE = "La cuenta ha sido eliminada con éxito, adiós :(";
	
	public static final String MSG_AUTOMOVIL_EXISTS = "Encontramos otro automóvil registrado con el mismo nombre, intenta con uno nuevo";
	public static final String MSG_AUTOMOVIL_CREATE = "Automóvil registrado con éxito";
	public static final String MSG_AUTOMOVIL_EDIT = "Los datos del vehículo han sido actualizados con éxito";
	public static final String MSG_AUTOMOVIL_DELETE = "El automóvil has sido eliminado con éxito";
	
	public static final String MSG_VERIFICACION_CUENTA = "Felicidades, tú cuenta ha sido verificada exitosamente";
	public static final String MSG_VERIFICATION_ERROR = "Error: La cuenta no pudo ser verficada";
	
	public static final String MSG_LOGIN_ERROR = "Las credenciales ingresadas son incorrectas";
	public static final String MSG_LOGIN_SUCCESS = "Bienvenido";
	
	public static final String MSG_PASSWORD_EMAIL = "Se ha enviado un correo electrónico para el reestablecimiento de las contraseña";
	public static final String MSG_EMAIL_ERROR = "El correo electrónico ingresado es incorrecto";
	
	public static final String EMAIL_RECUPERAR_PASSWORD = "Gasolimetro: Recuperación de contraseña";
	public static final String EMAIL_NUEVA_PASSWORD = "Tu nueva contraseña es: ";
	public static final String EMAIL_VALIDAR_USUARIO = "Gasolimetro: Validación de la cuenta";
	public static final String EMAIL_CREAR_USUARIO = "Gracias por crear una cuenta con nosotros, da click en el link para verificar tu cuenta: ";
	
	
	public static final Long ESTADO_USUARIO_REGISTRADO = 1L;
	public static final Long ESTADO_USUARIO_VERIFICADO = 2L;
	public static final Long ESTADO_USUARIO_DESTACADO = 3L;
	
	public static final String GENERIC_ERROR = "Ocurrio un error inesperado, contacté al administrador";
	public static final String NOT_FOUND = "No encontramos lo que estas buscando";
	
	public static final String MSG_ERROR_SERVER = "El sistema ha experimentado un error, porfavor contacte al administrador";
	public static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";	
}
