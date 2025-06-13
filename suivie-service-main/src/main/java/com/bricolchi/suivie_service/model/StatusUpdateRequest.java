package com.bricolchi.suivie_service.model;



import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class StatusUpdateRequest {

    @NotNull(message = "Booking ID is required")
    private Long bookingId;

    @NotNull(message = "Status is required")
    private TrackingStatus status;

    private String description;
    private String location;
    private LocalDateTime estimatedCompletion;
    private String notes;
}
