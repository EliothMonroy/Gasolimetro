package com.gasolimetro.usuarioservice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tus03_automovil")
public class Automovil implements Serializable{

	private static final long serialVersionUID = 4624799845000315566L;

	@Id
	@SequenceGenerator(name = "tus03_automovil_seq", sequenceName = "tus03_automovil_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tus03_automovil_seq")
	@Column(name="id_automovil")
	private Long idAutomovil;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="marca")
	private String marca;
	
	@Column(name="modelo")
	private String modelo;
	
	@Column(name="capacidad_litros")
	private Double capacidadLitros;
	
	@JsonIgnore
	@OneToMany(mappedBy = "id.automovil")
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<AutomovilSensor> sensoresAutomovil;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_usuario")
	@LazyCollection(LazyCollectionOption.TRUE)
	private Usuario usuario;

	public Automovil() {
		super();
	}

	public Automovil(Long idAutomovil, String nombre, String marca, String modelo, Double capacidadLitros,
			List<AutomovilSensor> sensoresAutomovil, Usuario usuario) {
		super();
		this.idAutomovil = idAutomovil;
		this.nombre = nombre;
		this.marca = marca;
		this.modelo = modelo;
		this.capacidadLitros = capacidadLitros;
		this.sensoresAutomovil = sensoresAutomovil;
		this.usuario = usuario;
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

	public List<AutomovilSensor> getSensoresAutomovil() {
		return sensoresAutomovil;
	}

	public void setSensoresAutomovil(List<AutomovilSensor> sensoresAutomovil) {
		this.sensoresAutomovil = sensoresAutomovil;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Automovil [idAutomovil=" + idAutomovil + ", nombre=" + nombre + ", marca=" + marca + ", modelo="
				+ modelo + ", capacidadLitros=" + capacidadLitros + ", sensoresAutomovil=" + sensoresAutomovil
				+ ", usuario=" + usuario + "]";
	}

}
