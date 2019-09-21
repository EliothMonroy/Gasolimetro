package com.gasolimetro.gasolineraservice.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.validation.Valid;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gasolimetro.gasolineraservice.ConstantesNegocio;
import com.gasolimetro.gasolineraservice.entity.Automovil;
import com.gasolimetro.gasolineraservice.entity.AutomovilSensor;
import com.gasolimetro.gasolineraservice.entity.AutomovilSensorPk;
import com.gasolimetro.gasolineraservice.entity.EstadoMedicion;
import com.gasolimetro.gasolineraservice.entity.Gasolinera;
import com.gasolimetro.gasolineraservice.entity.Medicion;
import com.gasolimetro.gasolineraservice.entity.ResponseGeneric;
import com.gasolimetro.gasolineraservice.entity.Sensor;
import com.gasolimetro.gasolineraservice.entity.Ubicacion;
import com.gasolimetro.gasolineraservice.models.GasolineraConsultaModel;
import com.gasolimetro.gasolineraservice.models.GasolineraModel;
import com.gasolimetro.gasolineraservice.models.ResultModel;
import com.gasolimetro.gasolineraservice.repository.GasolineraRepository;
import com.gasolimetro.gasolineraservice.repository.InsigniaGasolineraRepository;
import com.gasolimetro.gasolineraservice.repository.MedicionRepository;
import com.gasolimetro.gasolineraservice.repository.UbicacionRepository;

@RestController
public class GasolineraController {
	
	@Autowired
	private GasolineraRepository gasolineraRepository;
	
	@Autowired
	private UbicacionRepository ubicacionRepository;
	
	@Autowired
	private MedicionRepository medicionRepository;
	
	@Autowired
	private InsigniaGasolineraRepository insigniGasolineraRepository;
	
	@Autowired
	private Environment env;
	
	@Autowired
    ResourceLoader resourceLoader;
	
	@GetMapping("/gasolineras")
	public List<Gasolinera> getGasolineras(){
		List<Gasolinera> gasolineras = gasolineraRepository.findAll();
		for(Gasolinera g: gasolineras) {
			g.setPuerto(Integer.parseInt(env.getProperty("local.server.port")));
		}
		return gasolineras;
	}
	
