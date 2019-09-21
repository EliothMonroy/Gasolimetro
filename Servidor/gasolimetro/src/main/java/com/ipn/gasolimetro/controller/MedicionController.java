package com.ipn.gasolimetro.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ipn.gasolimetro.entity.Trama;
import com.ipn.gasolimetro.repository.TramaRepository;

@RestController
public class MedicionController {
	
	@Autowired
	private TramaRepository tramaRepository;

	@PostMapping("/tramas")
	public ResponseEntity<Object> postUsuario(@Valid @RequestBody Trama trama){		
		Trama tramaSaved = tramaRepository.save(trama);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(tramaSaved.getIdTrama())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	
}
