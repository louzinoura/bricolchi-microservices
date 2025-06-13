package com.example.geoservice.service;

import com.example.geoservice.dto.AnnonceDto;
import com.example.geoservice.dto.AnnoncePageDto;
import com.example.geoservice.model.GeoResponse;
import com.example.geoservice.util.HaversineUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeoService {

    @Value("${opencage.api.key}")
    private String apiKey;

    @Value("${opencage.api.url}")
    private String apiUrl;

    @Value("${annonce.service.url}")
    private String annonceServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public GeoResponse geocode(String address) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("q", address)
                .queryParam("key", apiKey)
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);
        JSONObject json = new JSONObject(response);
        JSONObject geometry = json.getJSONArray("results")
                .getJSONObject(0)
                .getJSONObject("geometry");

        return new GeoResponse(geometry.getDouble("lat"), geometry.getDouble("lng"));
    }

    public List<AnnonceDto> getNearbyAnnonces(double lat, double lon, double radiusKm) {
        System.out.println("Annonce Service URL = " + annonceServiceUrl);

        String url = annonceServiceUrl + "/api/annonce";  // URL de l’autre microservice

        System.out.println("==> Calling Annonce Service at: " + url);

        try {
            ResponseEntity<AnnoncePageDto> response = restTemplate.getForEntity(url, AnnoncePageDto.class);
            List<AnnonceDto> annonces = response.getBody() != null ? response.getBody().getContent() : List.of();

            return annonces.stream()
                    .filter(a -> HaversineUtil.calculate(lat, lon, a.getLatitude(), a.getLongitude()) <= radiusKm)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Erreur appel AnnonceService: " + e.getMessage());
            return List.of();
        }
    }


}
