package com.ipn.gasolimetro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipn.gasolimetro.entity.Trama;

@Repository
public interface TramaRepository  extends JpaRepository<Trama, Long>{

}
