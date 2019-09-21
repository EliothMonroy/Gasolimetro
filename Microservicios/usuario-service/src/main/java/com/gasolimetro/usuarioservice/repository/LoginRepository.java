package com.gasolimetro.usuarioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gasolimetro.usuarioservice.entity.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long>{
	public abstract Login findByEmailAndPassword(String email, String password);
	public abstract Login findByEmail(String email);
	
	@Query(value="update tus07_login set email = :email, password = :password where id_usuario = :id_usuario returning id_login", 
		nativeQuery = true)
	public Long updateLogin(String email, String password, Long id_usuario);
	
	@Query(value="select * from tus07_login where id_usuario = :id_usuario", 
			nativeQuery = true)
	public Login findByIdUsuario(Long id_usuario);
}
