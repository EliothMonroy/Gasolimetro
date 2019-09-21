package com.ipn.gasolimetro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipn.gasolimetro.entity.Automovil;

@Repository
public interface AutomovilRepository extends JpaRepository<Automovil, Long>{

}
