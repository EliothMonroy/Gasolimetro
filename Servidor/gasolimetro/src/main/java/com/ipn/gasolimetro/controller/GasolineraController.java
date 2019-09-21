package com.ipn.gasolimetro.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ipn.gasolimetro.entity.Gasolinera;
import com.ipn.gasolimetro.repository.GasolineraRepository;

@RestController
public class GasolineraController {
	
	@Autowired
	private GasolineraRepository gasolineraRepository;
	
	@GetMapping("/gasolineras")
	public List<Gasolinera> getGasolineras(){
		return gasolineraRepository.findAll();
	}
	
	@GetMapping("/gasolineras/{idGasolinera}")
	public Resource<Gasolinera> getGasolinera(@PathVariable Long idGasolinera) {
		Optional<Gasolinera> gasolinera = gasolineraRepository.findById(idGasolinera);
		if(!gasolinera.isPresent())
			System.out.println("User Not Found");
		
		Resource<Gasolinera> resource = new Resource<Gasolinera>(gasolinera.get());
		//Implementar HATEOAS
		return resource;
	}
	
	@DeleteMapping("/gasolineras/{idGasolinera}")
	public void deleteGasolinera(@PathVariable Long idGasolinera) {
		Optional<Gasolinera> gasolinera = gasolineraRepository.findById(idGasolinera);
		if(!gasolinera.isPresent())
			System.out.println("Exception user not found");
		else
			gasolineraRepository.delete(gasolinera.get());
	}

	@PostMapping("/gasolineras")
	public ResponseEntity<Object> postGasolinera(@Valid @RequestBody Gasolinera gasolineraNueva){
		Gasolinera gasolinera = gasolineraRepository.save(gasolineraNueva);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(gasolinera.getIdGasolinera())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/gasolineras/{idGasolinera}")
	public ResponseEntity<Object> putGasolinera(@Valid @RequestBody Gasolinera updatedGasolinera, @PathVariable Long idGasolinera){
		Gasolinera gasolinera = new Gasolinera(); 
		Optional<Gasolinera> gasolineraValida = gasolineraRepository.findById(idGasolinera);
		if(!gasolineraValida.isPresent())
			System.out.println("Exception user not found");
		else{
			updatedGasolinera.setIdGasolinera(gasolineraValida.get().getIdGasolinera());
			gasolinera = gasolineraRepository.save(updatedGasolinera);
		}
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("")
				.buildAndExpand(gasolinera.getIdGasolinera())
				.toUri();
		return ResponseEntity.created(location).build();
	}

}
