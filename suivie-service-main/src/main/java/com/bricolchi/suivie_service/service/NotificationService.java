package com.bricolchi.suivie_service.service;


import com.bricolchi.suivie_service.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final RestTemplate restTemplate;

    @Value("${services.notification.url}")
    private String notificationServiceUrl;

    public void sendStatusChangeNotification(Long userId, String type, Long bookingId, String status) {
        try {
            NotificationRequest notification = new NotificationRequest();
            notification.setUserId(userId);
            notification.setType(type);
            notification.setBookingId(bookingId);

            switch (status) {
                case "CONFIRMED":
                    notification.setTitle("Service confirmé");
                    notification.setMessage("Votre réservation a été confirmée et le service va bientôt commencer.");
                    break;
                case "IN_PROGRESS":
                    notification.setTitle("Service en cours");
                    notification.setMessage("Le prestataire a commencé à travailler sur votre demande.");
                    break;
                case "COMPLETED":
                    notification.setTitle("Service terminé");
                    notification.setMessage("Le service a été terminé avec succès.");
                    break;
                case "CANCELLED":
                    notification.setTitle("Service annulé");
                    notification.setMessage("Le service a été annulé.");
                    break;
                default:
                    notification.setTitle("Mise à jour du statut");
                    notification.setMessage("Le statut de votre réservation a été mis à jour.");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<NotificationRequest> request = new HttpEntity<>(notification, headers);

            String notificationUrl = notificationServiceUrl + "/api/notifications/send";
            restTemplate.postForObject(notificationUrl, request, String.class);

            log.info("Notification sent successfully for booking: {}", bookingId);
        } catch (Exception e) {
            log.error("Failed to send notification for booking: {}", bookingId, e);
        }
    }
}