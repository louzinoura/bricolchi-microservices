package com.example.geoservice.dto;

import com.example.geoservice.dto.AnnonceDto;

import java.util.List;

public class AnnoncePageDto {
    private List<AnnonceDto> content;

    // Tu peux aussi ajouter pageable, totalElements, etc., si besoin
    public List<AnnonceDto> getContent() {
        return content;
    }

    public void setContent(List<AnnonceDto> content) {
        this.content = content;
    }
}
