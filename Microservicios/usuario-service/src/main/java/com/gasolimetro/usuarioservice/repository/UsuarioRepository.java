package com.gasolimetro.usuarioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gasolimetro.usuarioservice.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Query(value = "select count(u.id_usuario) from tus01_usuario as u, tus07_login as l where u.id_usuario=l.id_usuario and l.email = :email", 
			nativeQuery = true)
	public Integer findUsuarioByEmail(@Param("email") String email);
	
	@Query(value = "select * from tus01_usuario where id_usuario = :idUsuario and id_estado = :idEstadoUsuario", 
			nativeQuery = true)
	public Usuario findUsuarioVerification(Long idUsuario, Long idEstadoUsuario);
	
	public Usuario findByIdUsuario(Long idUsuario);
	
	@Query(value = "update tus01_usuario set nombre = :nombre, apellido_paterno = :apellido_paterno, apellido_materno = :apellido_materno, genero = :genero where id_usuario = :id_usuario returning id_usuario", 
			nativeQuery = true)
	public Long updateUsuario(Long id_usuario, String nombre, String apellido_paterno, String apellido_materno, Boolean genero);
}
