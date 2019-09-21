package com.gasolimetro.medicionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gasolimetro.medicionservice.entity.Trama;

@Repository
public interface TramaRepository  extends JpaRepository<Trama, Long>{

}
