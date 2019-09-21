package com.gasolimetro.medicionservice.proxys;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="usuario-gateway")
@RibbonClient(name="usuario-service")
public interface UsuarioProxy {

}
