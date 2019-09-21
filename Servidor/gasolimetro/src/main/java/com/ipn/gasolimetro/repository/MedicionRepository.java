package com.ipn.gasolimetro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipn.gasolimetro.entity.Medicion;

@Repository
public interface MedicionRepository extends JpaRepository<Medicion, Long>{

}
