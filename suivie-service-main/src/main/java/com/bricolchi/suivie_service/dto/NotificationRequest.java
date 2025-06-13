package com.bricolchi.suivie_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private Long userId;
    private String type;
    private String title;
    private String message;
    private Long bookingId;
}