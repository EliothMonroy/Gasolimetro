package com.gasolimetro.gasolimetrogatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import com.netflix.config.ConfigurationManager;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class GasolimetroGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GasolimetroGatewayServerApplication.class, args);
		
	}

}
