package tn.univ.GestionEvenement.Config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced  // Important pour utiliser le nom du service Eureka
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}