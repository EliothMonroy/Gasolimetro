package com.gasolimetro.medicionservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gasolimetro.medicionservice.ConstantesNegocio;
import com.gasolimetro.medicionservice.api_models.MedicionModel;
import com.gasolimetro.medicionservice.entity.Automovil;
import com.gasolimetro.medicionservice.entity.AutomovilSensor;
import com.gasolimetro.medicionservice.entity.AutomovilSensorPk;
import com.gasolimetro.medicionservice.entity.EstadoMedicion;
import com.gasolimetro.medicionservice.entity.Gasolinera;
import com.gasolimetro.medicionservice.entity.Medicion;
import com.gasolimetro.medicionservice.entity.ResponseGeneric;
import com.gasolimetro.medicionservice.entity.Sensor;
import com.gasolimetro.medicionservice.repository.MedicionRepository;

@RestController
public class MedicionController {
	
	@Autowired
	private MedicionRepository medicionRepository;
	
	@PostMapping("/mediciones")
	public ResponseEntity<ResponseGeneric<Medicion>> agregarMedicion(@RequestBody MedicionModel medicionModel){		
		System.out.println(medicionModel);
		ResponseGeneric<Medicion> response = new ResponseGeneric<Medicion>();
		//RN - Revisar los posibles problemas con los que podría contar el registro de mediciones
		Medicion medicion = new Medicion();
		
		//Gasolinera
		Gasolinera gasolinera = new Gasolinera();
		gasolinera.setIdGasolinera(medicionModel.getIdGasolinera());
		medicion.setGasolinera(gasolinera);
		
		//Estado Medicion
		EstadoMedicion estadoMedicion = new EstadoMedicion();
		estadoMedicion.setIdEstado(ConstantesNegocio.ESTADO_SENSOR_REGISTRADO);
		medicion.setEstadoMedicion(estadoMedicion);
		
		//Automovil - Sensor
		AutomovilSensor automovilSensor = new AutomovilSensor();
		AutomovilSensorPk pk = new AutomovilSensorPk();
		
		Automovil automovil = new Automovil();
		automovil.setIdAutomovil(medicionModel.getIdAutomovil());
		pk.setAutomovil(automovil);
		
		Sensor sensor = new Sensor();
		sensor.setIdSensor(medicionModel.getIdSensor());
		pk.setSensor(sensor);
		
		automovilSensor.setId(pk);
		medicion.setAutomovilSensor(automovilSensor);
		
		//Datos - Medicion 
		medicion.setFinMedicion(medicionModel.getMedicion().getFinMedicion());
		medicion.setLitrosSolicitados(medicionModel.getMedicion().getLitrosSolicitados());
		medicion.setLitrosIngresados(medicionModel.getMedicion().getLitrosIngresados());
		medicion.setPrecioSolicitado(medicionModel.getMedicion().getPrecioSolicitado());
		medicion.setPrecioPagado(medicionModel.getMedicion().getPrecioPagado());
		medicion.setBomba(medicionModel.getMedicion().getBomba());
		
		
		medicionRepository.save(medicion);
		response.setMsg(ConstantesNegocio.MSG_MEDICION_CREATED);
		return new ResponseEntity<ResponseGeneric<Medicion>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/mediciones")
	public List<Medicion> obtenerMediciones(){
		return medicionRepository.findAll();
	}
	
}
