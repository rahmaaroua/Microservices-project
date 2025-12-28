package tn.univ.GestionEvenement;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GestionEvenementApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionEvenementApplication.class, args);
    }
}
