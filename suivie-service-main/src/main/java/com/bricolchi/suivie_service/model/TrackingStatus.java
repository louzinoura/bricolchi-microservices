package com.bricolchi.suivie_service.model;



public enum TrackingStatus {
    PENDING("En attente"),
    CONFIRMED("Confirmé"),
    IN_PROGRESS("En cours"),
    COMPLETED("Terminé"),
    CANCELLED("Annulé");

    private final String description;

    TrackingStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}