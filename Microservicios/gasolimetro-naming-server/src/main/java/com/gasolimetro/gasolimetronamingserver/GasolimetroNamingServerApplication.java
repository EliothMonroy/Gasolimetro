package com.gasolimetro.gasolimetronamingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GasolimetroNamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GasolimetroNamingServerApplication.class, args);
	}

}
