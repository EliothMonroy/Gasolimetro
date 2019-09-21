package com.ipn.gasolimetro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipn.gasolimetro.entity.Gasolinera;

@Repository
public interface GasolineraRepository extends JpaRepository<Gasolinera, Long>{

}
