package com.booking.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingRequestDto {

    @NotNull(message = "L'ID de l'annonce est obligatoire")
    private Long listingId;

    @NotNull(message = "L'ID du client est obligatoire")
    private Long clientId;

    @NotNull(message = "L'ID du prestataire est obligatoire")
    private Long providerId;

    private String message;

    @Positive(message = "Le prix proposé doit être positif")
    private BigDecimal proposedPrice;

    private LocalDateTime serviceDate;

    // Constructeurs
    public BookingRequestDto() {}

    // Getters et Setters
    public Long getListingId() { return listingId; }
    public void setListingId(Long listingId) { this.listingId = listingId; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public Long getProviderId() { return providerId; }
    public void setProviderId(Long providerId) { this.providerId = providerId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public BigDecimal getProposedPrice() { return proposedPrice; }
    public void setProposedPrice(BigDecimal proposedPrice) { this.proposedPrice = proposedPrice; }

    public LocalDateTime getServiceDate() { return serviceDate; }
    public void setServiceDate(LocalDateTime serviceDate) { this.serviceDate = serviceDate; }
}