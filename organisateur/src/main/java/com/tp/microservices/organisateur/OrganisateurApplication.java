package com.tp.microservices.organisateur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
public class OrganisateurApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrganisateurApplication.class, args);
    }

}
