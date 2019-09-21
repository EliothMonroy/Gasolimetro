package com.gasolimetro.gasolineraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import com.gasolimetro.gasolineraservice.entity.Gasolinera;

@Repository
public interface GasolineraRepository extends JpaRepository<Gasolinera, Long>{
	
	@Query(value = "select count(gasolinera.id_gasolinera) from tcl01_gasolinera as gasolinera, tcl02_ubicacion as ubicacion \n" + 
			"where gasolinera.id_gasolinera  = ubicacion.id_gasolinera \n" + 
			"and CAST(ubicacion.latitud as TEXT) like :latitud \n" + 
			"and CAST(ubicacion.longitud as TEXT) like :longitud", 
			nativeQuery = true)
	public Integer findGasolineraByUbicacion(String latitud, String longitud);
	
	@Query(value = "select gasolinera.* from tcl01_gasolinera as gasolinera, tcl02_ubicacion as ubicacion \n" + 
			"where gasolinera.id_gasolinera  = ubicacion.id_gasolinera \n" + 
			"and CAST(ubicacion.latitud as TEXT) like :latitud \n" + 
			"and CAST(ubicacion.longitud as TEXT) like :longitud ", 
			nativeQuery = true)
	public Gasolinera obtenerMapaGasolinera(String latitud, String longitud);
	
	public Gasolinera findByIdGasolinera(Long idGasolinera);
	
	@Query(value = "select count(id_medicion) from tme02_medicion where id_gasolinera = :idGasolinera", 
			nativeQuery = true)
	public Integer obtenerNumeroMediciones(Long idGasolinera);
	
	@Query(value = "select * from tcl01_gasolinera where id_gasolinera = :idGasolinera", 
			nativeQuery = true)
	public Gasolinera findGasolineraById(Long idGasolinera);
	
}
