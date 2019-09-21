package com.gasolimetro.usuarioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gasolimetro.usuarioservice.entity.Reporte;


@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long>{

}
