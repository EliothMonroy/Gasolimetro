package com.gasolimetro.medicionservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gasolimetro.medicionservice.ConstantesNegocio;
import com.gasolimetro.medicionservice.api_models.AutoSensorModel;
import com.gasolimetro.medicionservice.api_models.SensorInfoModel;
import com.gasolimetro.medicionservice.api_models.SensorModel;
import com.gasolimetro.medicionservice.entity.Automovil;
import com.gasolimetro.medicionservice.entity.ResponseGeneric;
import com.gasolimetro.medicionservice.entity.Sensor;
import com.gasolimetro.medicionservice.repository.AutomovilRepository;
import com.gasolimetro.medicionservice.repository.MedicionRepository;
import com.gasolimetro.medicionservice.repository.SensorRepository;

@RestController
public class SensorController {

	@Autowired
	private SensorRepository sensorRepository;
	
	@Autowired
	private MedicionRepository medicionRepository;
	
	@Autowired
	private AutomovilRepository automovilRepository;
	
	@GetMapping("/sensores/automovil/{idAutomovil}")
	public List<SensorInfoModel> obtenerSensoresAutomovil(@PathVariable Long idAutomovil){
		List<SensorInfoModel> sensoresMediciones = new ArrayList<>();
		
		List<Sensor> sensoresAutomovil = sensorRepository.findSensoresByIdAutomovil(idAutomovil);
		for(Sensor sensor: sensoresAutomovil) {
			SensorInfoModel sensorInfo =  new SensorInfoModel();
			sensorInfo.setSensor(sensor);
			
			sensorInfo.setMediciones(
					medicionRepository.obtenerMedicionesAutoSensor(sensor.getIdSensor(), idAutomovil));
			
			sensoresMediciones.add(sensorInfo);
		}
		return sensoresMediciones; 
	}
	
	@PostMapping("/sensores")
	public ResponseEntity<ResponseGeneric<SensorModel>> registrarSensor(@Valid @RequestBody SensorModel sensorModel){
		ResponseGeneric<SensorModel> response =  new ResponseGeneric<SensorModel>();
		//RN - Buscamos un sensor con el mismo nombre
		Integer existsSensor = sensorRepository.findSensorByNombre(sensorModel.getTxDevice());
		if(existsSensor == ConstantesNegocio.REGISTER_EXISTS) {
			response.setMsg(ConstantesNegocio.MSG_SENSOR_EXISTS);
			return new ResponseEntity<ResponseGeneric<SensorModel>>(response,HttpStatus.OK);
		} else { 
			Sensor sensorSaved = sensorRepository.save(sensorModel.createSensor());
			sensorModel.setIdSensor(sensorSaved.getIdSensor());
			
			response.setMsg(ConstantesNegocio.MSG_SENSOR_CREATED);
			response.setObject(sensorModel);
			
			return new ResponseEntity<ResponseGeneric<SensorModel>>(response,HttpStatus.CREATED);
		}
	}
	
	@DeleteMapping("/sensores/{idSensor}")
	public ResponseEntity<ResponseGeneric<SensorModel>> eliminarSensor(@PathVariable Long idSensor){
		ResponseGeneric<SensorModel> response =  new ResponseGeneric<SensorModel>();
		Sensor sensor= sensorRepository.findSensorById(idSensor);
		if(sensor != null) {
			sensorRepository.delete(sensor);
			response.setMsg(ConstantesNegocio.MSG_SENSOR_DELETED);
			return new ResponseEntity<ResponseGeneric<SensorModel>>(response,HttpStatus.OK);
		} else {
			response.setMsg(ConstantesNegocio.NOT_FOUND);
			return new ResponseEntity<ResponseGeneric<SensorModel>>(response,HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/sensores/usuario/{idUsuario}")
	public List<SensorModel> obtenerSensoresUsuario(@PathVariable Long idUsuario){
		List<SensorModel> sensoresModels = new ArrayList<SensorModel>();
		List<Sensor> sensores = sensorRepository.findSensorByIdUsuario(idUsuario);
		for(Sensor sensor: sensores) {
			SensorModel s =  new SensorModel(
					sensor.getIdSensor(), 
					sensor.getTxDevice(), 
					sensor.getEstadoSensor(), 
					sensor.getUsuario().getIdUsuario());
			sensoresModels.add(s);
		}
		return sensoresModels;
	}
	
	@GetMapping("/mediciones/sensor/{idUsuario}")
	public ResponseEntity<ResponseGeneric<List<AutoSensorModel>>> obtenerIdSensorMedicion(@PathVariable Long idUsuario){
		ResponseGeneric<List<AutoSensorModel>> response = new ResponseGeneric<List<AutoSensorModel>>();
		Integer existsSensor = sensorRepository.countSensoresUsuario(idUsuario);
		
		if(existsSensor > 0) {
			Integer existsSensorAsociado = sensorRepository.countSensoresAsociado(idUsuario);
			if(existsSensorAsociado > 0) {
				List<Long> sensores = sensorRepository.obtenerSensorAsociado(idUsuario);
				List<AutoSensorModel> sensorAutomovilList = new ArrayList<AutoSensorModel>();
				System.out.println("Numero sensores: "+sensores.size());
				for(Long sensor : sensores) {
					AutoSensorModel autoSensorModel = new AutoSensorModel();
					Automovil automovil = automovilRepository.obtenerAutomovilSensorActivo(sensor);
					autoSensorModel.setAutomovil(automovil);
					autoSensorModel.setIdSensor(sensor);
					
					sensorAutomovilList.add(autoSensorModel);
				}
				response.setObject(sensorAutomovilList);
				return new ResponseEntity<ResponseGeneric<List<AutoSensorModel>>>(response, HttpStatus.OK);
			} else {
				response.setMsg(ConstantesNegocio.MSG_NO_ACTIVADO);
				return new ResponseEntity<ResponseGeneric<List<AutoSensorModel>>>(response, HttpStatus.BAD_REQUEST);
			}			
		} else {
			response.setMsg(ConstantesNegocio.MSG_NO_SENSORES);
			return new ResponseEntity<ResponseGeneric<List<AutoSensorModel>>>(response, HttpStatus.NOT_FOUND);
		}
	}
}
