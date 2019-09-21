package com.gasolimetro.usuarioservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gasolimetro.usuarioservice.entity.Automovil;

@Repository
public interface AutomovilRepository extends JpaRepository<Automovil, Long>{

	@Query(value = "select count(id_automovil) from tus03_automovil where id_usuario = :idUsuario and nombre = :nombre", 
			nativeQuery = true)
	public Integer findAutomovilByNombre(String nombre, Long idUsuario);
	
	@Query(value = "select * from tus03_automovil where id_usuario = :idUsuario", 
			nativeQuery = true)
	public List<Automovil> findAutomovilesByIdUsuario(Long idUsuario);
	
	@Query(value = "select * from tus03_automovil where id_automovil = :idAutomovil", 
			nativeQuery = true)
	public Automovil findAutomovilById(Long idAutomovil);
	
	@Query(value="update tus03_automovil set nombre = :nombre, marca = :marca, modelo = :modelo, capacidad_litros = :capacidad where id_automovil = :id_automovil returning id_automovil",
			nativeQuery = true)
	public Long updateAutomovil(Long id_automovil, String nombre, String marca, String modelo, Double capacidad);
}
