package com.booking.service.dto;

import com.booking.service.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingResponseDto {

    private Long id;
    private Long listingId;
    private Long clientId;
    private Long providerId;
    private BookingStatus status;
    private String message;
    private BigDecimal proposedPrice;
    private LocalDateTime serviceDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructeurs
    public BookingResponseDto() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getListingId() { return listingId; }
    public void setListingId(Long listingId) { this.listingId = listingId; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public Long getProviderId() { return providerId; }
    public void setProviderId(Long providerId) { this.providerId = providerId; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public BigDecimal getProposedPrice() { return proposedPrice; }
    public void setProposedPrice(BigDecimal proposedPrice) { this.proposedPrice = proposedPrice; }

    public LocalDateTime getServiceDate() { return serviceDate; }
    public void setServiceDate(LocalDateTime serviceDate) { this.serviceDate = serviceDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}