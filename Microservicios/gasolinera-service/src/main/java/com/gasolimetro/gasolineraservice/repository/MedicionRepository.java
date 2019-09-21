package com.gasolimetro.gasolineraservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gasolimetro.gasolineraservice.entity.Medicion;


public interface MedicionRepository extends JpaRepository<Medicion, Long>{
	
	@Query(value = "select * from tme02_medicion where id_gasolinera = :idGasolinera", 
			nativeQuery = true)
	public List<Medicion> findByIdGasolinera(Long idGasolinera);
}
