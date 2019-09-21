package com.gasolimetro.gasolineraservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tus07_login")
public class Login implements Serializable{

	private static final long serialVersionUID = -6528192665103647080L;
	
	@Id
	@SequenceGenerator(name = "tus07_login_seq", sequenceName = "tus07_login_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tus07_login_seq")
	@Column(name="id_login")
	private Long idLogin;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@JsonIgnore
	@OneToOne
    @JoinColumn(name = "id_usuario")
	@LazyCollection(LazyCollectionOption.FALSE)
    private Usuario usuario;

	public Login() {
		super();
	}

	public Login(Long idLogin, String email, String password, Usuario usuario) {
		super();
		this.idLogin = idLogin;
		this.email = email;
		this.password = password;
		this.usuario = usuario;
	}

	public Long getIdLogin() {
		return idLogin;
	}

	public void setIdLogin(Long idLogin) {
		this.idLogin = idLogin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Login [idLogin=" + idLogin + ", email=" + email + ", password=" + password + ", usuario=" + usuario
				+ "]";
	}
	
}
