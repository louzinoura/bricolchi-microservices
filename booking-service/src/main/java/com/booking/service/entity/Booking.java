package com.booking.service.entity;

import com.booking.service.enums.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "listing_id")
    private Long listingId;

    @NotNull
    @Column(name = "client_id")
    private Long clientId;

    @NotNull
    @Column(name = "provider_id")
    private Long providerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookingStatus status = BookingStatus.PENDING;

    @Column(name = "message", length = 1000)
    private String message;

    @Column(name = "proposed_price", precision = 10, scale = 2)
    private BigDecimal proposedPrice;

    @Column(name = "service_date")
    private LocalDateTime serviceDate;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructeurs
    public Booking() {}

    public Booking(Long listingId, Long clientId, Long providerId, String message, BigDecimal proposedPrice, LocalDateTime serviceDate) {
        this.listingId = listingId;
        this.clientId = clientId;
        this.providerId = providerId;
        this.message = message;
        this.proposedPrice = proposedPrice;
        this.serviceDate = serviceDate;
    }

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