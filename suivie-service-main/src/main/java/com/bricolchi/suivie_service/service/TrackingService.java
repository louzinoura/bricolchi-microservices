package com.bricolchi.suivie_service.service;


import com.bricolchi.suivie_service.dto.TrackingResponse;
import com.bricolchi.suivie_service.model.ServiceTracking;
import com.bricolchi.suivie_service.model.StatusUpdateRequest;
import com.bricolchi.suivie_service.model.TrackingStatus;
import com.bricolchi.suivie_service.repository.ServiceTrackingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackingService {

    private final ServiceTrackingRepository trackingRepository;
    private final NotificationService notificationService;

    @Transactional(readOnly = true)
    public TrackingResponse getTrackingByBookingId(Long bookingId) {
        log.info("Getting tracking info for booking: {}", bookingId);

        ServiceTracking tracking = trackingRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Tracking not found for booking: " + bookingId));

        return convertToResponse(tracking);
    }

    @Transactional
    public TrackingResponse updateStatus(StatusUpdateRequest request) {
        log.info("Updating status for booking: {} to {}", request.getBookingId(), request.getStatus());

        ServiceTracking tracking = trackingRepository.findByBookingId(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Tracking not found for booking: " + request.getBookingId()));

        TrackingStatus oldStatus = tracking.getStatus();

        // Mettre à jour les informations
        tracking.setStatus(request.getStatus());
        tracking.setDescription(request.getDescription());
        tracking.setLocation(request.getLocation());
        tracking.setEstimatedCompletion(request.getEstimatedCompletion());
        tracking.setNotes(request.getNotes());

        // Si le service est terminé, enregistrer la date de fin
        if (request.getStatus() == TrackingStatus.COMPLETED) {
            tracking.setActualCompletion(LocalDateTime.now());
        }

        ServiceTracking savedTracking = trackingRepository.save(tracking);

        // Envoyer des notifications si le statut a changé
        if (oldStatus != request.getStatus()) {
            notificationService.sendStatusChangeNotification(
                    tracking.getClientId(),
                    "status_update",
                    tracking.getBookingId(),
                    request.getStatus().name()
            );

            notificationService.sendStatusChangeNotification(
                    tracking.getProviderId(),
                    "status_update",
                    tracking.getBookingId(),
                    request.getStatus().name()
            );
        }

        log.info("Status updated successfully for booking: {}", request.getBookingId());
        return convertToResponse(savedTracking);
    }

    @Transactional
    public TrackingResponse createTracking(Long bookingId, Long clientId, Long providerId) {
        log.info("Creating tracking for booking: {}", bookingId);

        if (trackingRepository.existsByBookingId(bookingId)) {
            throw new RuntimeException("Tracking already exists for booking: " + bookingId);
        }

        ServiceTracking tracking = new ServiceTracking();
        tracking.setBookingId(bookingId);
        tracking.setClientId(clientId);
        tracking.setProviderId(providerId);
        tracking.setStatus(TrackingStatus.PENDING);
        tracking.setDescription("Réservation créée, en attente de confirmation");

        ServiceTracking savedTracking = trackingRepository.save(tracking);

        log.info("Tracking created successfully for booking: {}", bookingId);
        return convertToResponse(savedTracking);
    }

    @Transactional(readOnly = true)
    public List<TrackingResponse> getTrackingsByUserId(Long userId) {
        log.info("Getting trackings for user: {}", userId);

        List<ServiceTracking> trackings = trackingRepository.findByUserId(userId);
        return trackings.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private TrackingResponse convertToResponse(ServiceTracking tracking) {
        TrackingResponse response = new TrackingResponse();
        response.setId(tracking.getId());
        response.setBookingId(tracking.getBookingId());
        response.setClientId(tracking.getClientId());
        response.setProviderId(tracking.getProviderId());
        response.setStatus(tracking.getStatus());
        response.setStatusDescription(tracking.getStatus().getDescription());
        response.setDescription(tracking.getDescription());
        response.setLocation(tracking.getLocation());
        response.setEstimatedCompletion(tracking.getEstimatedCompletion());
        response.setActualCompletion(tracking.getActualCompletion());
        response.setCreatedAt(tracking.getCreatedAt());
        response.setUpdatedAt(tracking.getUpdatedAt());
        response.setNotes(tracking.getNotes());
        return response;
    }
}