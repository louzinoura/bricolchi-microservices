package com.booking.service.repository;

import com.booking.service.entity.Booking;
import com.booking.service.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Candidatures reçues par un client
    List<Booking> findByClientIdOrderByCreatedAtDesc(Long clientId);

    // Candidatures envoyées par un prestataire
    List<Booking> findByProviderIdOrderByCreatedAtDesc(Long providerId);

    // Candidatures par statut
    List<Booking> findByStatus(BookingStatus status);

    // Candidatures d'un client avec un statut spécifique
    List<Booking> findByClientIdAndStatus(Long clientId, BookingStatus status);

    // Candidatures d'un prestataire avec un statut spécifique
    List<Booking> findByProviderIdAndStatus(Long providerId, BookingStatus status);

    // Candidatures pour une annonce spécifique
    List<Booking> findByListingIdOrderByCreatedAtDesc(Long listingId);

    // Requête personnalisée : candidatures en attente pour un client
    @Query("SELECT b FROM Booking b WHERE b.clientId = :clientId AND b.status = 'PENDING' ORDER BY b.createdAt DESC")
    List<Booking> findPendingBookingsForClient(@Param("clientId") Long clientId);
}