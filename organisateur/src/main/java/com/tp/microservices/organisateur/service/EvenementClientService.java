package com.tp.microservices.organisateur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EvenementClientService {

    @Autowired
    private RestTemplate restTemplate;

    private final String EVENEMENT_URL = "http://evenement-microservice/Evenement";

    public Map<String, Object> getEvenementById(int id) {
        String url = EVENEMENT_URL + "/" + id;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return response.getBody(); // raw data as Map
    }

    public String getEvenementCount() {
        String url = EVENEMENT_URL + "/count";
        return restTemplate.getForObject(url, String.class);
    }
}
