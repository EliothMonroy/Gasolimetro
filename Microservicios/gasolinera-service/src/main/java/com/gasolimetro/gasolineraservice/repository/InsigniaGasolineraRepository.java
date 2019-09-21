package com.gasolimetro.gasolineraservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gasolimetro.gasolineraservice.entity.Insignia;

public interface InsigniaGasolineraRepository extends JpaRepository<Insignia, Long> {

	@Query(value = " select insignia.* from tcl03_insignia_gasolinera as insignia, tcl03_insignia_gasolinera_tcl01_gasolinera as ixg \n" + 
			"where insignia.id_insignia_gasolinera = ixg.insignia_id_insignia_gasolinera and \n" + 
			"ixg.gasolinera_id_gasolinera = :idGasolinera", 
			nativeQuery = true)
	public List<Insignia> findByIdGasolinera(Long idGasolinera);
}