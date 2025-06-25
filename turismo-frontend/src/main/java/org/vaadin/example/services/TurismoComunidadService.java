package org.vaadin.example.services;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vaadin.example.models.TurismoComunidad;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class TurismoComunidadService {

    private final RestTemplate restTemplate;

    public TurismoComunidadService() {
        this.restTemplate = new RestTemplate();
    }

    public List<TurismoComunidad> getAllTurismoComunidad() {
        TurismoComunidad[] turismoArray = restTemplate.getForObject("http://localhost:8080/api/turismo/all", TurismoComunidad[].class);
        return Arrays.asList(turismoArray);
    }

    public void addTurismoComunidad(TurismoComunidad turismoComunidad) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TurismoComunidad> request = new HttpEntity<>(turismoComunidad, headers);
        restTemplate.postForObject("http://localhost:8080/api/turismo/add", request, TurismoComunidad.class);
    }

    public void updateTurismoComunidad(TurismoComunidad turismoComunidad) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TurismoComunidad> request = new HttpEntity<>(turismoComunidad, headers);

        try {
            restTemplate.put("http://localhost:8080/api/turismo/update", request);
        } catch (Exception ex) {
            throw new RuntimeException("Error al enviar actualización al backend: " + ex.getMessage());
        }
    }

    public Map<String, List<TurismoComunidad>> getAllAgrupadas() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<Map<String, Map<String, List<TurismoComunidad>>>> response = restTemplate.exchange(
                    "http://localhost:8080/api/turismo/allAgrupadas",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<>() {}
            );

            // Extraer el mapa de comunidades
            return response.getBody().get("comunidades");
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las comunidades agrupadas: " + e.getMessage(), e);
        }
    }



}