package com.gasolimetro.usuarioservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gasolimetro.usuarioservice.entity.PanelControl;
import com.gasolimetro.usuarioservice.repository.InsigniaRepository;

@RestController
public class PanelController {
	
	@Autowired
	private InsigniaRepository insigniaRepository;
	
	@GetMapping("/dashboard/{idUsuario}")
	public PanelControl consultarPanelUsuario(@PathVariable Long idUsuario) {
		PanelControl panelControl = new PanelControl();
		
		//Obteniendo insignias de la BD
		panelControl.setInsignias(insigniaRepository.findInsigniasByIdUsuario(idUsuario));
		
		//Obtiendo mediciones a través de un Cliente Feign
		
		return panelControl;
	}
}
