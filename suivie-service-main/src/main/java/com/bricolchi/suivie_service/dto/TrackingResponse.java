package com.bricolchi.suivie_service.dto;


import com.bricolchi.suivie_service.model.TrackingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingResponse {
    private Long id;
    private Long bookingId;
    private Long clientId;
    private Long providerId;
    private TrackingStatus status;
    private String statusDescription;
    private String description;
    private String location;
    private LocalDateTime estimatedCompletion;
    private LocalDateTime actualCompletion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String notes;
}

