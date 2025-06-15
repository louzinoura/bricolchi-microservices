package com.example.geoservice.controller;

import com.example.geoservice.model.GeoRequest;
import com.example.geoservice.model.GeoResponse;
import com.example.geoservice.service.GeoService;
import com.example.geoservice.util.HaversineUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import com.example.geoservice.dto.AnnonceDto; // adapte le package selon où tu places AnnonceDto

@RestController  // <- This annotation was missing!
@RequestMapping("/api/geo")
public class GeoController {

    private final GeoService geoService;

    public GeoController(GeoService geoService) {
        this.geoService = geoService;
    }

    @PostMapping("/geocode")
    public GeoResponse geocode(@RequestBody GeoRequest request) {
        return geoService.geocode(request.getAddress());
    }

    @GetMapping("/distance")
    public double distance(@RequestParam double lat1,
                           @RequestParam double lon1,
                           @RequestParam double lat2,
                           @RequestParam double lon2) {
        return HaversineUtil.calculate(lat1, lon1, lat2, lon2);
    }

    @PostMapping("/nearby")
    public ResponseEntity<List<AnnonceDto>> getNearby(@RequestBody GeoRequest request) {
        double lat, lon;

        if (request.getLatitude() != null && request.getLongitude() != null) {
            lat = request.getLatitude();
            lon = request.getLongitude();
        } else if (request.getAddress() != null) {
            GeoResponse geo = geoService.geocode(request.getAddress());
            lat = geo.getLatitude();
            lon = geo.getLongitude();
        } else {
            return ResponseEntity.badRequest().build();
        }

        double radius = (request.getRadius() != null) ? request.getRadius() : 10.0;

        return ResponseEntity.ok(geoService.getNearbyAnnonces(lat, lon, radius));
    }


    @PostMapping("/recommendations")
    public ResponseEntity<List<AnnonceDto>> getRecommendations(@RequestBody GeoRequest request) {
        double lat, lon;

        if (request.getLatitude() != null && request.getLongitude() != null) {
            lat = request.getLatitude();
            lon = request.getLongitude();
        } else if (request.getAddress() != null) {
            GeoResponse geo = geoService.geocode(request.getAddress());
            lat = geo.getLatitude();
            lon = geo.getLongitude();
        } else {
            return ResponseEntity.badRequest().build();
        }

        double radius = (request.getRadius() != null) ? request.getRadius() : 15.0;

        // Hypothèse : recommandations basées sur proximité
        List<AnnonceDto> nearby = geoService.getNearbyAnnonces(lat, lon, radius);

        // 🔁 Tu pourrais affiner ici : filtrer par popularité, catégorie, etc.
        return ResponseEntity.ok(nearby);
    }




    // Optional: Add a test endpoint to verify the controller is working
    @GetMapping("/test")
    public String test() {
        return "Geo service is working!";
    }
}