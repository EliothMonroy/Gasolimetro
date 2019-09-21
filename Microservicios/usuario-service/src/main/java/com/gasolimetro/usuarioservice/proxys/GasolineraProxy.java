package com.gasolimetro.usuarioservice.proxys;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="gasolimetro-gateway")
@RibbonClient(name="gasolinera-service")
public interface GasolineraProxy {

}