	@GetMapping("/gasolineras/xml")
	public ResponseEntity<ResponseGeneric<String>> cargarGasolineras(){
		ResponseGeneric<String> response = new ResponseGeneric<String>();
		//Acceso al XML
		Resource resource = resourceLoader.getResource("classpath:gasolineras_places.xml");			
		try {
			File gasolinerasXML = resource.getFile();
			
			//Creacion del nuestro DOM a partir del XML
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(gasolinerasXML);
			
			doc.getDocumentElement().normalize();
			
			//Recorriendo el XML
			NodeList nodosGasolineras = doc.getElementsByTagName("place");
			List<Gasolinera> gasolineras =  new ArrayList<Gasolinera>();
			List<Ubicacion> ubicaciones =  new ArrayList<Ubicacion>();
			int i;
			for (i = 0; i < nodosGasolineras.getLength(); i++) {
				Node nodoGasolinera = nodosGasolineras.item(i);
				
				if (nodoGasolinera.getNodeType() == Node.ELEMENT_NODE) {

					Element gasolineraElement = (Element) nodoGasolinera;
					
					String latitud = gasolineraElement.getElementsByTagName("y").item(0).getTextContent();
					
					String longitud = gasolineraElement.getElementsByTagName("x").item(0).getTextContent();
					
					if(Double.parseDouble(latitud) > 19 && Double.parseDouble(latitud) < 21
							&& Double.parseDouble(longitud) < -99 && Double.parseDouble(longitud) > -100) {
						
						//Creación de la gasolinera en nuestra BD
						Gasolinera gasolinera = new Gasolinera();
						gasolinera.setTxGasolinera(gasolineraElement.getElementsByTagName("name")
								.item(0).getTextContent());
						
						gasolinera.setCalificacion(obtenerCalificacionInicial());
						
						Ubicacion ubicacion = new Ubicacion();
						ubicacion.setGasolinera(gasolinera); 
						
						if(latitud.length() >= 8)
							ubicacion.setLatitud(latitud.substring(0, 7));
						else
							ubicacion.setLatitud(latitud);
						
						if(longitud.length() >= 9)
							ubicacion.setLongitud(longitud.substring(0, 8));
						else
							ubicacion.setLongitud(longitud);
						
						gasolineras.add(gasolinera);
						ubicaciones.add(ubicacion);
					}
				}
			}
			gasolineraRepository.saveAll(gasolineras);
			ubicacionRepository.saveAll(ubicaciones);

			response.setMsg(ConstantesNegocio.MSG_CREATE_GASOLINERAS);
			return new ResponseEntity<ResponseGeneric<String>>(response,HttpStatus.OK);
		} catch (IOException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseGeneric<String>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@PostMapping("/gasolineras")
	public ResponseEntity<ResponseGeneric<Gasolinera>> registrarUsuario(@Valid @RequestBody Gasolinera gasolineraNueva){
		ResponseGeneric<Gasolinera> response = new ResponseGeneric<Gasolinera>();
		
		String latitud = gasolineraNueva.getUbicacion().getLatitud() + "%";
		String longitud = gasolineraNueva.getUbicacion().getLongitud() + "%";

		//RN - Buscamos una gasolinera con la misma ubicacion
		Integer existsGasolinera = gasolineraRepository.findGasolineraByUbicacion(latitud, longitud);
		if(existsGasolinera > ConstantesNegocio.ZERO){
			response.setMsg(ConstantesNegocio.MSG_GASOLINERA_EXISTS);
			return new ResponseEntity<ResponseGeneric<Gasolinera>>(response, HttpStatus.OK);
		}else { 
			Gasolinera g = gasolineraRepository.saveAndFlush(gasolineraNueva);
			
			Ubicacion ubicacion = gasolineraNueva.getUbicacion();
			ubicacion.setGasolinera(g);
			ubicacionRepository.save(ubicacion);
			
			response.setMsg(ConstantesNegocio.MSG_GASOLINERA_CREATED);
			
			return new ResponseEntity<ResponseGeneric<Gasolinera>>(response, HttpStatus.CREATED);
		}
	}
	
	@DeleteMapping("/gasolineras/{idGasolinera}")
	public ResponseEntity<ResponseGeneric<Gasolinera>> deleteGasolinera(@PathVariable Long idGasolinera) {
		ResponseGeneric<Gasolinera> response =  new ResponseGeneric<Gasolinera>();
		Gasolinera gasolinera = gasolineraRepository.findByIdGasolinera(idGasolinera);
		if(gasolinera != null) {
			gasolineraRepository.delete(gasolinera);
			response.setMsg(ConstantesNegocio.MSG_GASOLINERA_DELETED);
			return new ResponseEntity<ResponseGeneric<Gasolinera>>(response, HttpStatus.OK);
		} else {
			response.setMsg(ConstantesNegocio.NOT_FOUND);
			return new ResponseEntity<ResponseGeneric<Gasolinera>>(response,HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/gasolineras/{idGasolinera}")
	public ResponseEntity<ResponseGeneric<GasolineraModel>> consultarGasolinera(@PathVariable Long idGasolinera){
		ResponseGeneric<GasolineraModel> response  = new ResponseGeneric<GasolineraModel>();
		GasolineraModel gasolineraModel = new GasolineraModel();
		Gasolinera gasolinera = gasolineraRepository.findByIdGasolinera(idGasolinera);
		if(gasolinera != null) {
			//Obtener el numero de mediciones que se han realizado por gasolinera
			gasolineraModel.setGasolinera(gasolinera);
			gasolineraModel.setNumeroMediciones(
					gasolineraRepository.obtenerNumeroMediciones(idGasolinera));
			response.setObject(gasolineraModel);
			gasolineraModel.setInsigniasGasolinera(
					insigniGasolineraRepository.findByIdGasolinera(idGasolinera));
			return new ResponseEntity<ResponseGeneric<GasolineraModel>>(response, HttpStatus.OK);
		} else {
			response.setMsg(ConstantesNegocio.NOT_FOUND);
			return new ResponseEntity<ResponseGeneric<GasolineraModel>>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/gasolineras/mapa")
	public List<GasolineraConsultaModel> obtenerGasolinerasMapa(@RequestBody ResultModel resultModel){
		List<GasolineraConsultaModel> gasolinerasCalificadas = new ArrayList<GasolineraConsultaModel>();
		List<GasolineraConsultaModel> gasolinerasCercanas = resultModel.getGasolinerasCercanas();
		List<String> nombresGasolinera = new ArrayList<String>();
		for(GasolineraConsultaModel gasolineraConsulta : gasolinerasCercanas) {			
			
			GasolineraConsultaModel gasolineraCalificada = new GasolineraConsultaModel();
			gasolineraCalificada.setLat(gasolineraConsulta.getLat());
			gasolineraCalificada.setLon(gasolineraConsulta.getLon());
			
			gasolineraCalificada.setPlace_id(gasolineraConsulta.getPlace_id());
			
			Gasolinera g = gasolineraRepository.obtenerMapaGasolinera(
					gasolineraConsulta.getLat().substring(0, 6)+"%", 
					gasolineraConsulta.getLon().substring(0, 7)+"%");
			
			if(g != null) {
				String nombreGasolinera = g.getTxGasolinera();
				Boolean gasolineraExists = false;
				if(nombresGasolinera.size() > 0) {
					for(String nombre : nombresGasolinera) {
						if(nombre.equals(nombreGasolinera)) {
							gasolineraExists = true;
						}
					}
				}else { 
					nombresGasolinera.add(nombreGasolinera);
				}
				
				if(!gasolineraExists) {
					gasolineraCalificada.setNombreGasolinera(nombreGasolinera);
					gasolineraCalificada.setGasolineraId(g.getIdGasolinera());
					gasolineraCalificada.setCalificacion(g.getCalificacion());
					gasolineraCalificada.setExists(Boolean.TRUE);	
					nombresGasolinera.add(nombreGasolinera);
					gasolinerasCalificadas.add(gasolineraCalificada);
				}
			} else { 
				gasolineraCalificada.setExists(Boolean.FALSE);
				gasolinerasCalificadas.add(gasolineraCalificada);
			}
			
		}
		
		return gasolinerasCalificadas;
	}
	
	@GetMapping("/gasolineras/simular")
	private ResponseEntity<ResponseGeneric<String>> cargarMediciones(){
		ResponseGeneric<String> response = new ResponseGeneric<String>();
		Random rand = new Random(); 
		
		for(int i = 1; i < 1264; i++) {
			Medicion medicion = new Medicion();
			medicion.setFinMedicion(new Date());
			
			int litros = ThreadLocalRandom.current().nextInt(20, 40);			
			medicion.setLitrosSolicitados(new Double(litros));
						
			double error = rand.nextDouble()*3;
			double litrosIngresados = litros-error;
			medicion.setLitrosIngresados(litrosIngresados);
			
			//Gasolinera
			Gasolinera gasolinera = new Gasolinera();
			gasolinera.setIdGasolinera(new Long(i));
			medicion.setGasolinera(gasolinera);
			
			//Estado Medicion
			EstadoMedicion estadoMedicion = new EstadoMedicion();
			estadoMedicion.setIdEstado(1L);
			medicion.setEstadoMedicion(estadoMedicion);
			
			//Automovil - Sensor
			AutomovilSensor automovilSensor = new AutomovilSensor();
			AutomovilSensorPk pk = new AutomovilSensorPk();
			
			Automovil automovil = new Automovil();
			automovil.setIdAutomovil(1L);
			pk.setAutomovil(automovil);
			
			Sensor sensor = new Sensor();
			sensor.setIdSensor(1L);
			pk.setSensor(sensor);
			
			automovilSensor.setId(pk);
			medicion.setAutomovilSensor(automovilSensor);
			
			medicionRepository.save(medicion);
		}
		
		response.setMsg(ConstantesNegocio.MSG_MEDICIONES_PRUEBA);
		return new ResponseEntity<ResponseGeneric<String>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/gasolineras/media")
	private ResponseEntity<ResponseGeneric<String>> calcularMedia(){
		ResponseGeneric<String> response = new ResponseGeneric<String>();
		List<Gasolinera> gasolineras = gasolineraRepository.findAll();
		for(Gasolinera g : gasolineras) {
			Double promedio = 0.0;

			List<Medicion> medicionesGasolinera = medicionRepository.findByIdGasolinera(g.getIdGasolinera());
			Double sumaMediciones = 0.0;
			for(Medicion medicion: medicionesGasolinera) {
				sumaMediciones += (medicion.getLitrosIngresados()*5)/medicion.getLitrosSolicitados();
			}
			promedio = sumaMediciones / medicionesGasolinera.size();
			g.setCalificacion(promedio);
			
			gasolineraRepository.save(g);
		}
		response.setMsg(ConstantesNegocio.MSG_PROMEDIO_PRUEBA);
		return new ResponseEntity<ResponseGeneric<String>>(response,HttpStatus.OK);
	}
	
	private Double obtenerCalificacionInicial() {
		if(ConstantesNegocio.ENV_DEVELOPMENT) {
			Random rand = new Random();
			return (double)rand.nextDouble()*5;
		} else {
			return ConstantesNegocio.CALIFICACION_INICIAL;
		}
	}

}
