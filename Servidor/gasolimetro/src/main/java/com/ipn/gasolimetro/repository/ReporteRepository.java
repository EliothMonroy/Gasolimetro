package com.ipn.gasolimetro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipn.gasolimetro.entity.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long>{

}
