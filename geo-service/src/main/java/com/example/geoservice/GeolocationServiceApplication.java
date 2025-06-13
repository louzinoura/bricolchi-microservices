package com.example.geoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GeolocationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeolocationServiceApplication.class, args);
    }

    // Le bean doit être déclaré directement dans la classe, pas dans main()
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
