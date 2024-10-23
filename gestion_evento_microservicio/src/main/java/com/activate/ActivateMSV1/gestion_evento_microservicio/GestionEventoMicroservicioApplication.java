package com.activate.ActivateMSV1.gestion_evento_microservicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GestionEventoMicroservicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionEventoMicroservicioApplication.class, args);
	}

}
