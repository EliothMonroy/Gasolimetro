package com.gasolimetro.usuarioservice.proxys;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gasolimetro.usuarioservice.api_models.SensorInfoModel;

@FeignClient(name="medicion-service")
@RibbonClient(name="medicion-service")
public interface MedicionProxy {
	@GetMapping("/sensores/automovil/{idAutomovil}")
	public List<SensorInfoModel> obtenerInfoSensores(@PathVariable Long idAutomovil);
}