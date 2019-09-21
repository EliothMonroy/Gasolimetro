package com.gasolimetro.medicionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gasolimetro.medicionservice.entity.Automovil;

public interface AutomovilRepository extends JpaRepository<Automovil, Long> {
	@Query(value = "select auto.* from tus03_automovil as auto, tus09_automovil_sensor as axs \n" + 
			"where auto.id_automovil = axs.id_automovil and axs.id_sensor = :idSensor",
			nativeQuery = true)
	public Automovil obtenerAutomovilSensorActivo(Long idSensor);
}
