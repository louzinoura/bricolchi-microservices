package com.bricolchi.annonce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnnonceRequest {
    private String titre;
    private String description;
    private String categorie;
    private Double prix;
    private String localisation;
    private String adresse;
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private List<String> images;
    private List<String> tags;
    private Double latitude;
    private Double longitude;
}