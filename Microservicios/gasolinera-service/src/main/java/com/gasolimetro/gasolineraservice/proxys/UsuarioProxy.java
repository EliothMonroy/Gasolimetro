package com.gasolimetro.gasolineraservice.proxys;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="usuario-service")
@RibbonClient(name="usuario-service")
public interface UsuarioProxy {

}
