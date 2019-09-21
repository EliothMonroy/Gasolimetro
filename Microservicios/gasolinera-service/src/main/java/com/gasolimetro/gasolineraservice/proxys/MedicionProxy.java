package com.gasolimetro.gasolineraservice.proxys;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="gasolimetro-gateway")
@RibbonClient(name="medicion-service")
public interface MedicionProxy {

}
