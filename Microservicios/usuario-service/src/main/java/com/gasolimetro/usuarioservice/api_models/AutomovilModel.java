package com.gasolimetro.usuarioservice.api_models;

import com.gasolimetro.usuarioservice.entity.Automovil;
import com.gasolimetro.usuarioservice.entity.Usuario;

public class AutomovilModel {
	
	private Long idAutomovil;
	private String nombre;
	private String marca;
	private String modelo;
	private Double capacidadLitros;
	private Long idUsuario;
	
	public AutomovilModel() {
		super();
	}

	public AutomovilModel(Long idAutomovil, String nombre, String marca, String modelo, Double capacidadLitros,
			Long idUsuario) {
		super();
		this.idAutomovil = idAutomovil;
		this.nombre = nombre;
		this.marca = marca;
		this.modelo = modelo;
		this.capacidadLitros = capacidadLitros;
		this.idUsuario = idUsuario;
	}
	
	public Automovil createAutomovil(Usuario usuario) {
		Automovil automovil = new Automovil();
		automovil.setNombre(this.nombre);
		automovil.setMarca(this.marca);
		automovil.setModelo(this.modelo);
		automovil.setCapacidadLitros(this.capacidadLitros);
		automovil.setUsuario(usuario);
		
		return automovil;
	}

	public Long getIdAutomovil() {
		return idAutomovil;
	}

	public void setIdAutomovil(Long idAutomovil) {
		this.idAutomovil = idAutomovil;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Double getCapacidadLitros() {
		return capacidadLitros;
	}

	public void setCapacidadLitros(Double capacidadLitros) {
		this.capacidadLitros = capacidadLitros;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public String toString() {
		return "AutomovilModel [idAutomovil=" + idAutomovil + ", nombre=" + nombre + ", marca=" + marca + ", modelo="
				+ modelo + ", capacidadLitros=" + capacidadLitros + ", idUsuario=" + idUsuario + "]";
	}
	
}
