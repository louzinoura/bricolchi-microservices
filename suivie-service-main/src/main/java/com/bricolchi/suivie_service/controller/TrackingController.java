package com.bricolchi.suivie_service.controller;


import com.bricolchi.suivie_service.dto.TrackingResponse;
import com.bricolchi.suivie_service.model.StatusUpdateRequest;
import com.bricolchi.suivie_service.service.TrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tracking")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class TrackingController {

    private final TrackingService trackingService;

    @GetMapping("/{bookingId}")
    public ResponseEntity<TrackingResponse> getTracking(@PathVariable Long bookingId) {
        try {
            log.info("GET /api/tracking/{} - Getting tracking info", bookingId);
            TrackingResponse tracking = trackingService.getTrackingByBookingId(bookingId);
            return ResponseEntity.ok(tracking);
        } catch (RuntimeException e) {
            log.error("Error getting tracking for booking {}: {}", bookingId, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Unexpected error getting tracking for booking {}", bookingId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/update-status")
    public ResponseEntity<TrackingResponse> updateStatus(@Valid @RequestBody StatusUpdateRequest request) {
        try {
            log.info("POST /api/tracking/update-status - Updating status for booking: {}", request.getBookingId());
            TrackingResponse tracking = trackingService.updateStatus(request);
            return ResponseEntity.ok(tracking);
        } catch (RuntimeException e) {
            log.error("Error updating status for booking {}: {}", request.getBookingId(), e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Unexpected error updating status for booking {}", request.getBookingId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<TrackingResponse> createTracking(
            @RequestParam Long bookingId,
            @RequestParam Long clientId,
            @RequestParam Long providerId) {
        try {
            log.info("POST /api/tracking/create - Creating tracking for booking: {}", bookingId);
            TrackingResponse tracking = trackingService.createTracking(bookingId, clientId, providerId);
            return ResponseEntity.status(HttpStatus.CREATED).body(tracking);
        } catch (RuntimeException e) {
            log.error("Error creating tracking for booking {}: {}", bookingId, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Unexpected error creating tracking for booking {}", bookingId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TrackingResponse>> getUserTrackings(@PathVariable Long userId) {
        try {
            log.info("GET /api/tracking/user/{} - Getting user trackings", userId);
            List<TrackingResponse> trackings = trackingService.getTrackingsByUserId(userId);
            return ResponseEntity.ok(trackings);
        } catch (Exception e) {
            log.error("Error getting trackings for user {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Tracking Service is running on port 8084");
    }
}
