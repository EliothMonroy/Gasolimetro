package com.gasolimetro.medicionservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gasolimetro.medicionservice.entity.Medicion;

@Repository
public interface MedicionRepository extends JpaRepository<Medicion, Long>{
	
	@Query(value = "select * from tus01_usuario where id_usuario = :idUsuario and id_estado = :idEstadoUsuario", 
			nativeQuery = true)
	public List<Medicion> obtenerMedicionesUsuario();
	
	@Query(value = "select * from tme02_medicion as med where med.id_sensor = :idSensor and med.id_automovil = :idAutomovil", 
			nativeQuery = true)
	public List<Medicion> obtenerMedicionesAutoSensor(Long idSensor, Long idAutomovil);
	
	@Query(value = "select id_sensor from tme01_sensor where id_estado = 1 and id_usuario = :idUsuario", 
			nativeQuery = true)
	public Integer obtenerIdSensor();
		
}
