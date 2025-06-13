package com.example.geoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnonceDto {
    private Long id;
    private String titre;
    private String description;
    private double latitude;
    private double longitude;
}
