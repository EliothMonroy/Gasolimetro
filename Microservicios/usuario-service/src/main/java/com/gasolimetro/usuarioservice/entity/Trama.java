package com.gasolimetro.usuarioservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tus10_trama")
public class Trama implements Serializable{

	private static final long serialVersionUID = -3579126164437283849L;

	@Id
	@SequenceGenerator(name = "tus10_trama_seq", sequenceName = "tus10_trama_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tus10_trama_seq")
	@Column(name="id_trama")
	private Long idTrama;
	
	@NotNull
	@Column(name="trama")
	private String trama;

	public Trama() {
		super();
	}

	public Trama(Long idTrama, @NotNull String trama) {
		super();
		this.idTrama = idTrama;
		this.trama = trama;
	}

	public Long getIdTrama() {
		return idTrama;
	}

	public void setIdTrama(Long idTrama) {
		this.idTrama = idTrama;
	}

	public String getTrama() {
		return trama;
	}

	public void setTrama(String trama) {
		this.trama = trama;
	}

	@Override
	public String toString() {
		return "Trama [idTrama=" + idTrama + ", trama=" + trama + "]";
	}
	
	
}
