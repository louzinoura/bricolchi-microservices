package com.bricolchi.annonce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private String categorie;
    private String localisation;
    private Double prixMin;
    private Double prixMax;
    private String motCle;
    private int page = 0;
    private int size = 10;
    private String sortBy = "createdAt";
    private String sortDir = "desc";
}