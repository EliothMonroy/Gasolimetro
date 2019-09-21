package com.gasolimetro.usuarioservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gasolimetro.usuarioservice.entity.InsigniaU;

@Repository
public interface InsigniaRepository extends JpaRepository<InsigniaU, Long>{
	
	@Query(value = "select * from tus05_insignia_usuario as iu, tus01_usuario_tus05_insignia as ixu, tus01_usuario as u \n" + 
			"where iu.id_insignia_usuario = ixu.id_insignia_usuario and ixu.id_usuario = u.id_usuario and u.id_usuario = :id_usuario", 
			nativeQuery = true)
	public List<InsigniaU> findInsigniasByIdUsuario(Long id_usuario);
}