package com.bricolchi.suivie_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*; // (Jakarta EE 9+)

import java.time.LocalDateTime;

@Entity
@Table(name = "service_tracking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id", nullable = false, unique = true)
    private Long bookingId;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "provider_id", nullable = false)
    private Long providerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TrackingStatus status;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "estimated_completion")
    private LocalDateTime estimatedCompletion;

    @Column(name = "actual_completion")
    private LocalDateTime actualCompletion;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "notes")
    private String notes;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = TrackingStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}