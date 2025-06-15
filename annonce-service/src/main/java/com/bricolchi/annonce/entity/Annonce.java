package com.bricolchi.annonce.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "annonces")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String categorie;

    @Column(nullable = false)
    private Double prix;

    @Column(nullable = false)
    private String localisation;

    private String adresse;

    @Column(nullable = false)
    private Long userId; // ID du prestataire

    @Column(nullable = false)
    private String userName; // Nom du prestataire

    @Column(nullable = false)
    private String userEmail;

    private String userPhone;

    @Enumerated(EnumType.STRING)
    private StatusAnnonce status = StatusAnnonce.ACTIVE;

    @ElementCollection
    @CollectionTable(name = "annonce_images", joinColumns = @JoinColumn(name = "annonce_id"))
    @Column(name = "image_url")
    private List<String> images;

    @ElementCollection
    @CollectionTable(name = "annonce_tags", joinColumns = @JoinColumn(name = "annonce_id"))
    @Column(name = "tag")
    private List<String> tags;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Géolocalisation
    private Double latitude;
    private Double longitude;

    // Disponibilité
    private Boolean disponible = true;

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum StatusAnnonce {
        ACTIVE, INACTIVE, ARCHIVED
    }
}
