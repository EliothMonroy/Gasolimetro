package com.gasolimetro.medicionservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gasolimetro.medicionservice.entity.Sensor;


@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long>{
	@Query(value = "select * from tme01_sensor as sensor, tus09_automovil_sensor as axs, tus03_automovil as aut\n" + 
			"where sensor.id_sensor = axs.id_sensor and axs.id_automovil = aut.id_automovil and aut.id_automovil = :idAutomovil", 
			nativeQuery = true)
	public List<Sensor> findSensoresByIdAutomovil(Long idAutomovil);
	
	@Query(value = "select count(id_sensor) from tme01_sensor where tx_device = :nombre", 
			nativeQuery = true)
	public Integer findSensorByNombre(String nombre);
	
	@Query(value = "select * from tme01_sensor where id_sensor = :idSensor", 
			nativeQuery = true)
	public Sensor findSensorById(Long idSensor);
	
	@Query(value = "select * from tme01_sensor where id_usuario = :idUsuario", 
			nativeQuery = true)
	public List<Sensor> findSensorByIdUsuario(Long idUsuario);
	
	@Query(value = "select count(id_sensor) from tme01_sensor where id_usuario = :idUsuario", 
			nativeQuery = true)
	public Integer countSensoresUsuario(Long idUsuario);
	
	@Query(value = "select count(*) from tus09_automovil_sensor as axs, tme01_sensor as sensor\n" + 
			"where axs.id_sensor = sensor.id_sensor and sensor.id_estado = 2 and axs.activo = 1 and sensor.id_usuario = :idUsuario", 
			nativeQuery = true)
	public Integer countSensoresAsociado(Long idUsuario);
	
	@Query(value = "select axs.id_sensor from tus09_automovil_sensor as axs, tme01_sensor as sensor \n" + 
			"where axs.id_sensor = sensor.id_sensor and sensor.id_estado = 2 and axs.activo = 1 and sensor.id_usuario = :idUsuario", 
			nativeQuery = true)
	public List<Long> obtenerSensorAsociado(Long idUsuario);
	
}
