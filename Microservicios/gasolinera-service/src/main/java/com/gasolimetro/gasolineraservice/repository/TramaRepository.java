package com.gasolimetro.gasolineraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gasolimetro.gasolineraservice.entity.Trama;

@Repository
public interface TramaRepository  extends JpaRepository<Trama, Long>{

}
