package com.gasolimetro.usuarioservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gasolimetro.usuarioservice.ConstantesNegocio;
import com.gasolimetro.usuarioservice.api_models.AutomovilInfoModel;
import com.gasolimetro.usuarioservice.api_models.AutomovilModel;
import com.gasolimetro.usuarioservice.entity.Automovil;
import com.gasolimetro.usuarioservice.entity.ResponseGeneric;
import com.gasolimetro.usuarioservice.entity.Usuario;
import com.gasolimetro.usuarioservice.proxys.MedicionProxy;
import com.gasolimetro.usuarioservice.repository.AutomovilRepository;
import com.gasolimetro.usuarioservice.repository.UsuarioRepository;

@RestController
public class AutomovilController {
	
	@Autowired
	private AutomovilRepository automovilRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private MedicionProxy medicionProxy;
	
	@PostMapping("/automoviles")
	public ResponseEntity<ResponseGeneric<Automovil>> registrarUsuario(@Valid @RequestBody AutomovilModel automovilModel){
		ResponseGeneric<Automovil> response = new ResponseGeneric<Automovil>();
		//RN -Buscamos un automóvil con el mismo nombre 
		
		Integer existsAutomovil = automovilRepository.findAutomovilByNombre(automovilModel.getNombre(), automovilModel.getIdUsuario()); 
		if(existsAutomovil == ConstantesNegocio.REGISTER_EXISTS) {
			response.setMsg(ConstantesNegocio.MSG_AUTOMOVIL_EXISTS);
			return new ResponseEntity<ResponseGeneric<Automovil>>(response,HttpStatus.OK);
		} else {
			//Creación de un automovil
			Usuario usuario = usuarioRepository.getOne(automovilModel.getIdUsuario());
			Automovil automovil = automovilModel.createAutomovil(usuario);
			
			//Guardamos un automóvil
			automovil = automovilRepository.save(automovil);
			response.setMsg(ConstantesNegocio.MSG_AUTOMOVIL_CREATE);
			response.setObject(automovil);
			return new ResponseEntity<ResponseGeneric<Automovil>>(response,HttpStatus.CREATED);
		}
	}
	
	@PutMapping("/automoviles")
	public ResponseEntity<ResponseGeneric<AutomovilModel>> actualizarUsuario(@Valid @RequestBody AutomovilModel automovilModel){
		ResponseGeneric<AutomovilModel> response = new ResponseGeneric<AutomovilModel>();
		Automovil automovil = automovilRepository.findAutomovilById(automovilModel.getIdAutomovil());
		if(automovil != null) {
			automovilRepository.updateAutomovil(
					automovilModel.getIdAutomovil(), 
					automovilModel.getNombre(), 
					automovilModel.getMarca(), 
					automovilModel.getModelo(), 
					automovilModel.getCapacidadLitros());
			
			response.setMsg(ConstantesNegocio.MSG_AUTOMOVIL_EDIT);
			response.setObject(automovilModel);
			return new ResponseEntity<ResponseGeneric<AutomovilModel>>(response,HttpStatus.OK); 
		} else {
			response.setMsg(ConstantesNegocio.NOT_FOUND);
			return new ResponseEntity<ResponseGeneric<AutomovilModel>>(response,HttpStatus.NOT_FOUND); 
		}
	}
	
	@GetMapping("/automoviles/usuario/{idUsuario}")
	public List<Automovil> obtenerAutomovilesCliente(@PathVariable Long idUsuario){
		return automovilRepository.findAutomovilesByIdUsuario(idUsuario);
	}
		
	@GetMapping("/automovil/{idAutomovil}")
	public Automovil obtenerAutomovilById(@PathVariable Long idAutomovil){
		return automovilRepository.findAutomovilById(idAutomovil);
	}
	
	@GetMapping("/automoviles/{idAutomovil}")
	public ResponseEntity<ResponseGeneric<AutomovilInfoModel>> obtenerAutomovil(@PathVariable Long idAutomovil){
		ResponseGeneric<AutomovilInfoModel> response =  new ResponseGeneric<AutomovilInfoModel>();
		Automovil automovil = automovilRepository.findAutomovilById(idAutomovil);
		if(automovil != null) {
			AutomovilModel automovilModel = new AutomovilModel(
					automovil.getIdAutomovil(), automovil.getNombre(), automovil.getMarca(),
					automovil.getModelo(), automovil.getCapacidadLitros(), 
					automovil.getUsuario().getIdUsuario());
			
			AutomovilInfoModel model = new AutomovilInfoModel();
			model.setAutomovilModel(automovilModel);
			model.setSensoresInfo(medicionProxy.obtenerInfoSensores(idAutomovil));
			
			response.setObject(model);
			return new ResponseEntity<ResponseGeneric<AutomovilInfoModel>>(response,HttpStatus.OK);
		} else {
			response.setMsg(ConstantesNegocio.NOT_FOUND);
			return new ResponseEntity<ResponseGeneric<AutomovilInfoModel>>(response,HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/automoviles/{idAutomovil}")
	public ResponseEntity<ResponseGeneric<AutomovilInfoModel>> eliminarAutomovil(@PathVariable Long idAutomovil){
		ResponseGeneric<AutomovilInfoModel> response =  new ResponseGeneric<AutomovilInfoModel>();
		Automovil automovil = automovilRepository.findAutomovilById(idAutomovil);
		if(automovil != null) {
			automovilRepository.delete(automovil);
			response.setMsg(ConstantesNegocio.MSG_AUTOMOVIL_DELETE);
			return new ResponseEntity<ResponseGeneric<AutomovilInfoModel>>(response,HttpStatus.OK);
		} else {
			response.setMsg(ConstantesNegocio.NOT_FOUND);
			return new ResponseEntity<ResponseGeneric<AutomovilInfoModel>>(response,HttpStatus.NOT_FOUND);
		}
	}
}
