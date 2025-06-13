package com.bricolchi.suivie_service.repository;


import com.bricolchi.suivie_service.model.ServiceTracking;
import com.bricolchi.suivie_service.model.TrackingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceTrackingRepository extends JpaRepository<ServiceTracking, Long> {

    Optional<ServiceTracking> findByBookingId(Long bookingId);

    List<ServiceTracking> findByClientId(Long clientId);

    List<ServiceTracking> findByProviderId(Long providerId);

    List<ServiceTracking> findByStatus(TrackingStatus status);

    @Query("SELECT st FROM ServiceTracking st WHERE st.clientId = :userId OR st.providerId = :userId")
    List<ServiceTracking> findByUserId(@Param("userId") Long userId);

    boolean existsByBookingId(Long bookingId);
}
