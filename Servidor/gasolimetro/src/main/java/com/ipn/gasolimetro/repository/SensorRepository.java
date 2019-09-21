package com.ipn.gasolimetro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipn.gasolimetro.entity.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long>{

}
